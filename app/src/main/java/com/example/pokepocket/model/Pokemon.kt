package com.example.pokepocket.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

@Entity
data class Pokemon(
    var page: Int = 0,
    @field:Json(name = "name") @PrimaryKey val name: String,
    @field:Json(name = "url") val url: String
){

    fun getImageUrl(): String {
        val index = url.split("/".toRegex()).dropLast(1).last()
        return "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${index}.png"
        //return url
    }
}