package com.pravin.desai.swipe.network.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Product(

	@SerializedName("image")
	val image: String? = null,

	@SerializedName("product_type")
	val productType: String? = null,

	@SerializedName("price")
	val price: Double? = null,

	@SerializedName("tax")
	val tax: Float? = null,

	@SerializedName("product_name")
	val productName: String? = null
) :Serializable