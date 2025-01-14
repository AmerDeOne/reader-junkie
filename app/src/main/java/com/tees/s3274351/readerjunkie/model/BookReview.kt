package com.tees.s3274351.readerjunkie.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlin.String


data class BookReview(
    val userId: String,
    val title: String,
    val author: String,
    val edition: String,
    val publisher: String,
    val review: String,
    var id: String
) {
    constructor() : this("", "", "", "", "", "", "")

    fun makeReview(): Review{
        return Review(
            userId = userId,
            title = title,
            author = author,
            edition = edition,
            publisher = publisher,
            review = review,
            id = id
        )
    }
}

@Parcelize
data class Review(
    val userId: String,
    val title: String,
    val author: String,
    val edition: String,
    val publisher: String,
    val review: String,
    var id: String
): Parcelable