package com.xekera.Ecommerce.data.rest.response.searchAllProductReponse

data class Product(
    val Price: String?,
    val Product_Sku: String?,
    val Regular_price: String?,
    val id: String?,
    val image_json: List<String?>?,
    val name: String?,
    val type: String?
)