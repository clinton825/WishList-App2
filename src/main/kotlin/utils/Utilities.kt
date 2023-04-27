package utils
import models.Wishlist


object Utilities  {
    @JvmStatic
    fun formatListString(wishlistToFormat: List<Wishlist>): String {
        return wishlistToFormat
            .joinToString (separator = "\n" ) { wishlist -> "$wishlist"  }
    }

}