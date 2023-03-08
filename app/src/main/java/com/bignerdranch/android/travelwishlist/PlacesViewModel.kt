package com.bignerdranch.android.travelwishlist

import android.util.Log
import androidx.lifecycle.ViewModel

const val TAG = "PLACES_VIEW_MODEL"
class PlacesViewModel: ViewModel() {
    private val places = mutableListOf<Place>(Place("Aukland, NZ", "See where they filmed Lord of the rings"),
        Place("Patagonia, Chile", "Whale watch"))

    fun getPlaces(): List<Place> {
        return places
    }

    fun addNewPlace(place: Place, position: Int? = null): Int {
        if (places.any { placeName -> placeName.name.uppercase() == place.name.uppercase() }) {
            return -1
        }
        return if (position == null) {
            places.add(place)
            return places.lastIndex
        } else {
            places.add(position, place)
            return position
        }
    }

    fun addNewReason(place: Place, position: Int? = null): Int {
        if (places.any { placeReason -> placeReason.reason.uppercase() == place.reason.uppercase() }) {
            return -1
        }
        return if (position == null) {
            places.add(place)
            return places.lastIndex
        } else {
            places.add(position, place)
            return position
        }
    }

    fun movePlace(from: Int, to: Int) {
        val place = places.removeAt(from)
        places.add(to, place)
        Log.d(TAG, places.toString())
    }

    fun deletePlace(position: Int): Place {
        return places.removeAt(position)
    }
}