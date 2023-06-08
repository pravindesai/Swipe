package com.pravin.desai.swipe.ui.home.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pravin.desai.swipe.network.model.Product
import com.pravin.desai.swipe.network.repository.ProductRepository
import com.pravin.desai.swipe.network.result.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val productRepository: ProductRepository
) :ViewModel() {


    val allPostsFlow = productRepository.getAllProducts()

    fun addProduct(product: Product) = productRepository.addProduct(product)

}