import controllers.wishlistAPI
import models.Wishlist
import mu.KotlinLogging
import utils.ScannerInput
import utils.ScannerInput.readNextInt
import utils.ScannerInput.readNextLine
import java.lang.System.exit
import java.time.LocalDate


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
            4 -> deleteWishlist()
            0 -> exitApp()
            else -> println("Invalid option entered: ${option}")

        }
    } while (true)
}


fun mainMenu() : Int {
    return readNextInt(""" 
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


fun AddWishlist(): Wishlist? {
    val wishlistName = readNextLine("Enter a name for the wishlist: ")
    val wishlistUserName = readNextLine("Enter the name of the person whom the wishlist belongs to: ")
    val wishlistPriority = readNextInt("Enter a priority (1-low, 2, 3, 4, 5-high): ")

    // Validate user input
    if (wishlistPriority !in 1..10) {
        println("Invalid wishlist priority. Please enter a number between 1 and 5.")
        return null
    }

    val wishlistCategory = readNextLine("Enter a category for the wishlist: ")
    val wishlistDate = LocalDate.now()

    return Wishlist(wishlistName, wishlistDate, wishlistUserName, wishlistCategory, wishlistPriority, false, false)
}

fun addWishList() {
    // logger.info { "addWishList() function invoked" }
    val wishlist = AddWishlist()

    if (wishlist != null) {
        try {
            val isAdded = wishlistAPI.add(wishlist)
            if (isAdded) {
                println("Wishlist added successfully.")
            } else {
                println("Failed to add wishlist.")
            }
        } catch (e: Exception) {
            println("An error occurred while adding wishlist: ${e.message}")
        }
    }
}


fun listWishList() {
    //logger.info { "listWishLists() function invoked" }
println(wishlistAPI.listAllWishlists())
}

fun updateWishList() {
    logger.info { "upadteWlishList() function invoked" }

}


//   logger.info { "deleteWishList() function invoked" }
fun deleteWishlist(){
    //logger.info { "deleteNotes() function invoked" }
    listWishList()
    if (wishlistAPI.numberOfWishlists() > 0) {
        //only ask the user to choose the note to delete if notes exist
        val indexToDelete = readNextInt("Enter the index of the wishlist to delete: ")
        //pass the index of the note to NoteAPI for deleting and check for success.
        val wishlistToDelete = wishlistAPI.deleteWishlist(indexToDelete)
        if (wishlistToDelete != null) {
            println("Delete Successful! Deleted wishlist: ${wishlistToDelete.wishlistName}")
        } else {
            println("Delete NOT Successful")
        }
    }
}



fun exitApp() {
    println("Exiting...bye")
    exit(0)
}