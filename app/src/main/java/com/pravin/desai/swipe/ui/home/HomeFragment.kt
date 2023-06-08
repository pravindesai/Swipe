package com.pravin.desai.swipe.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.pravin.desai.swipe.R
import com.pravin.desai.swipe.databinding.FragmentHomeBinding
import com.pravin.desai.swipe.network.model.Product
import com.pravin.desai.swipe.network.result.NetworkResult
import com.pravin.desai.swipe.ui.adapter.IOnProductClickListener
import com.pravin.desai.swipe.ui.home.viewModel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class HomeFragment : Fragment(), IOnProductClickListener {

    private var _binding: FragmentHomeBinding? = null
    private var _viewmodel: HomeViewModel? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        _viewmodel = ViewModelProvider(this)[HomeViewModel::class.java]
        _binding?.isLoading = false
        _binding?.iOnProductClickListener = this


        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getAllProducts()
        /*addProduct(
            Product(
                image = "asdasdas",
                productType = "sldfjls",
                price = 1.2,
                tax = 1.3f,
                productName = "sadshdk"
            )
        )*/

    }

    private fun getAllProducts(){
       lifecycleScope.launchWhenStarted {
           _viewmodel?.allPostsFlow?.collectLatest {
               when(it){
                   is NetworkResult.Loading->{
                       _binding?.isLoading = true
                       _binding?.productsList = emptyList()
                        Log.e("***", "Loading")
                   }

                   is NetworkResult.Success->{
                       delay(2000) // for shimmer effect
                       _binding?.isLoading = false
                       _binding?.productsList = it.data
                       Log.e("***", "Success -> ${it.data}")
                   }

                   is NetworkResult.Error->{
                       delay(2000) // for shimmer effect
                       _binding?.isLoading = false
                       _binding?.productsList = emptyList()
                       Log.e("***", "Error -> ${it.message}")
                   }

               }
           }
       }
    }

    private fun addProduct(product: Product){
       lifecycleScope.launchWhenStarted {
           _viewmodel?.addProduct(product)?.collectLatest {
               when(it){
                   is NetworkResult.Loading->{
                        Log.e("***", "Loading")
                   }

                   is NetworkResult.Success->{
                       Log.e("***", "Success -> ${it.data}")
                   }

                   is NetworkResult.Error->{
                       Log.e("***", "Error -> ${it.message}")
                   }

               }
           }
       }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun iOnProductClicked(product: Product, position: Int) {
        findNavController().navigate(
            R.id.action_FirstFragment_to_SecondFragment,
            Bundle().apply {
                this.putSerializable("product",  product)
            }
        )
    }
}