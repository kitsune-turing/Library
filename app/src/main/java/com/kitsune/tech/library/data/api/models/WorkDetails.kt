package com.kitsune.tech.library.data.api.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class WorkDetails(
    @Json(name = "key")
    val key: String?,
    @Json(name = "title")
    val title: String?,
    @Json(name = "description")
    val description: Description?,
    @Json(name = "covers")
    val covers: List<Long>?,
    @Json(name = "subjects")
    val subjects: List<String>?,
    @Json(name = "authors")
    val authors: List<AuthorRef>?,
    @Json(name = "first_publish_date")
    val firstPublishDate: String?
) {
    fun getCoverUrl(size: String = "L"): String? {
        return covers?.firstOrNull()?.let {
            "https://covers.openlibrary.org/b/id/$it-$size.jpg"
        }
    }

    fun getDescriptionText(): String? {
        return when (description) {
            is Description.TextDescription -> description.value
            is Description.ObjectDescription -> description.value
            null -> null
        }
    }
}

@JsonClass(generateAdapter = true)
data class AuthorRef(
    @Json(name = "author")
    val author: AuthorKey?
)

@JsonClass(generateAdapter = true)
data class AuthorKey(
    @Json(name = "key")
    val key: String?
)

sealed class Description {
    data class TextDescription(val value: String) : Description()
    data class ObjectDescription(@Json(name = "value") val value: String) : Description()
}
