package com.pravin.desai.swipe.ui.productdetails.viewModel

import androidx.lifecycle.ViewModel
import com.pravin.desai.swipe.network.model.Product
import com.pravin.desai.swipe.network.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val productRepository: ProductRepository
):ViewModel()  {
    val allPostsFlow = productRepository.getAllProducts()

    fun addProduct(product: Product) = productRepository.addProduct(product)

}