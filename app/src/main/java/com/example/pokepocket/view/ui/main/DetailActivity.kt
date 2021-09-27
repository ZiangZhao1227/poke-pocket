package com.example.pokepocket.view.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.pokepocket.R

class DetailActivity : AppCompatActivity() {

    companion object {
        const val ARG_POKEMON_NAME = "pokemon_name"
        const val ARG_POKEMON_IMAGE_URL = "pokemon_image_url"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
    }
}