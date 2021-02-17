package com.stephenmorgandevelopment.more_quore_galore.models

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class Article (
    @Embedded val source: Source,
    val author: String,
    @PrimaryKey val title: String,
    val description: String,
    val url: String,
    val urlToImage: String,
    val publishedAt: String,
    val content: String
)