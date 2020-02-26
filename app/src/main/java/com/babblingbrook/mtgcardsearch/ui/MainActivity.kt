package com.babblingbrook.mtgcardsearch.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.babblingbrook.mtgcardsearch.R
import dagger.android.AndroidInjection

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
