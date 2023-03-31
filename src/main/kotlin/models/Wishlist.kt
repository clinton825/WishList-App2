package models

import java.util.Date

data class Wishlist(
    val wishlistName: String,
    val wishlistDate: Date,
    val wishlistUserName: String,
    val wishlistCategory: String,
    val wishlistPriority: String
) {
}