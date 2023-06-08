package com.pravin.desai.swipe.network.retrofit

import com.pravin.desai.swipe.BuildConfig
import com.pravin.desai.swipe.network.model.Product
import com.pravin.desai.swipe.network.model.ProductAddedResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ProductService {

    @GET(BuildConfig.URL_GET_ALL_PRODUCTS)
    suspend fun getAllProducts():Response<List<Product>>

    @POST(BuildConfig.URL_ADD_PRODUCTS)
    suspend fun addProducts(@Body product: Product):Response<ProductAddedResponse>
}