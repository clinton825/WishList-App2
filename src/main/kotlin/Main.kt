import controllers.wishlistAPI
import models.Wishlist
import mu.KotlinLogging
import utils.ScannerInput
import utils.ScannerInput.readNextDouble
import utils.ScannerInput.readNextInt
import utils.ScannerInput.readNextLine
import java.lang.System.exit


private val logger = KotlinLogging.logger {}
private val wishlistAPI = wishlistAPI()

fun main(args: Array<String>) {
    runMenu()
}

fun runMenu() {
    do {
        val option = mainMenu()
        when (option) {
            1 -> addWishList()
            2 -> listWishList()
            3 -> updateWishList()
            4 -> deleteWishList()
            0 -> exitApp()
            else -> println("Invalid option entered: ${option}")

        }
    } while (true)
}


fun mainMenu() : Int {
    return ScannerInput.readNextInt(""" 
         > ----------------------------------
         > |        SHOPPING WISHLIST APP   |
         > ----------------------------------
         > |  WISHLIST MENU                 |
         > |   1) Add a WishList            |
         > |   2) List all WishLists        |
         > |   3) Update the WishList       |
         > |   4) Delete a WishList         |
         > ----------------------------------
         > |   0) Exit                      |
         > ----------------------------------
         > ==>> """.trimMargin(">"))

}


fun addWishList() {
    //logger.info { "addWishList() function invoked" }
val wishlistName = readNextLine("Enter a Name for the WishList: ")
    val wishlistDate = readNextDouble("Enter the date the wishlist was created: ")
    val wishlistUserName = readNextLine("Enter the Name of the person whom the wishlist belongs to): ")
    val wishlistPriority = readNextInt("Enter a priority (1-low,2,3,4,5-high): ")
    val wishlistCategory = readNextLine("Enter a category for the wishlist: ")
    val isAdded = wishlistAPI.add(Wishlist(wishlistName,wishlistDate,wishlistUserName,wishlistCategory, wishlistPriority,false))

    if (isAdded){
        println("Added Successfully")
    }else {
        println("add Failed")
    }
}

fun listWishList() {
    //logger.info { "listWishLists() function invoked" }
println(wishlistAPI.listAllWishlists())
}

fun updateWishList() {
    logger.info { "upadteWlishList() function invoked" }

}

fun deleteWishList() {
    logger.info { "deleteWishList() function invoked" }

}

fun exitApp() {
    println("Exiting...bye")
    exit(0)
}