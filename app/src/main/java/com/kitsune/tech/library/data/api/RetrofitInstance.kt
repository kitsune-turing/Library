package com.kitsune.tech.library.data.api

import android.content.Context
import com.kitsune.tech.library.data.api.models.DescriptionAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Cache
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

object RetrofitInstance {
    private const val BASE_URL = "https://openlibrary.org/"
    private const val CACHE_SIZE = 10L * 1024 * 1024 // 10 MB
    private const val CACHE_MAX_AGE = 60 * 60 // 1 hour
    private const val CACHE_MAX_STALE = 60 * 60 * 24 * 7 // 1 week

    private lateinit var cache: Cache
    private lateinit var retrofit: Retrofit

    fun initialize(context: Context) {
        val cacheDir = File(context.cacheDir, "http_cache")
        cache = Cache(cacheDir, CACHE_SIZE)

        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BASIC
        }

        // Cache interceptor for responses
        val cacheInterceptor = Interceptor { chain ->
            val response = chain.proceed(chain.request())
            val cacheControl = CacheControl.Builder()
                .maxAge(CACHE_MAX_AGE, TimeUnit.SECONDS)
                .build()
            response.newBuilder()
                .header("Cache-Control", cacheControl.toString())
                .removeHeader("Pragma")
                .build()
        }

        // Offline cache interceptor
        val offlineCacheInterceptor = Interceptor { chain ->
            var request = chain.request()

            // If there's no network, use stale cache
            if (!isNetworkAvailable(context)) {
                val cacheControl = CacheControl.Builder()
                    .maxStale(CACHE_MAX_STALE, TimeUnit.SECONDS)
                    .onlyIfCached()
                    .build()
                request = request.newBuilder()
                    .cacheControl(cacheControl)
                    .build()
            }
            chain.proceed(request)
        }

        val okHttpClient = OkHttpClient.Builder()
            .cache(cache)
            .addInterceptor(offlineCacheInterceptor)
            .addNetworkInterceptor(cacheInterceptor)
            .addInterceptor(loggingInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()

        val moshi = Moshi.Builder()
            .add(DescriptionAdapter())
            .add(KotlinJsonAdapterFactory())
            .build()

        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    val api: OpenLibraryApi by lazy {
        retrofit.create(OpenLibraryApi::class.java)
    }

    private fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE)
            as android.net.ConnectivityManager
        val activeNetwork = connectivityManager.activeNetworkInfo
        return activeNetwork?.isConnectedOrConnecting == true
    }

    fun clearCache() {
        cache.evictAll()
    }
}
