package com.bignerdranch.android.travelwishlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

interface OnListItemClickedListener {
    fun onMapRequestButtonClicked(places: Place)
    fun onStarredStatusChanged(place: Place, isStarred: Boolean)
}

class PlaceRecyclerAdapter(private val places: List<Place>,
                           private val onListItemClickedListener: OnListItemClickedListener):
    RecyclerView.Adapter<PlaceRecyclerAdapter.ViewHolder>() {

    // manages one view - one list item - sets the actual data in the view
    inner class ViewHolder(private val view: View): RecyclerView.ViewHolder(view) {
        fun bind(place: Place) {
            val placeNameTextView: TextView = view.findViewById(R.id.place_name)
            placeNameTextView.text = place.name

            val reasonTextView: TextView = view.findViewById(R.id.reason_name)
            reasonTextView.text = place.reason

            val mapIcon: ImageView = view.findViewById(R.id.map_icon)

            mapIcon.setOnClickListener {
                onListItemClickedListener.onMapRequestButtonClicked(place)
            }

            val starCheck = view.findViewById<CheckBox>(R.id.star_check)
            starCheck.setOnClickListener(null)
            starCheck.isChecked = place.starred
            starCheck.setOnClickListener {
                onListItemClickedListener.onStarredStatusChanged(place, starCheck.isChecked)
            }
        }
    }

    // called by the recycler view to create as many view holders that are needed to
    // display the list on screen
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.place_list_item, parent, false)
        return ViewHolder(view)
    }

    // called by the recycler view to set the data for one list item, by position
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val place = places[position]
        holder.bind(place)
    }

    override fun getItemCount(): Int {
        return places.size
    }
}