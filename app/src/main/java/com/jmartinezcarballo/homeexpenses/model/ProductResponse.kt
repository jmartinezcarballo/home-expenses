package com.jmartinezcarballo.homeexpenses.model

data class ProductResponse(
    val code: String,
    val status: Int,
    val productData: ProductData) {


}