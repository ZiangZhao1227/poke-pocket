package com.example.pokepocket.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.pokepocket.model.Pokemon
import com.example.pokepocket.repository.MainRepository
import com.example.pokepocket.viewstate.ViewState

class MainActivityViewModel(mainRepository : MainRepository) : ViewModel() {

    val pokemonLiveData : LiveData<ViewState<List<Pokemon>>> = mainRepository.pokemonListLiveData

    init {
        mainRepository.getPokemonList()
    }
}