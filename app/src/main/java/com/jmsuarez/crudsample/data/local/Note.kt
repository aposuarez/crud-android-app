package com.jmsuarez.crudsample.data.local

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Note(
    val id: String,
    val title: String,
    val description: String,
    val createdAt: String,
    val updatedAt: String
) : Parcelable