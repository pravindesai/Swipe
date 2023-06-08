package com.pravin.desai.swipe.ui.adapter

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pravin.desai.swipe.R
import com.pravin.desai.swipe.network.model.Product

object ProductsRvBindingAdapter {
    @JvmStatic
    @BindingAdapter(
        "productsList",
        "iOnProductClickListener"
    )
    fun bindCartItemsList(
        recyclerView: RecyclerView,
        productsList: MutableList<Product>?,
        iOnProductClickListener: IOnProductClickListener
    ) {

        val adapter = getOrCreateCartItemsRVAdapterRecipeAdapter(recyclerView)
        adapter.updateItems(productsList, iOnProductClickListener)
    }

    private fun getOrCreateCartItemsRVAdapterRecipeAdapter(recyclerView: RecyclerView): ProductsRecyclerView {

        return if (recyclerView.adapter != null && recyclerView.adapter is ProductsRecyclerView) {
            recyclerView.adapter as ProductsRecyclerView
        } else {
            val shoppingCartAdapter = ProductsRecyclerView()
            recyclerView.adapter = shoppingCartAdapter
            shoppingCartAdapter
        }
    }


    @JvmStatic
    @BindingAdapter("imgUrl")
    fun setImage(imgView: ImageView, imgUrl: String?) {
        val optional = "https://app.getswipe.in/swipe.png"
        val url = imgUrl?.ifBlank{optional}
        imgUrl?.let {
            Glide.with(imgView)
                .load(url)
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(imgView)
        }
    }


    @JvmStatic
    @BindingAdapter("productFilter")
    fun EditText.setFilter(recyclerView: RecyclerView) {
        val adapter = getOrCreateCartItemsRVAdapterRecipeAdapter(recyclerView)
        this.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                //No impl
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                adapter.filter.filter(s)
            }

            override fun afterTextChanged(s: Editable?) {
                // no impl
            }

        })


    }

}