package com.babblingbrook.mtgcardsearch.data

import androidx.room.TypeConverter
import com.babblingbrook.mtgcardsearch.model.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class Converters {
    @TypeConverter
    fun cardFacesToString(cardFaces: List<CardFaces>?): String? {
        val gson = Gson()
        return gson.toJson(cardFaces)
    }

    @TypeConverter
    fun stringToCardFaces(json: String?): List<CardFaces>? {
        val gson = Gson()
        val listType: Type = object : TypeToken<CardFaces>() {}.type
        return gson.fromJson(json, listType)
    }

    @TypeConverter
    fun imageUriToString(imageUris: ImageUris?): String? {
        val gson = Gson()
        return gson.toJson(imageUris)
    }

    @TypeConverter
    fun stringToImageUris(json: String?): ImageUris? {
        val gson = Gson()
        return gson.fromJson(json, ImageUris::class.java)
    }

    @TypeConverter
    fun legalitiesToString(legalities: Legalities?): String? {
        val gson = Gson()
        return gson.toJson(legalities)
    }

    @TypeConverter
    fun stringToLegalities(json: String?): Legalities? {
        val gson = Gson()
        return gson.fromJson(json, Legalities::class.java)
    }

    @TypeConverter
    fun multiverseIdsToString(multiverseIds: List<Int>?): String? {
        val gson = Gson()
        return gson.toJson(multiverseIds)
    }

    @TypeConverter
    fun stringToMultiverseIds(json: String?): List<Int>? {
        val gson = Gson()
        val type: Type = object: TypeToken<List<Int>>(){}.type
        return gson.fromJson(json, type)
    }

    @TypeConverter
    fun colorsToString(colors: List<String>?): String? {
        val gson = Gson()
        return gson.toJson(colors)
    }

    @TypeConverter
    fun stringToColors(json: String?): List<String>? {
        val gson = Gson()
        val type: Type = object: TypeToken<List<String>>(){}.type
        return gson.fromJson(json, type)
    }

    @TypeConverter
    fun pricesToString(prices: Prices?): String? {
        val gson = Gson()
        return gson.toJson(prices)
    }

    @TypeConverter
    fun stringToPrices(json: String?): Prices? {
        val gson = Gson()
        return gson.fromJson(json, Prices::class.java)
    }

    @TypeConverter
    fun purchaseUrisToString(purchaseUris: PurchaseUris?): String? {
        val gson = Gson()
        return gson.toJson(purchaseUris)
    }

    @TypeConverter
    fun stringToPurchaseUris(json: String?): PurchaseUris? {
        val gson = Gson()
        return gson.fromJson(json, PurchaseUris::class.java)
    }

    @TypeConverter
    fun relatedUrisToString(relatedUris: RelatedUris?): String? {
        val gson = Gson()
        return gson.toJson(relatedUris)
    }

    @TypeConverter
    fun stringToRelatedUris(json: String?): RelatedUris? {
        val gson = Gson()
        return gson.fromJson(json, RelatedUris::class.java)
    }
}