package com.babblingbrook.mtgcardsearch.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.babblingbrook.mtgcardsearch.model.Card

class DetailViewModel : ViewModel() {

    private val _cardLiveData = MutableLiveData<Card>()

    fun setCardData(card: Card?) {
        _cardLiveData.value = card
    }

    fun getCardData() : LiveData<Card> {
        return _cardLiveData
    }
}
