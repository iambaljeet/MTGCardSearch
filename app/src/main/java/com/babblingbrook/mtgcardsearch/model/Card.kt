package com.babblingbrook.mtgcardsearch.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class Card(
    val id: String,
    val name: String,
    val type_line: String,
    val image_uris: @RawValue ImageUris,
    val oracle_text: String
) : Parcelable