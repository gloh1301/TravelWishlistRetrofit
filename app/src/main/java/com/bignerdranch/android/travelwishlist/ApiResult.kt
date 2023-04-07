package com.bignerdranch.android.travelwishlist

enum class ApiStatus {
    SUCCESS,
    SERVER_ERROR,
    NETWORK_ERROR
}

data class ApiResult<out T>(val status: ApiStatus, val data: T?, val message: String?)