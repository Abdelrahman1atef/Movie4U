package com.example.movieproject.base

import com.google.gson.annotations.SerializedName

class BaseException(
    @SerializedName("status_code") val statusCode: Int? = null,
    @SerializedName("status_message") val statusMessage: String? = null,
    @SerializedName("success") val success: Boolean = false
) : Exception(statusMessage){

    companion object {
        fun networkError(): BaseException {
            return BaseException(null, "No Internet Connection", false)
        }
    }
}