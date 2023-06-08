package com.pravin.desai.swipe.network.result

sealed class NetworkResult<out T>{

    class Success<out R>(val data: R?) : NetworkResult<R>()

    class Error(val message: String) : NetworkResult<Nothing>()

    object Loading : NetworkResult<Nothing>()

}
