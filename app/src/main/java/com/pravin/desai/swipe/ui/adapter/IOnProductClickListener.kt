package com.pravin.desai.swipe.ui.adapter

import com.pravin.desai.swipe.network.model.Product

interface IOnProductClickListener {

    fun iOnProductClicked(product: Product, position:Int)

}