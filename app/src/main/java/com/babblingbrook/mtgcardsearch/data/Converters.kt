package com.babblingbrook.mtgcardsearch.data

import androidx.room.TypeConverter
import com.babblingbrook.mtgcardsearch.model.Card
import com.babblingbrook.mtgcardsearch.model.CardFace
import com.babblingbrook.mtgcardsearch.model.ImageUris
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class Converters {
    @TypeConverter
    fun cardFacesToString(cardFaces: List<CardFace>?): String? {
        val gson = Gson()
        return if (cardFaces == null) null else gson.toJson(cardFaces)
    }

    @TypeConverter fun stringToCardFaces(json: String?): List<CardFace>? {
        val gson = Gson()
        val listType: Type = object: TypeToken<CardFace>() {}.type
        if (json.isNullOrEmpty()) {
            return null
        } else {
            return gson.fromJson(json, listType)
        }
    }

    @TypeConverter fun imageUriToString(imageUris: ImageUris?): String? {
        val gson = Gson()
        return if (imageUris == null) null else gson.toJson(imageUris)
    }

    @TypeConverter fun stringToImageUris(json: String): ImageUris {
        val gson = Gson()
        return gson.fromJson(json, ImageUris::class.java)
    }
}