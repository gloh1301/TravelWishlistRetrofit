package com.bignerdranch.android.travelwishlist

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

const val TAG = "PLACES_VIEW_MODEL"
class PlacesViewModel: ViewModel() {

    /*
    private val places = mutableListOf<Place>(Place("Aukland, NZ", "See where they filmed Lord of the rings"),
        Place("Patagonia, Chile", "Whale watch", starred = true),
        Place("Ely, MN", starred = true))
    */


    private val placeRepository = PlaceRepository()

    val allPlaces = MutableLiveData<List<Place>>(listOf<Place>())
    val userMessage = MutableLiveData<String>()

    init {
        getPlaces()
    }

    fun getPlaces() {
        viewModelScope.launch {
            val apiResult = placeRepository.getAllPlaces()
            if (apiResult.status == ApiStatus.SUCCESS) {
                allPlaces.postValue(apiResult.data)
            }
            userMessage.postValue(apiResult.message)
        }
    }

    fun addNewPlace(place: Place){
        viewModelScope.launch {
            val apiResult = placeRepository.addPlace(place)
            updateUI(apiResult)
        }
    }

    fun updatePlace(place: Place) {
        viewModelScope.launch {
            val apiResult = placeRepository.updatePlace(place)
            updateUI(apiResult)
        }
    }

    fun deletePlace(place: Place) {
        viewModelScope.launch {
            val apiResult = placeRepository.deletePlace(place)
            updateUI(apiResult)
        }
    }

    private fun updateUI(result: ApiResult<Any>) {
        if (result.status == ApiStatus.SUCCESS) {
            getPlaces()
        }
        userMessage.postValue(result.message)
    }

}