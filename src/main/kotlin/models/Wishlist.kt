package models

import utils.Utilities
import java.time.LocalDate

data class Wishlist(
    var wishlistName: String,
    var wishlistDate: LocalDate,
    var wishlistUserName: String,
    var wishlistCategory: String,
    var wishlistPriority: Int,
    var isWishlistArchived: Boolean,
    var products: MutableSet<Product> = mutableSetOf()
) {

    // functions to manage the product set will go in here

    private var lastProductId = 0
    private fun getProductId() = lastProductId++

    fun addProduct(product: Product): Boolean {
        product.productId = getProductId()
        return products.add(product)
    }
    fun numberOfProducts() = products.size

    fun findOne(id: Int): Product? {
        return products.find { product -> product.productId == id }
    }

    fun delete(id: Int): Boolean {
        return products.removeIf { product -> product.productId == id }
    }

    fun update(id: Int, newProduct: Product): Boolean {
        val foundProduct = findOne(id)

        // if the object exists, use the details passed in the newItem parameter to
        // update the found object in the Set
        if (foundProduct != null) {
            foundProduct.productName = newProduct.productName
            foundProduct.productDescription = newProduct.productDescription
            foundProduct.productBrand = newProduct.productBrand
            foundProduct.productPrice = newProduct.productPrice
            foundProduct.productType = newProduct.productType
            foundProduct.productQuantity = newProduct.productQuantity
            foundProduct.isProductFavourite = newProduct.isProductFavourite

            return true
        }

        // if the object was not found, return false, indicating that the update was not successful
        return false
    }
    fun listProducts() =
        if (products.isEmpty()) "\tNO PRODUCTS ADDED"
        else Utilities.formatListString(products)
    override fun toString(): String {
        val archived = if (isWishlistArchived) 'Y' else 'N'
        return " $wishlistName,$wishlistDate,$wishlistUserName ,Priority($wishlistPriority) Category($wishlistCategory), Archived($archived) \n${listProducts()}"
    }

    fun checkProductFavouriteStatus(): Boolean {
        if (products.isNotEmpty()) {
            for (product in products) {
                if (!product.isProductFavourite) {
                    return false
                }
            }
        }
        return true // a note with empty items can be archived, or all items are complete
    }
}
