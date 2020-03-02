package com.babblingbrook.mtgcardsearch.model

import com.google.gson.annotations.SerializedName

data class ImageUris(
    val id: String?,
    val cardId: String,
    val small: String,
    val normal: String,
    val large: String,
    val png: String,
    @SerializedName("art_crop")
    val artCrop: String,
    @SerializedName("border_crop")
    val borderCrop: String
)