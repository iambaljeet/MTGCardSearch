package com.babblingbrook.mtgcardsearch.model

import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.RawValue

data class CardFaces(
    @SerializedName("image_uris")
    val imageUris: @RawValue ImageUris?,
    @SerializedName("mana_cost")
    val manaCost: String?,
    val name: String,
    @SerializedName("oracle_text")
    val oracleText: String,
    val power: String?,
    val toughness: String?,
    @SerializedName("type_line")
    val typeLine: String?
    )