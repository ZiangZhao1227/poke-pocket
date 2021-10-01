package com.example.pokepocket.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.pokepocket.model.PokemonInfo
import com.example.pokepocket.viewstate.ViewState

class DetailActivityViewModel(private val detailRepository) : ViewModel() {

    val pokemonInfoData : LiveData<ViewState<PokemonInfo>> = detailRepository.pokemonDetailsLiveData


    fun fetchPokemonDetails(name: String) {
        detailRepository.getPokemonDetails(name)
    }

}