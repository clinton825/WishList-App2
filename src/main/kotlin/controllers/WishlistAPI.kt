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
fun numberOfWishlists(): Int{
    return wishlists.size
}
fun findWishlist(index:Int):Wishlist? {
    return if (isValidListIndex(index,wishlists)){
        wishlists[index]
    }else null
}
    // utility method to determine if an index is vaild in a list
    fun isValidListIndex(index: Int,list: List<Any>): Boolean {
        return (index>= 0 && index < list.size)
    }
}
