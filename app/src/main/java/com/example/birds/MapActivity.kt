package com.example.birds


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.CameraUpdateFactory.zoomIn
import com.google.android.gms.maps.CameraUpdateFactory.zoomOut
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

       val button = findViewById<ImageButton>(R.id.backButton)
        button.setOnClickListener {
            val intent = Intent(this, BirdListActivity::class.java)
            startActivity(intent)
        }

        val zoomInButton: ImageButton = findViewById(R.id.zoomInButton)
        val zoomOutButton: ImageButton = findViewById(R.id.zoomOutButton)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        zoomInButton.setOnClickListener { zoomIn() }
        zoomOutButton.setOnClickListener { zoomOut() }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap


        val adapter = BirdInfoAdapter(this)
        mMap.setInfoWindowAdapter(adapter)

        createPlaces()
    }


    private fun zoomIn() {
        mMap.animateCamera(CameraUpdateFactory.zoomIn())
    }

    private fun zoomOut() {
        mMap.animateCamera(CameraUpdateFactory.zoomOut())
    }


    fun createPlaces() {


        val p1 = PlaceInfo("Sångsvan", "Rysjön", LatLng(59.18, 15.36), R.drawable.sangsvanar)
        val p2 = PlaceInfo("Stare", "Oknö", LatLng(57.00, 16.50), R.drawable.starar2)

        val placeList = listOf<PlaceInfo>(p1, p2)
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(placeList[0].position, 5f))

        for (place in placeList) {
            val marker = mMap.addMarker(MarkerOptions().position(place.position))
            marker?.tag = place
        }

    }

    data class PlaceInfo(
        val birdname: String,
        val place: String,
        val position: LatLng,
        val image: Int
    )

}

