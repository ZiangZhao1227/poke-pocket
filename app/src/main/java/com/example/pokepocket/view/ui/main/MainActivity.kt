package com.example.pokepocket.view.ui.main

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pokepocket.R
import com.example.pokepocket.extensions.changeColor
import com.example.pokepocket.extensions.hide
import com.example.pokepocket.extensions.show
import com.example.pokepocket.model.Pokemon
import com.example.pokepocket.services.ForegroundService
import com.example.pokepocket.view.adapter.PokemonListAdapter
import com.example.pokepocket.viewmodels.MainActivityViewModel
import com.example.pokepocket.viewmodels.MainActivityViewModelFactory
import com.example.pokepocket.viewstate.Error
import com.example.pokepocket.viewstate.Loading
import com.example.pokepocket.viewstate.Success
import com.example.pokepocket.viewstate.ViewState
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    // Using by lazy so the database and the repository are only created when they're needed rather than when the application starts
    private val viewmodelFactory by lazy { MainActivityViewModelFactory(this) }
    //create viewModel by using viewModels delegate, passing in an instance of our viewModelFactory.
    private val viewModel: MainActivityViewModel by viewModels {
        viewmodelFactory
    }
    lateinit var receiver: AirplaneModeChangedReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        receiver = AirplaneModeChangedReceiver()

        IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED).also {
            registerReceiver(receiver, it)
        }


    //to display how many Pokemons will display in one raw
        val pokemonList: RecyclerView = findViewById(R.id.pokemon_recycler_view)
        pokemonList.layoutManager = GridLayoutManager(this, 2)

        val pokemonListAdapter = PokemonListAdapter(this)
        pokemonList.adapter = pokemonListAdapter

        //Create an observer which updates UI in after network calls
        //The onChanged() method (the default method for our Lambda) fires when the observed data changes and the activity is in the foreground
        viewModel.pokemonLiveData.observe(this, Observer<ViewState<List<Pokemon>>> { viewState ->
            when (viewState) {
                is Success -> {
                    main_progress_bar.hide()
                    startService()
                    pokemonListAdapter.setPokemonList(viewState.data)
                }
                is Error -> {
                    main_progress_bar.hide()
                    stopService()
                    Toast.makeText(this, viewState.errMsg, Toast.LENGTH_SHORT).show()
                }
                is Loading -> {
                    main_progress_bar.show()
                }
            }

        })
        changeColor(getColor(R.color.colorPrimary))

        floating_action_button.setOnClickListener{
            val intent = Intent(this, MapsActivity::class.java)
            startActivity(intent)
        }

    }

    private fun startService() {
        val serviceIntent = Intent(this, ForegroundService::class.java)
        serviceIntent.putExtra("inputExtra", "Internet connected successfully")
        ContextCompat.startForegroundService(this, serviceIntent)
    }

    private fun stopService() {
        val serviceIntent = Intent(this, ForegroundService::class.java)
        stopService(serviceIntent)
    }


    override fun onResume() {
        super.onResume()
        if (searchView != null) {
            searchView.clearFocus();
        }
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(receiver)
    }
}