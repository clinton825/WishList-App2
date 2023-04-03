package models

import java.time.LocalDate


data class Wishlist(
    val wishlistName: String,
    val wishlistDate: LocalDate,
    val wishlistUserName: String,
    val wishlistCategory: String,
    val wishlistPriority: Int,
    val b: Any?
)
