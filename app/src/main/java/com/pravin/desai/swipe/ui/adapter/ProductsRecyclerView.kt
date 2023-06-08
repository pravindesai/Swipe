package com.pravin.desai.swipe.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.pravin.desai.swipe.R
import com.pravin.desai.swipe.databinding.ProductLayoutBinding
import com.pravin.desai.swipe.network.model.Product

class ProductsRecyclerView() : RecyclerView.Adapter<ProductsRecyclerView.ProductsViewHolder>(),Filterable {
    private var productsListFull: MutableList<Product>? = null
    private var productsListFiltered: MutableList<Product>? = null
    private var iOnProductClickListener: IOnProductClickListener? = null

    inner class ProductsViewHolder(private val item: ProductLayoutBinding) :
        RecyclerView.ViewHolder(item.root) {

        fun bind(product: Product, position: Int) {
            item.product = product
            item.container.setOnClickListener { iOnProductClickListener?.iOnProductClicked(product, position = position ) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = ProductLayoutBinding.inflate(layoutInflater, parent, false)
        return ProductsViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductsViewHolder, position: Int) {
        val product = productsListFiltered?.get(position)
        product?.let {
            holder.bind(product = it, position = position)
        }
    }

    override fun getItemCount(): Int = productsListFiltered?.size?:0

    fun updateItems(productsList: MutableList<Product>?,
                    iOnProductClickListener: IOnProductClickListener){
        this.productsListFull = productsList
        this.productsListFiltered = productsList
        this.iOnProductClickListener = iOnProductClickListener
        notifyDataSetChanged()
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence): FilterResults {
                val charString = constraint.toString()
                if (charString.isEmpty()) productsListFiltered = productsListFull else {
                    val filteredList = productsListFull?.filter {
                            it.productName?.contains(constraint, ignoreCase = true)?:false

                        }
                    productsListFiltered = filteredList?.toMutableList()

                }
                return FilterResults().apply { values = productsListFiltered }
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {

                productsListFiltered = if (results?.values == null)
                    mutableListOf()
                else
                    results.values as MutableList<Product>
                notifyDataSetChanged()
            }
        }
    }


}