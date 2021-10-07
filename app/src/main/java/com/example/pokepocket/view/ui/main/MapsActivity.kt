package com.example.pokepocket.view.ui.main

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.pokepocket.R
import com.example.pokepocket.databinding.ActivityMapsBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_maps.*
import java.util.*
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt
import com.google.android.gms.maps.model.Marker



class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private val USER_LOCATION_REQUEST_CODE = 33
    private var playerLocation: Location? = null
    private var oldLocationOfPlayer: Location? = null
    private var locationManager: LocationManager? = null
    private var locationListener: PlayerLocationListener? = null
    private var currentLocationMarker: Marker? = null
    private var pokemonCharacters: ArrayList<LatLng> = ArrayList()
    private var currentPlayerLatitude = 0.0
    private var currentPlayerLongtitude = 0.0




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
        //if no pokemons in shared pref
        show_pokemon.setOnClickListener {
            setMarkerOnRandomLocations(mMap)
        }

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

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) !=
            PackageManager.PERMISSION_GRANTED
        ) {

            requestPermissions(
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                USER_LOCATION_REQUEST_CODE
            )
            return // for not reaching the accessUserLocation() to avoid crash
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

    inner class PlayerLocationListener// whenever the app is referring to players location, it is not going to end up with null pointer exception
        : LocationListener {

        init {
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

/*    private fun saveData() {
        // method for saving the data in array list.
        // creating a variable for storing data in
        // shared preferences.
        val sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE)
        // creating a variable for editor to
        // store data in shared preferences.
        val editor = sharedPreferences.edit()
        // creating a new variable for gson.
        val gson = Gson()
        // getting data from gson and storing it in a string.
        val json = gson.toJson(pokemonCharacters)
        editor.putString("pokemon", json)
        // below line is to apply changes
        // and save data in shared prefs.
        editor.apply()
        Log.d("saveee","save data from array list $json")
    }*/

    private fun getRandomLocation(x0: Double, y0: Double, radius: Int) : LatLng {
        val random = Random()

        // Convert radius from meters to degrees
        val radiusInDegrees = (radius / 111000f).toDouble()
        val u = random.nextDouble()
        val v = random.nextDouble()
        val w = radiusInDegrees * sqrt(u)
        val t = 2 * Math.PI * v
        val x = w * cos(t)
        val y = w * sin(t)

        // Adjust the x-coordinate for the shrinking of the east-west distances
        val newX = x / cos(Math.toRadians(y0))
        val foundLongitude = newX + x0
        val foundLatitude = y + y0
        Log.d("txt","Longitude: $foundLongitude  Latitude: $foundLatitude")
        pokemonCharacters.add(LatLng(foundLongitude,foundLatitude))
        Log.d("show","$pokemonCharacters")
        return LatLng(foundLongitude,foundLatitude)
    }

    private fun setMarkerOnRandomLocations(map: GoogleMap) {
        for (i in 0..4) {
            when(i){
                0 -> changeIcon(map, R.drawable.img_pikachu,"Pikachu")
                1 -> changeIcon(map, R.drawable.img_haunter,"Haunter")
                2 -> changeIcon(map, R.drawable.img_charizard,"Charizard")
                3 -> changeIcon(map, R.drawable.img_vaporeon,"Vaporeon")
                4 -> changeIcon(map, R.drawable.img_zapdos,"Zapdos")
            }

        }
    }

    private fun changeIcon(map: GoogleMap, drawable: Int,content: String){
        map.addMarker(
            MarkerOptions().position(
                getRandomLocation(
                    currentPlayerLatitude,
                    currentPlayerLongtitude,
                    55
                )
            )
                .title(content)
        )?.setIcon(
            getBitmapDescriptorFromVector(
                this,
                drawable
            )
        )
    }

    private fun getBitmapDescriptorFromVector(
        context: Context,
        vectorDrawableResourceId: Int
    ): BitmapDescriptor? {

        val vectorDrawable = ContextCompat.getDrawable(context, vectorDrawableResourceId)
        val bitmap = Bitmap.createBitmap(
            vectorDrawable!!.intrinsicWidth,
            vectorDrawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        vectorDrawable.setBounds(0, 0, canvas.width, canvas.height)
        vectorDrawable.draw(canvas)

        return BitmapDescriptorFactory.fromBitmap(bitmap)
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
        val newThread = NewThread()
        newThread.start()
    }

    // NewThread class is going to accept for to show the player on the map
    inner class NewThread// above was null, so I assigned a valid value to oldLocationOfPlayer// NewThread's constructor
        : Thread() {
        init {
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
                        // Add a marker for player's location
                        val plrLocation =
                            LatLng(playerLocation!!.latitude, playerLocation!!.longitude)
                        currentPlayerLatitude = playerLocation!!.latitude
                        currentPlayerLongtitude = playerLocation!!.longitude
                        currentLocationMarker?.remove()
                        currentLocationMarker = mMap.addMarker(
                            MarkerOptions().position(plrLocation).title("Player")
                                .snippet("Let's Go !")
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.player))
                        )
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(plrLocation, 14.0f))

                        for (pokemonCharacterIndex in 0.until(pokemonCharacters.size)) {
                            val pokeLocation = LatLng(pokemonCharacters[pokemonCharacterIndex].latitude,pokemonCharacters[pokemonCharacterIndex].latitude)
                            Log.d("dis","${pokeLocation}")
                            val PokeLocation = Location("pokemonLocation")
                            PokeLocation.latitude = pokemonCharacters[pokemonCharacterIndex].latitude
                            PokeLocation.longitude = pokemonCharacters[pokemonCharacterIndex].longitude
                            Log.d("distance","${playerLocation!!.distanceTo(PokeLocation)}")
                        if ((playerLocation!!.distanceTo(PokeLocation)) < 5 + playerLocation!!.accuracy) {

                            mMap.setOnMarkerClickListener {
                                Toast.makeText(this@MapsActivity,"pokemon removed",Toast.LENGTH_SHORT).show()
                                true }
                        }
                            }
                    }
                    sleep(1000)
                } catch (exception: Exception) {
                    exception.printStackTrace()
                }
            }
        }
    }
}