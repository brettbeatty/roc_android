package com.brettbeatty.roc.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.brettbeatty.roc.R

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class EventActivity : AppCompatActivity() {
    private lateinit var map: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event)
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync {
            map = it
            val stadium = LatLng(40.257609, -111.654612)
            val options = MarkerOptions().position(stadium).title("LaVell Edwards Stadium")
            map.addMarker(options)
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(stadium, 15.0f))
        }
    }
}
