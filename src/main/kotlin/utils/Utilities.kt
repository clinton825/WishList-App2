package utils
import models.Product
import models.Wishlist


object Utilities  {
    @JvmStatic
    fun formatListString(wishlistToFormat: List<Wishlist>): String {
        return wishlistToFormat
            .joinToString (separator = "\n" ) { wishlist -> "$wishlist"  }
    }


    @JvmStatic
    fun formatListString(productToFormat: Set<Product>): String {
        return productToFormat
            .joinToString (separator = "\n" ) { product -> "$product"  }
    }

}