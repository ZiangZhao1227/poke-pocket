package com.example.pokepocket.view.ui.main

import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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

// import com.example.pokepocket.view.ui.main.databinding.ActivityMapsBinding

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    private val USER_LOCATION_REQUEST_CODE = 33
    private var playerLocation: Location? = null
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
            playerLocation?.latitude = 0.0
            playerLocation?.longitude = 0.0
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
                1.651729,
                31.996134
            )
        )
        pokemonCharacters.add(
            PokemonCharacter(
                "This is Haunter",
                "Be afraid!",
                R.drawable.img_haunter,
                27.404523,
                29.647654
            )
        )
        pokemonCharacters.add(
            PokemonCharacter(
                "This is Vaporeon",
                "I'm thirsty",
                R.drawable.img_vaporeon,
                10.492703,
                10.709112
            )
        )
        pokemonCharacters.add(
            PokemonCharacter(
                "This is Zapdos",
                "I'm the most powerful!",
                R.drawable.img_zapdos,
                28.220750,
                1.898764
            )
        )
    }

    private fun accessUserLocation() {
        // calls requestLocationUpdates if the location of player changed in 2 meters, updates every 2 seconds
        locationManager?.requestLocationUpdates(
            LocationManager.GPS_PROVIDER,
            2000, 2f, locationListener!!
        )
        // for executing the run function
        var newThread = NewThread()
        newThread.start()
    }

    // NewThread class is going to accept for to show the player on the map
    inner class NewThread : Thread {
        // NewThread's constructor
        constructor() : super() {


        }

        // overrides the run fun of the NewThread class
        override fun run() {
            super.run()

            runOnUiThread {
                // Add a marker for player's location
                val plrLocation = LatLng(playerLocation!!.latitude, playerLocation!!.longitude)
                mMap.addMarker(
                    MarkerOptions().position(plrLocation).title("Player").snippet("Let's Go !")
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.player))
                )
                mMap.moveCamera(CameraUpdateFactory.newLatLng(plrLocation))
            }
        }
    }
}