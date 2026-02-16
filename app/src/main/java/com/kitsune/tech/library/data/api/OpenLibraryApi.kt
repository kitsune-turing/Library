package com.kitsune.tech.library.data.api

import com.kitsune.tech.library.data.api.models.AuthorDetails
import com.kitsune.tech.library.data.api.models.SearchResponse
import com.kitsune.tech.library.data.api.models.WorkDetails
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface OpenLibraryApi {

    @GET("search.json")
    suspend fun searchBooks(
        @Query("q") query: String,
        @Query("limit") limit: Int = 20,
        @Query("offset") offset: Int = 0,
        @Query("fields") fields: String = "key,title,author_name,first_publish_year,cover_i,isbn,subject,number_of_pages_median,ratings_average"
    ): SearchResponse

    @GET("search.json")
    suspend fun searchBooksByAuthor(
        @Query("author") author: String,
        @Query("limit") limit: Int = 20,
        @Query("offset") offset: Int = 0,
        @Query("fields") fields: String = "key,title,author_name,first_publish_year,cover_i,isbn,subject,number_of_pages_median,ratings_average"
    ): SearchResponse

    @GET("search.json")
    suspend fun searchBooksBySubject(
        @Query("subject") subject: String,
        @Query("limit") limit: Int = 20,
        @Query("offset") offset: Int = 0,
        @Query("fields") fields: String = "key,title,author_name,first_publish_year,cover_i,isbn,subject,number_of_pages_median,ratings_average"
    ): SearchResponse

    @GET("works/{id}.json")
    suspend fun getWorkDetails(
        @Path("id") workId: String
    ): WorkDetails

    @GET("authors/{id}.json")
    suspend fun getAuthorDetails(
        @Path("id") authorId: String
    ): AuthorDetails

    @GET("isbn/{isbn}.json")
    suspend fun getBookByIsbn(
        @Path("isbn") isbn: String
    ): WorkDetails
}

object CoverImageHelper {
    fun getCoverUrl(coverId: Long, size: String = "M"): String {
        return "https://covers.openlibrary.org/b/id/$coverId-$size.jpg"
    }

    fun getCoverUrlByIsbn(isbn: String, size: String = "M"): String {
        return "https://covers.openlibrary.org/b/isbn/$isbn-$size.jpg"
    }

    enum class Size(val value: String) {
        SMALL("S"),
        MEDIUM("M"),
        LARGE("L")
    }
}
