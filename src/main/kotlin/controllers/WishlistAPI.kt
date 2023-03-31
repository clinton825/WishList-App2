package controllers

import models.Wishlist

class wishlistAPI {
    private var wishlists = ArrayList<Wishlist>()

    fun add(wishlist: Wishlist): Boolean{
        return wishlists.add(wishlist)
    }
    fun listAllWishlists():String {
        return if (wishlists.isEmpty()) {
            "No wishlits stored"
        }else{
            var listOfWishlists = ""
            for (i in wishlists.indices){
                listOfWishlists += "${i}: ${wishlists[i]} \n"
            }
            listOfWishlists
        }
    }
}
