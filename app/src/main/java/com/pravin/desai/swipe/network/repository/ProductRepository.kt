package com.pravin.desai.swipe.network.repository

import com.pravin.desai.swipe.network.model.Product
import com.pravin.desai.swipe.network.retrofit.ProductService
import com.pravin.desai.swipe.network.retrofit.callApiForResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ProductRepository @Inject constructor(private val productService: ProductService) {

    fun getAllProducts() = callApiForResult{
            productService.getAllProducts()
        }.flowOn(Dispatchers.IO)


    fun addProduct(product: Product) = callApiForResult{
        productService.addProducts(product)
    }.flowOn(Dispatchers.IO)

}