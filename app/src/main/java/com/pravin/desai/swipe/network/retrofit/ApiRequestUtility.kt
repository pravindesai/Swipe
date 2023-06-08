package com.pravin.desai.swipe.network.retrofit

import android.util.Log
import com.pravin.desai.swipe.network.result.NetworkResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response

fun<T> callApiForResult(apiCall:suspend ()-> Response<T>) : Flow<NetworkResult<T?>> = flow {
    emit(NetworkResult.Loading)
    try {
        val response = apiCall()
        if (response.isSuccessful){
            emit(NetworkResult.Success(response.body()))
        }else{
            response.errorBody()?.close()
            emit(NetworkResult.Error(response.message()))

        }
    }catch (e:Exception){
        e.printStackTrace()
        emit(NetworkResult.Error(message = e.message.toString()))
    }
}
