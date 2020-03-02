package com.babblingbrook.mtgcardsearch.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Entity
@Parcelize
data class Card(
    @PrimaryKey
    val id: String,
    val name: String,
    val type_line: String,
    @SerializedName("image_uris")
    val imageUris: @RawValue ImageUris?,
    @SerializedName("oracle_text")
    val oracleText: String?,
    val power: String?,
    val toughness: String?,
    @SerializedName("mana_cost")
    val manaCost: String?,
    @SerializedName("card_faces")
    val cardFaces: @RawValue List<CardFace>?,
    var isFavorite: Boolean = false
) : Parcelable