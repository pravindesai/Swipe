package com.pravin.desai.swipe.ui.productdetails

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.pravin.desai.swipe.R
import com.pravin.desai.swipe.databinding.FragmentProductDetailsBinding
import com.pravin.desai.swipe.network.model.Product
import com.pravin.desai.swipe.network.result.NetworkResult
import com.pravin.desai.swipe.ui.adapter.IOnProductClickListener
import com.pravin.desai.swipe.ui.home.viewModel.HomeViewModel
import com.pravin.desai.swipe.ui.productdetails.viewModel.ProductViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class ProductDetailsFragment : Fragment(), IOnProductClickListener, IonAddProductClickListener {

    private var _binding: FragmentProductDetailsBinding? = null
    private var _viewmodel:ProductViewModel? = null
    private val _args by navArgs<ProductDetailsFragmentArgs>()
    private var _product:Product? = null
    private var _totalInCart = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentProductDetailsBinding.inflate(inflater, container, false)
        _viewmodel = ViewModelProvider(this)[ProductViewModel::class.java]
        _product = _args.product
        _binding?.isLoading = false
        _binding?.iOnProductClickListener = this
        _binding?.ionAddProductClickListener = this
        _binding?.product =_product
        _binding?.totalInCart = _totalInCart

        Log.e("***", _product.toString())

        return _binding?.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getAllProducts()
        _binding?.isLoading = true
    }

    private fun getAllProducts(){
        lifecycleScope.launchWhenStarted {
            _viewmodel?.allPostsFlow?.collectLatest {
                when(it){
                    is NetworkResult.Loading->{
                        _binding?.isLoading = true
                        _binding?.productsList = emptyList()
                    }

                    is NetworkResult.Success->{
                        delay(2000) // for shimmer effect
                        _binding?.isLoading = false
                        _binding?.productsList = it.data
                    }

                    is NetworkResult.Error->{
                        delay(2000) // for shimmer effect
                        _binding?.isLoading = false
                        _binding?.productsList = emptyList()
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
                        // No impl
                    }

                    is NetworkResult.Success->{
                        _binding?.root?.let { it1 ->
                            Snackbar.make(it1, "ADDED", Snackbar.LENGTH_LONG).show()
                        }
                    }

                    is NetworkResult.Error->{
                        _binding?.root?.let { it1 ->
                            Snackbar.make(it1, it.message, Snackbar.LENGTH_LONG).show()
                        }
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
            R.id.action_SecondFragment_self,
            Bundle().apply {
                this.putSerializable("product",  product)
            }
        )
    }

    override fun onAddProduct() {
        _totalInCart = _totalInCart.plus(1)
        _binding?.totalInCart = _totalInCart

        _product?.let { addProduct(it) }
    }

    override fun onRemoveProduct() {
        if (_totalInCart>0){
            _totalInCart = _totalInCart.minus(1)
            _binding?.totalInCart = _totalInCart
        }
    }
}