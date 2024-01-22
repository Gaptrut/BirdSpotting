package com.example.birds

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker


class BirdInfoAdapter(val context : Context) : GoogleMap.InfoWindowAdapter {


    val layoutInflater = LayoutInflater.from(context)


    override fun getInfoContents(p0: Marker): View? {
        return null
    }

    override fun getInfoWindow(marker : Marker): View? {
        val infoWindow = layoutInflater.inflate(R.layout.bird_info, null)

        val imageView = infoWindow.findViewById<ImageView>(R.id.birdImageView1)
        val titleView = infoWindow.findViewById<TextView>(R.id.nameTextView)
        val infoView = infoWindow.findViewById<TextView>(R.id.infoTextView)

        val place = marker.tag as? MapActivity.PlaceInfo

        titleView.text = place?.birdname
        infoView.text = place?.place
        if(place != null){
            imageView.setImageResource(place.image)
        }


        return infoWindow


    }

}