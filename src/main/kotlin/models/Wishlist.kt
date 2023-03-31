package models

data class Wishlist(
    val wishlistName: String,
    val wishlistDate: Double,
    val wishlistUserName: String,
    val wishlistCategory: String,
    val wishlistPriority: Int,
    val b: Boolean
) {
}