package models

data class Product(var productId: Int = 0,
                   var productName: String = "",
                   var productPrice: Double = 0.0,
                   var productDescription: String = "",
                   var productType: String = "",
                   var productBrand: String = "",
                   var productQuantity: Int = 0,
                   var isProductFavourite: Boolean = false,

)
