package controllers

import models.Wishlist
import javax.print.attribute.standard.JobPriority

class wishlistAPI {
    private var wishlists = ArrayList<Wishlist>()

    fun add(wishlist: Wishlist): Boolean{
        return wishlists.add(wishlist)
    }
    fun listAllWishlists(): String{
        return if (wishlists.isEmpty()){
            "no wishlists stored"
        }else{
            var listOfWishlists = ""
            for (i in wishlists.indices){
                listOfWishlists+= "${i}: ${wishlists[i]} \n"
            }
            listOfWishlists
        }
    }

fun listActiveWishlists(): String{
    return if (numberOfActiveWishlits() == 0){
        "No active wishlists stored"
    }else {
        var listOfActiveWishlists = ""
        for (wishlist in wishlists){
            if (!wishlist.isWishlistArchived){
                listOfActiveWishlists += "${wishlists.indexOf(wishlist)}: $wishlist \n"
            }
        }
        listOfActiveWishlists
    }
}
    fun listArchivedWishlists(): String{
        return if (numberOfWishlists() == 0) {
            "NO archived wishlist stored"
        }else{
            var listOfArchivedWishlists = ""
            for (wishlist in wishlists){
                if (wishlist.isWishlistArchived){
                    listOfArchivedWishlists += "${wishlists.indexOf(wishlist)}: $wishlist \n"
                }
            }
            listOfArchivedWishlists
        }
    }

fun numberOfWishlists(): Int{
    return wishlists.size
}

    fun numberOfArchivedWishlists(): Int{
        var counter = 0
        for (wishlist in wishlists){
            if (wishlist.isWishlistArchived){
                counter++
            }
        }
        return  counter
    }

     fun numberOfActiveWishlits(): Int{
         var counter = 0
         for (wishlist in wishlists){
             if (!wishlist.isWishlistArchived) {
                 counter++
             }
         }
        return counter

     }

    fun listWishlistBySelectedPrioriry(priority: Int): String{
        return if(wishlists.isEmpty()) {
            "No wishlists stored"
        }else {
            var listOfWishlists = ""
            for (i in wishlists.indices){
                if (wishlists[i].wishlistPriority == priority) {
                    listOfWishlists +=
                        """"$i: ${wishlists[i]}
                            """.trimIndent()

                }
            }
            if (listOfWishlists.equals("")){
                "No wishlists with priority:$priority"
            }else {
                "{numberOfWishlistsByPriority(priority)} wishlists with priority $priority: $listOfWishlists"
            }
        }
    }

    fun numberOfWishlistsByPriority(priority: Int) : Int {
        var counter = 0
        for (wishlist in wishlists){
            if (wishlist.wishlistPriority == priority){
                counter++
            }
        }
        return counter
    }


    fun deleteWishlist(indexToDelete: Int):Wishlist? {
        return if (isValidListIndex(indexToDelete, wishlists)){
            wishlists.removeAt(indexToDelete)
        }else null
    }

fun findWishlist(index:Int):Wishlist? {
    return if (isValidListIndex(index,wishlists)){
        wishlists[index]
    }else null
}
    // utility method to determine if an index is valid in a list
    fun isValidListIndex(index: Int,list: List<Any>): Boolean {
        return (index>= 0 && index < list.size)
    }


}
