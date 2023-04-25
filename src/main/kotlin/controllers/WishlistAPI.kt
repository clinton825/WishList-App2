package controllers

import models.Wishlist
import persistence.Serializer
import javax.print.attribute.standard.JobPriority
import kotlin.jvm.Throws


class WishlistAPI(serializerType: Serializer){
    private var serializer: Serializer = serializerType
    private var wishlists = ArrayList<Wishlist>()

    private fun formatListString(wishlistToFormat: List<Wishlist>): String =
        wishlistToFormat
            .joinToString(separator = "\n") { wishlist ->
                wishlists.indexOf(wishlist).toString() + ": " + wishlist.toString()
            }
    fun add(wishlist: Wishlist): Boolean{
        return wishlists.add(wishlist)
    }
    fun listAllWishlists(): String =
        if  (wishlists.isEmpty()) "No wishlists stored"
        else wishlists.joinToString (separator = "\n") { wishlist ->
            wishlists.indexOf(wishlist).toString() + ": " + wishlist.toString() }


    fun listActiveWishlists(): String =
        if (numberOfActiveWishlits() == 0) "No active wishlists stored"
        else formatListString(wishlists.filter { wishlist -> !wishlist.isWishlistArchived })


    fun listArchivedWishlists(): String =
        if  (numberOfArchivedWishlists() == 0) "No archived wishlists stored"
        else formatListString(wishlists.filter { wishlist -> wishlist.isWishlistArchived})


    fun listWishlistsBySelectedPriority(priority: Int): String =
        if (wishlists.isEmpty()) "No wishlists stored"
        else {
            val listOfWishlists = formatListString(wishlists.filter { wishlist -> wishlist.wishlistPriority == priority })
            if (listOfWishlists.equals("")) "No wishlists with priority: $priority"
            else "${numberOfWishlistsByPriority(priority)} wishlists with priority $priority: $listOfWishlists"
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
