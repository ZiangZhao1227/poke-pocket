package com.example.pokepocket.view.ui.main

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pokepocket.R
import com.example.pokepocket.extensions.changeColor
import com.example.pokepocket.extensions.hide
import com.example.pokepocket.extensions.show
import com.example.pokepocket.model.Pokemon
import com.example.pokepocket.view.adapter.PokemonListAdapter
import com.example.pokepocket.viewmodels.MainActivityViewModel
import com.example.pokepocket.viewmodels.MainActivityViewModelFactory
import com.example.pokepocket.viewstate.Error
import com.example.pokepocket.viewstate.Loading
import com.example.pokepocket.viewstate.Success
import com.example.pokepocket.viewstate.ViewState
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val viewmodelFactory by lazy { MainActivityViewModelFactory(this) }
    private val viewModel: MainActivityViewModel by viewModels {
        viewmodelFactory
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val pokemonList: RecyclerView = findViewById(R.id.pokemon_recycler_view)
        pokemonList.layoutManager = GridLayoutManager(this, 2)

        val pokemonListAdapter = PokemonListAdapter(this)
        pokemonList.adapter = pokemonListAdapter

        //Create an observer which updates UI in after network calls
        viewModel.pokemonLiveData.observe(this, Observer<ViewState<List<Pokemon>>> { viewState ->
            when (viewState) {
                is Success -> {
                    main_progress_bar.hide()
                    pokemonListAdapter.setPokemonList(viewState.data)
                }
                is Error -> {
                    main_progress_bar.hide()
                    Toast.makeText(this, viewState.errMsg, Toast.LENGTH_SHORT).show()
                }
                is Loading -> {
                    main_progress_bar.show()
                }
            }

        })
        changeColor(getColor(R.color.colorPrimary))
    }

    override fun onResume() {
        super.onResume()
        if (searchView != null) {
            searchView.clearFocus();
        }
    }
}