package com.example.e_commerce_iti.model.pojos

/**
 *      below class is the actuall one used to catch products
 */
data class Producut(
    val products: List<Product>
)

data class ProductWrapper(
    val product: Product
)
data class SearchedProductResponse(
    val product: Product
)
data class AllProduct(var products: List<Product>)
data class Product(
    val id: Long,
    val title: String,
    val body_html: String,
    val vendor: String,
    val product_type: String,
    val tags: String,
    val status: String,
    val variants: List<Variant>,
    val options: List<Option>,
    val images: List<Image>
)
data class Variant(
    val id: Long,
    val title: String,
    val price: String,
    val inventory_quantity: Int,
    val inventoryQuantity: Int=0,
    val sku: String
)

data class Option(
    val id: Long,
    val name: String,
    val values: List<String>
)

data class Image(
    val id: Long,
    val src: String,
    val width: Int,
    val height: Int
)
