package com.bignerdranch.android.travelwishlist

import android.util.Log
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PlaceRepository {

    private val TAG = "PLACE_REPOSITORY"

    private val baseURL = "https://claraj.pythonanywhere.com/api/"

    private val client: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(AuthorizationHeaderInterceptor())
        .build()

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(baseURL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val placeService = retrofit.create(PlaceService::class.java)

    suspend fun <T: Any> apiCall(apiCallFunction: suspend () -> Response<T>, successMessage: String?, failMessgae: String?): ApiResult<T> {
        try {
            // val response = placeService.getAllPlaces()
            val response = apiCallFunction.invoke()

            if (response.isSuccessful) {
                Log.d(TAG, "Response body ${response.body()}")
                return ApiResult(ApiStatus.SUCCESS, response.body(), successMessage)
            } else {
                Log.e(TAG, "Server error ${response.errorBody()}")
                return ApiResult(ApiStatus.SERVER_ERROR, null, failMessgae)
            }
        } catch (ex: Exception) {
            Log.e(TAG, "Error connecting to API server", ex)
            return ApiResult(ApiStatus.NETWORK_ERROR, null, "Can't connect to server")
        }
    }

    suspend fun getAllPlaces(): ApiResult<List<Place>> {
        return apiCall(placeService::getAllPlaces,
            null,
            "Error fetching places from server"
        )
    }

    suspend fun addPlace(place: Place): ApiResult<Place> {
        return apiCall(
            {placeService.addPlace(place)},
            "Place added!",
            "Error adding place - is name unique?"
        )
    }

    suspend fun updatePlace(place: Place): ApiResult<Place> {
        if (place.id == null) {
            Log.e(TAG, "Error - trying to update place with no ID")
            return ApiResult(ApiStatus.SERVER_ERROR, null, "Error - trying to update place with no ID")
        } else {
            return apiCall(
                {placeService.updatePlace(place, place.id)},
                "Place updated",
                "Error updating place"
            )
        }
    }

    suspend fun deletePlace(place: Place): ApiResult<String> {
        if (place.id == null) {
            Log.e(TAG, "Error - trying to delete place with no ID")
            return ApiResult(ApiStatus.SERVER_ERROR, null, "Error - trying to delete place with no ID")
        } else {
            return apiCall(
                {placeService.deletePlace(place.id)},
                "Place deleted",
                "Error deleting place"
            )
        }
    }
}