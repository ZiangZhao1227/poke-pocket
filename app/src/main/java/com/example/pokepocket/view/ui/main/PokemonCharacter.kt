package com.example.pokepocket.view.ui.main

import android.location.Location

class PokemonCharacter// access location in terms of coordinates
// access location in terms of coordinates
    (
    titleOfPokemon: String,
    message: String,
    iconOfPokemon: Int,
    latitude: Double,
    longitude: Double
) {

    var titleOfPokemon : String? = titleOfPokemon
    var message : String? = message
    var iconOfPokemon : Int? = iconOfPokemon
    var location : Location? = null
    var isDefeated = false

    init {
        location = Location("MyProvider")
        this.location?.latitude = latitude
        this.location?.longitude = longitude
    }

}