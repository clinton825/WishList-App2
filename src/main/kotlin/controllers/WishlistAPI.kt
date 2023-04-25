package controllers

import models.Wishlist
import persistence.Serializer
import javax.print.attribute.standard.JobPriority
import kotlin.jvm.Throws


class WishlistAPI(serializerType: Serializer){
    private var serializer: Serializer = serializerType
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

    fun numberOfArchivedWishlists(): Int = wishlists.count {
        wishlist: Wishlist -> wishlist.isWishlistArchived }

     fun numberOfActiveWishlits(): Int {
         return wishlists.stream()
             .filter { wishlist: Wishlist -> !wishlist.isWishlistArchived }
             .count()
             .toInt()
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

    fun numberOfWishlistsByPriority(priority: Int): Int = wishlists.count { p: Wishlist -> p.wishlistPriority == priority }



    fun deleteWishlist(indexToDelete: Int):Wishlist? {
        return if (isValidListIndex(indexToDelete, wishlists)){
            wishlists.removeAt(indexToDelete)
        }else null
    }

fun updateWishlist(indexToUpdate: Int, wishlist: Wishlist?): Boolean{
    val foundWishlist = findWishlist(indexToUpdate)
    if ((foundWishlist != null) && (wishlist != null)){
        foundWishlist.wishlistName = wishlist.wishlistName
        foundWishlist.wishlistUserName = wishlist.wishlistUserName
        foundWishlist.wishlistDate = wishlist.wishlistDate
        foundWishlist.wishlistPriority = wishlist.wishlistPriority
        foundWishlist.wishlistCategory = wishlist.wishlistCategory
        return true
    }
    return false
}

    fun archiveWishlist(indexToArchive: Int): Boolean{
        if (isValidIndex(indexToArchive)){
            val wishlistToArchive = wishlists[indexToArchive]
            if (!wishlistToArchive.isWishlistArchived) {
                wishlistToArchive.isWishlistArchived = true
                return true
            }
        }
        return false
    }

    @Throws(Exception::class)
    fun load(){
        wishlists = serializer.read() as ArrayList<Wishlist>
    }

    @Throws(Exception::class)
    fun store() {
        serializer.write(wishlists)
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
    fun isValidIndex(index: Int) :Boolean{
        return isValidListIndex(index, wishlists)
    }


}
