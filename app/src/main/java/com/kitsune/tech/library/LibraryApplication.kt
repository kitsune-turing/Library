package com.kitsune.tech.library

import android.app.Application
import com.kitsune.tech.library.data.api.RetrofitInstance

class LibraryApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        RetrofitInstance.initialize(this)
    }
}
