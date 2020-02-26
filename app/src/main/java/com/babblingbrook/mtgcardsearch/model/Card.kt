package com.babblingbrook.mtgcardsearch.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class Card(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("type_line")
    val typeLine: String,
    @SerializedName("image_uris")
    val image_uris: @RawValue ImageUris
) : Parcelable