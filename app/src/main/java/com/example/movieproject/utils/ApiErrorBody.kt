package com.example.movieproject.utils

import okhttp3.ResponseBody
import org.json.JSONObject

class ApiErrorBody {
    var message: String? = null
}

fun ResponseBody.toApiErrorBy(): ApiErrorBody {
    val errorBody = ApiErrorBody()
    try {
        val obj = JSONObject(this.string())
        errorBody.message = obj.getString("status_message")
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return errorBody
}