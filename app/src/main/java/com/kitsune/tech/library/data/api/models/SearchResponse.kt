package com.kitsune.tech.library.data.api.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SearchResponse(
    @Json(name = "start")
    val start: Int,
    @Json(name = "num_found")
    val numFound: Int,
    @Json(name = "docs")
    val docs: List<BookDoc>
)

@JsonClass(generateAdapter = true)
data class BookDoc(
    @Json(name = "key")
    val key: String?,
    @Json(name = "title")
    val title: String?,
    @Json(name = "author_name")
    val authorName: List<String>?,
    @Json(name = "first_publish_year")
    val firstPublishYear: Int?,
    @Json(name = "cover_i")
    val coverId: Long?,
    @Json(name = "isbn")
    val isbn: List<String>?,
    @Json(name = "subject")
    val subjects: List<String>?,
    @Json(name = "number_of_pages_median")
    val numberOfPages: Int?,
    @Json(name = "ratings_average")
    val ratingsAverage: Double?
) {
    fun getCoverUrl(size: String = "M"): String? {
        return coverId?.let {
            "https://covers.openlibrary.org/b/id/$it-$size.jpg"
        }
    }

    fun getWorkId(): String? {
        return key?.removePrefix("/works/")
    }
}
