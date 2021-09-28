package com.example.pokepocket.repository

import android.os.AsyncTask
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.pokepocket.model.Pokemon
import com.example.pokepocket.network.IPokemonService
import com.example.pokepocket.persistence.PokemonDao
import com.example.pokepocket.viewstate.Error
import com.example.pokepocket.viewstate.Loading
import com.example.pokepocket.viewstate.Success
import com.example.pokepocket.viewstate.ViewState
import java.lang.Exception

class MainRepository(
    private val pokemonService: IPokemonService,
    private val pokemonDao : PokemonDao): IMainRepository {

    private val TAG = MainRepository::class.java.simpleName

    /** LIVE DATA **/
    private val _pokemonListLiveData : MutableLiveData<ViewState<List<Pokemon>>> = MutableLiveData()
    val pokemonListLiveData :LiveData<ViewState<List<Pokemon>>> = _pokemonListLiveData

    override fun getPokemonList() {
        FetchPokemonListTask(_pokemonListLiveData, pokemonService, pokemonDao).execute()
    }

    class FetchPokemonListTask(private val pokemonLiveData: MutableLiveData<ViewState<List<Pokemon>>>,
                               private val pokemonService: IPokemonService,
                               private val pokemonDao: PokemonDao): AsyncTask<Void, Void, List<Pokemon>>() {

        private val TAG = FetchPokemonListTask::class.java.simpleName

        override fun onPreExecute() {
            super.onPreExecute()
            pokemonLiveData.value = Loading
        }

        override fun doInBackground(vararg p0: Void?): List<Pokemon>? {
            return try {
                val response = pokemonService.fetchPokemonList().execute()
                if (response.isSuccessful && response.body() != null) {
                    val pokemons = response.body()!!.results
                    pokemonDao.insertPokemons(pokemons)
                    pokemonDao.getPokemons()
                } else {
                    null
                }
            } catch (e: Exception) {
                Log.e(TAG, e.message)
                null
            }
        }

        override fun onPostExecute(result: List<Pokemon>?) {
            super.onPostExecute(result)
            if (result == null) {
                pokemonLiveData.value = Error("Error fetching list of pokemon, please check internet connection")
            } else {
                pokemonLiveData.value = Success(result)
            }
        }
    }

}