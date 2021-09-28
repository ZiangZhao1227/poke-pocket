package com.example.pokepocket.network

import com.example.pokepocket.model.PokemonInfo
import com.example.pokepocket.model.PokemonResponse
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

fun createPokemonService() : IPokemonService {

    val okHttpClient = OkHttpClient
        .Builder()
        .build()

    val retrofit = Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl("https://pokeapi.co/api/v2/")
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    return retrofit.create(IPokemonService::class.java)
}


interface IPokemonService {
    @GET("pokemon")
    fun fetchPokemonList(
        @Query("limit") limit: Int = 900,
        @Query("offset") offset: Int = 0
    ): Call<PokemonResponse>

    @GET("pokemon/{name}")
    fun fetchPokemonDetails(
        @Path("name")
        name : String) : Call<PokemonInfo>
}