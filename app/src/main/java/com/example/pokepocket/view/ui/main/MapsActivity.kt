package com.example.pokepocket.view.ui.main

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.example.pokepocket.R
import com.example.pokepocket.databinding.ActivityMapsBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.CameraUpdate




// import com.example.pokepocket.view.ui.main.databinding.ActivityMapsBinding

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    private val USER_LOCATION_REQUEST_CODE = 33
    private var playerLocation: Location? = null
    private var oldLocationOfPlayer: Location? = null
    private var locationManager: LocationManager? = null
    private var locationListener: PlayerLocationListener? = null
    private var pokemonCharacters: ArrayList<PokemonCharacter> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        locationListener = PlayerLocationListener()

        requestLocationPermission()
        initializePokemonCharacters()
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and wreturned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

    }

    // ask user's permission
    private fun requestLocationPermission() {

        if (Build.VERSION.SDK_INT >= 23) {

            if (ActivityCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                ) !=
                PackageManager.PERMISSION_GRANTED
            ) {

                requestPermissions(
                    arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                    USER_LOCATION_REQUEST_CODE
                )
                return // for not reaching the accessUserLocation() to avoid crash
            }
        }
        accessUserLocation()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {

        if (requestCode == USER_LOCATION_REQUEST_CODE) {
            // this array holds the results whether they use it has given that the app the permission to access the location or not
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                accessUserLocation()
            }

        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    inner class PlayerLocationListener : LocationListener {

        constructor() {
            // whenever the app is referring to players location, it is not going to end up with null pointer exception
            playerLocation = Location("MyProvider")
            playerLocation?.latitude = 60.17
            playerLocation?.longitude = 24.95
        }

        override fun onLocationChanged(updatedLocation: Location?) {
            // gets access to the updated location
            playerLocation = updatedLocation

        }

        override fun onStatusChanged(p0: String?, p1: Int, p2: Bundle?) {

        }

        override fun onProviderEnabled(p0: String?) {

        }

        override fun onProviderDisabled(p0: String?) {

        }

    }

    private fun initializePokemonCharacters() {
        // refers to the class PokemonCharacter
        pokemonCharacters.add(
            PokemonCharacter(
                "This is Pikachu",
                "I'm hungry",
                R.drawable.img_pikachu,
                60.22436,
                24.75823
            )
        )
        pokemonCharacters.add(
            PokemonCharacter(
                "This is Haunter",
                "Be afraid!",
                R.drawable.img_haunter,
                60.20603,
                24.65421
            )
        )
        pokemonCharacters.add(
            PokemonCharacter(
                "This is Vaporeon",
                "I'm thirsty",
                R.drawable.img_vaporeon,
                60.21467,
                24.81200
            )
        )
        pokemonCharacters.add(
            PokemonCharacter(
                "This is Zapdos",
                "I'm the most powerful!",
                R.drawable.img_zapdos,
                60.14438,
                24.98964
            )
        )
        pokemonCharacters.add(
            PokemonCharacter(
                "This is Charizard",
                "I will burn you!",
                R.drawable.img_charizard,
                60.22396,
                24.75841
            )
        )
    }

    private fun accessUserLocation() {
        // calls requestLocationUpdates if the location of player changed in 2 meters, updates every 1 second
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        locationManager?.requestLocationUpdates(
            LocationManager.NETWORK_PROVIDER,
            1000,
            2f,
            locationListener!!
        )
        // for executing the run function
        var newThread = NewThread()
        newThread.start()
    }

    // NewThread class is going to accept for to show the player on the map
    inner class NewThread : Thread {
        // NewThread's constructor
        constructor() : super() {
            // above was null, so I assigned a valid value to oldLocationOfPlayer
            oldLocationOfPlayer = Location("MyProvider")
            oldLocationOfPlayer?.latitude = 0.0
            oldLocationOfPlayer?.longitude = 0.0
        }
        // overrides the run fun of the NewThread class
        override fun run() {
            super.run()
            // for always execute try block
            while (true) {
                // if the player, hasn't moved at all
                if (oldLocationOfPlayer?.distanceTo(playerLocation) == 0f) {
                    continue // for go back to while loop
                }

                oldLocationOfPlayer = playerLocation

                try {

                    runOnUiThread {
                        // will clear the map everytime
                        mMap.clear()
                        // Add a marker for player's location
                        val plrLocation = LatLng(playerLocation!!.latitude, playerLocation!!.longitude)
                        mMap.addMarker(
                            MarkerOptions().position(plrLocation).title("Player").snippet("Let's Go !")
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.player))
                        )
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(plrLocation,14.0f))

                        for (pokemonCharacterIndex in 0.until(pokemonCharacters.size)) {

                            var pc = pokemonCharacters[pokemonCharacterIndex]
                            //in this case it will show the pokemon character on the map
                            if (pc.isDefeated == false) {

                                var pcLocation = LatLng(pc.location!!.latitude, pc.location!!.longitude)
                                mMap.addMarker(MarkerOptions()
                                    .position(pcLocation)
                                    .title(pc.titleOfPokemon)
                                    .snippet(pc.message)
                                    .icon(BitmapDescriptorFactory.fromResource(pc.iconOfPokemon!!)))

                                // when u catch the pokemon, when usersLocation == pokemonLocation, it will disappear from map
                                if (playerLocation!!.distanceTo(pc.location) < 1) {

                                    Toast.makeText(this@MapsActivity, "${pc.titleOfPokemon} is eliminated", Toast.LENGTH_SHORT).show()
                                    pc.isDefeated = true
                                    //then it updates the array list
                                    pokemonCharacters[pokemonCharacterIndex] = pc
                                }
                            }
                        }
                    }
                    Thread.sleep(1000)

                } catch (exception: Exception) {

                    exception.printStackTrace()
                }
            }
        }
    }
}