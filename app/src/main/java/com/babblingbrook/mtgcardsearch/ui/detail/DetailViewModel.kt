package com.babblingbrook.mtgcardsearch.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.babblingbrook.mtgcardsearch.model.Card

class DetailViewModel : ViewModel() {

    private val _card = MutableLiveData<Card>()

    val card: LiveData<Card> get() = _card

    fun setCardData(card: Card?) {
        _card.value = card
    }
}
