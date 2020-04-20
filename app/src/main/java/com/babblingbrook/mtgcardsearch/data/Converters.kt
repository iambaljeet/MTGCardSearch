package com.babblingbrook.mtgcardsearch.data

import androidx.room.TypeConverter
import com.babblingbrook.mtgcardsearch.model.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class Converters {
    @TypeConverter
    fun cardFacesToString(cardFaces: List<CardFaces>?): String? = toJson(cardFaces)

    @TypeConverter
    fun stringToCardFaces(json: String?): List<CardFaces>? = fromJson(json)

    @TypeConverter
    fun imageUriToString(imageUris: ImageUris?): String? = toJson(imageUris)

    @TypeConverter
    fun stringToImageUris(json: String?): ImageUris? = fromJson(json)

    @TypeConverter
    fun legalitiesToString(legalities: Legalities?): String? = toJson(legalities)

    @TypeConverter
    fun stringToLegalities(json: String?): Legalities? = fromJson(json)

    @TypeConverter
    fun multiverseIdsToString(multiverseIds: List<Int>?): String? = toJson(multiverseIds)

    @TypeConverter
    fun stringToMultiverseIds(json: String?): List<Int>? = fromJson(json)

    @TypeConverter
    fun colorsToString(colors: List<String>?): String? = toJson(colors)

    @TypeConverter
    fun stringToColors(json: String?): List<String>? = fromJson(json)

    @TypeConverter
    fun pricesToString(prices: Prices?): String? = toJson(prices)

    @TypeConverter
    fun stringToPrices(json: String?): Prices? = fromJson(json)

    @TypeConverter
    fun purchaseUrisToString(purchaseUris: PurchaseUris?): String? = toJson(purchaseUris)

    @TypeConverter
    fun stringToPurchaseUris(json: String?): PurchaseUris? = fromJson(json)

    @TypeConverter
    fun relatedUrisToString(relatedUris: RelatedUris?): String? = toJson(relatedUris)

    @TypeConverter
    fun stringToRelatedUris(json: String?): RelatedUris? = fromJson(json)

    inline fun <reified T> fromJson(value: String?): T {
        val gson = Gson()
        return gson.fromJson(
            value,
            T::class.java
        )
    }

    inline fun <reified T> toJson(value: T): String {
        val gson = Gson()
        return gson.toJson(value)
    }
}