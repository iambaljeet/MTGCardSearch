package com.babblingbrook.mtgcardsearch.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue
import androidx.annotation.Keep

data class CardIdentifier(val identifiers: List<Identifiers>)

data class Identifiers(val name: String)

@Entity
@Parcelize
@Keep
data class Card(
    @PrimaryKey
    val id: String,
    val arena_id: Int?,
    val lang: String,
    val mtgo_id: Int?,
    val multiverse_ids: List<Int>?,
    val tcgplayer_id: Int?,
    val `object`: String,
    val oracle_id: String,
    val prints_search_uri: String,
    val rulings_uri: String,
    val scryfall_uri: String,
    val uri: String,
    val card_faces: @RawValue List<CardFaces>?,
    val cmc: Int,
    val colors: List<String>?,
    val color_identity: List<String>,
    val edhrec_rank: Int?,
    val foil: Boolean,
    val hand_modifier: String?,
    val layout: String,
    val legalities: @RawValue Legalities,
    val life_modifier: String?,
    val loyalty: String?,
    val mana_cost: String?,
    val name: String,
    val nonfoil: Boolean,
    val oracle_text: String?,
    val oversized: Boolean,
    val power: String?,
    val reserved: Boolean,
    val toughness: String?,
    val type_line: String,
    val artist: String?,
    val booster: Boolean,
    val border_color: String,
    val card_back_id: String,
    val collector_number: String,
    val digital: Boolean,
    val flavor_text: String?,
    val frame: String,
    val full_art: Boolean,
    val games: List<String>,
    val highres_image: Boolean,
    val illustration_id: String?,
    val image_uris: @RawValue ImageUris?,
    val prices: @RawValue Prices,
    val printed_name: String?,
    val printed_text: String?,
    val printed_type_line: String?,
    val promo: Boolean,
    val promo_types: List<String>?,
    val purchase_uris: @RawValue PurchaseUris,
    val rarity: String,
    val related_uris: @RawValue RelatedUris,
    val released_at: String,
    val scryfall_set_uri: String,
    val set_name: String,
    val set_search_uri: String,
    val set_type: String,
    val set_uri: String,
    val `set`: String,
    val story_spotlight: Boolean,
    val textless: Boolean,
    val variation: Boolean,
    val variation_of: String?,
    val watermark: String?,
    var isFavorite: Boolean = false
) : Parcelable

@Keep
data class CardFaces(
    val artist: String?,
    val color_indicator: List<String>?,
    val colors: List<String>?,
    val flavor_text: String?,
    val illustration_id: String?,
    val image_uris: @RawValue ImageUris?,
    val loyalty: String?,
    val mana_cost: String,
    val name: String,
    val oracle_text: String?,
    val power: String?,
    val printed_name: String?,
    val printed_text: String?,
    val printed_type_line: String?,
    val toughness: String?,
    val type_line: String,
    val watermark: String?
)

@Keep
data class ImageUris(
    val art_crop: String,
    val border_crop: String,
    val large: String,
    val normal: String,
    val png: String,
    val small: String
)

@Keep
data class Legalities(
    val brawl: String,
    val commander: String,
    val duel: String,
    val future: String,
    val historic: String,
    val legacy: String,
    val modern: String,
    val oldschool: String,
    val pauper: String,
    val penny: String,
    val pioneer: String,
    val standard: String,
    val vintage: String
)

@Keep
data class Prices(
    val eur: Any,
    val tix: Any,
    val usd: Any,
    val usd_foil: Any
)

@Keep
data class PurchaseUris(
    val cardhoarder: String,
    val cardmarket: String,
    val tcgplayer: String
)

@Keep
data class RelatedUris(
    val edhrec: String,
    val gatherer: String,
    val mtgtop8: String,
    val tcgplayer_decks: String
)