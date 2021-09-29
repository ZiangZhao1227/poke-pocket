package com.example.pokepocket.view.ui.main

import android.location.Location

class PokemonCharacter {

    var titleOfPokemon : String? = null
    var message : String? = null
    var iconOfPokemon : Int? = null
    var location : Location? = null
    var isDefeated : Boolean? = false

    constructor(titleOfPokemon: String, message: String, iconOfPokemon: Int, location: Location) {

        this.titleOfPokemon = titleOfPokemon
        this.message = message
        this.iconOfPokemon = iconOfPokemon
        this.location = location
        
    }

}