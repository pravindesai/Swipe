package com.pravin.desai.swipe.network.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ProductAddedResponse(

	@SerializedName("success")
	val success: Boolean? = null,

	@SerializedName("product_id")
	val productId: Int? = null,

	@SerializedName("message")
	val message: String? = null,

	@SerializedName("product_details")
	val productDetails: Product? = null
):Serializable