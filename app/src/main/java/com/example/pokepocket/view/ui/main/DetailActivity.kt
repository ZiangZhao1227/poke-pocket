package com.example.pokepocket.view.ui.main

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.github.florent37.glidepalette.BitmapPalette
import com.github.florent37.glidepalette.GlidePalette
import com.example.pokepocket.R
import com.example.pokepocket.extensions.changeColor
import com.example.pokepocket.extensions.getTypeColor
import com.example.pokepocket.extensions.hide
import com.example.pokepocket.extensions.show
import com.example.pokepocket.model.PokemonInfo
import com.example.pokepocket.viewmodels.DetailActivityViewModel
import com.example.pokepocket.viewmodels.DetailActivityViewModelFactory
import com.example.pokepocket.viewstate.Error
import com.example.pokepocket.viewstate.Loading
import com.example.pokepocket.viewstate.Success
import com.example.pokepocket.viewstate.ViewState
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {
    private val viewmodelFactory by lazy { DetailActivityViewModelFactory(this) }
    private val viewModel: DetailActivityViewModel by viewModels {
        viewmodelFactory
    }

    companion object {
        const val ARG_POKEMON_NAME = "pokemon_name"
        const val ARG_POKEMON_IMAGE_URL = "pokemon_image_url"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        supportActionBar?.elevation = 0f

        val intent: Intent = intent
        val nameFromMainActivity = intent.getStringExtra(ARG_POKEMON_NAME) ?: "pikachu"
        val imageUrl = intent.getStringExtra(ARG_POKEMON_IMAGE_URL)

        Glide.with(this)
            .load(imageUrl)
            .listener(
                GlidePalette.with(imageUrl)
                    .use(BitmapPalette.Profile.MUTED_LIGHT)
                    .intoCallBack { palette ->
                        val rgb = palette?.dominantSwatch?.rgb
                        if (rgb != null) {
                            if(!resources.getBoolean(R.bool.is_tablet)){
                                pokemon_image_layout.setBackgroundColor(rgb)
                            }
                            changeColor(rgb)
                        }
                    }
                    .crossfade(true))
            .into(pokemon_image)
        pokemon_name.text = nameFromMainActivity

        viewModel.pokemonInfoData.observe(this, Observer<ViewState<PokemonInfo>> { viewState ->
            when(viewState){
                is Success -> {
                    detail_progress_bar.hide()
                    val pokemonInfo = viewState.data
                    if(pokemonInfo.types.size == 1) {
                        type_name_one.show()
                        type_name_one.text = pokemonInfo.types[0].type.name
                        type_name_one.setBackgroundColor(getColor(pokemonInfo.types[0].type.name.getTypeColor()))

                        type_name_two.hide()
                    }else {
                        type_name_one.show()
                        type_name_one.text = pokemonInfo.types[0].type.name
                        type_name_one.setBackgroundColor(getColor(pokemonInfo.types[0].type.name.getTypeColor()))

                        type_name_two.show()
                        type_name_two.text = pokemonInfo.types[1].type.name
                        type_name_two.setBackgroundColor(getColor(pokemonInfo.types[1].type.name.getTypeColor()))
                    }
                    height.text = pokemonInfo.getHeightString()
                    weight.text = pokemonInfo.getWeightString()

                    progress_hp.labelText = pokemonInfo.getHpString()
                    progress_hp.max = PokemonInfo.maxHp.toFloat()
                    progress_hp.progress = pokemonInfo.getHp().toFloat()

                    progress_attack.labelText = pokemonInfo.getAttackString()
                    progress_attack.max = PokemonInfo.maxAttack.toFloat()
                    progress_attack.progress = pokemonInfo.getAttack().toFloat()

                    progress_defense.labelText = pokemonInfo.getDefenseString()
                    progress_defense.max = PokemonInfo.maxDefense.toFloat()
                    progress_defense.progress = pokemonInfo.getDefense().toFloat()

                    progress_speed.labelText = pokemonInfo.getSpeedString()
                    progress_speed.max = PokemonInfo.maxSpeed.toFloat()
                    progress_speed.progress = pokemonInfo.getSpeed().toFloat()

                    progress_exp.labelText = pokemonInfo.getExpString()
                    progress_exp.max = PokemonInfo.maxExp.toFloat()
                    progress_exp.progress = pokemonInfo.getExp().toFloat()
                }
                is Error -> {
                    detail_progress_bar.hide()
                    Toast.makeText(this, viewState.errMsg, Toast.LENGTH_SHORT).show()
                }
                is Loading -> {
                    detail_progress_bar.show()
                }
            }



        })

        viewModel.fetchPokemonDetails(nameFromMainActivity)
    }
}