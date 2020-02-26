package com.babblingbrook.mtgcardsearch.model

import com.google.gson.annotations.SerializedName

data class ImageUris(
    @SerializedName("small")
    val small: String,
    @SerializedName("normal")
    val normal: String,
    @SerializedName("large")
    val large: String,
    @SerializedName("png")
    val png: String,
    @SerializedName("art_crop")
    val art_crop: String,
    @SerializedName("border_crop")
    val border_crop: String
)