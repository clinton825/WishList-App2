package models

import java.time.LocalDate


data class Wishlist(
    var wishlistName: String,
    var wishlistDate: LocalDate,
    var wishlistUserName: String,
    var wishlistCategory: String,
    var wishlistPriority: Int,
    var isWishlistArchived: Boolean
)
