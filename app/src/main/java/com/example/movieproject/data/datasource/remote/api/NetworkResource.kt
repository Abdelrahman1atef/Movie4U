package com.example.movieproject.data.datasource.remote.api

import com.example.movieproject.base.BaseException

open class NetworkResource<T>(
    val status: NetworkState,
    val data: T? = null,
    val error: BaseException? = null
) {
    class Loading<T>() : NetworkResource<Boolean>(NetworkState.LOADING)
    class Success<T>( data: T) : NetworkResource<T>(NetworkState.SUCCESS, data)
    class Failure<T>( error: BaseException) :
        NetworkResource<BaseException>(NetworkState.FAILED, error )


}