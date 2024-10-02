package com.example.movieproject.data.datasource.remote.api

import com.example.movieproject.base.BaseException
import com.example.movieproject.utils.toApiErrorBy
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response

open class ApiProvider {
    fun <T> apiRequest(apiCall: suspend () -> Response<T>): Flow<NetworkResource<T>> {
        return flow {
            // Emit loading state
            emit(NetworkResource(status = NetworkState.LOADING))

            try {
                val response = apiCall()

                if (response.isSuccessful && response.body() != null) {
                    // Emit success state with the data
                    emit(NetworkResource(status = NetworkState.SUCCESS, data = response.body()))
                } else {
                    // Emit failure state with error details from the response
                    val errorBody = response.errorBody()?.toApiErrorBy()?.message ?: "Unknown error"
                    emit(
                        NetworkResource(
                            status = NetworkState.FAILED,
                            error = BaseException(
                                statusCode = response.code(),
                                statusMessage = errorBody,
                                success = false
                            )
                        )
                    )
                }
            } catch (e: Exception) {
                // Handle any exceptions during the API call
                emit(
                    NetworkResource(
                        status = NetworkState.FAILED,
                        error = BaseException(
                            statusCode = 500, // or any relevant code for network failure
                            statusMessage = "No Internet Connection",
                            success = false
                        )
                    )
                )
            }
        }.flowOn(Dispatchers.IO) // Ensures the API request is done in the IO dispatcher
    }
}
