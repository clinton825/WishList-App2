import controllers.WishlistAPI
import models.Wishlist
import mu.KotlinLogging
import persistence.JSONSerializer
import persistence.Serializer
import persistence.XMLSerializer
import persistence.YAMlSerializer
import utils.ScannerInput.readNextInt
import utils.ScannerInput.readNextLine
import java.io.File
import java.lang.System.exit
import java.time.LocalDate
import java.util.Date


private val logger = KotlinLogging.logger {}
//private val wishlistAPI = WishlistAPI(XMLSerializer(File("wishlists.xml")))
//private val wishlistAPI = WishlistAPI(JSONSerializer(File("wishlists.json")))
private  val wishlistAPI = WishlistAPI(YAMlSerializer(File("wishlists.yaml")))


fun main(args: Array<String>) {
    runMenu()
}

fun runMenu() {
    do {
        val option = mainMenu()
        when (option) {
            1 -> addWishList()
            2 -> listWishlists()
            3 -> updateWishList()
            4 -> deleteWishlist()
            5 -> archiveWishlist()
            20 -> saveWishlist()
            21 -> loadWishlist()
            0 -> exitApp()
            else -> println("Invalid option entered: ${option}")

        }
    } while (true)
}


fun loadWishlist() {
    try {
        wishlistAPI.load()
    }catch (e: Exception){
        System.err.println("Error reading  from file: $e")
    }
}

fun saveWishlist() {
    try {
        wishlistAPI.store()
    }catch (e: Exception){
        System.err.println("Error reading  from file: $e")
    }
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
         > |   5) Archive a  WishList       |
         > |   20)  Save Wishlists          |
         > |   21) Load Wishlists           |
         > ----------------------------------
         > |   0) Exit                      |
         > ----------------------------------
         > ==>> """.trimMargin(">"))

}


fun addWishlistInput(): Wishlist? {
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

    return Wishlist(wishlistName, wishlistDate, wishlistUserName, wishlistCategory, wishlistPriority, false)
}

fun addWishList() {
    // logger.info { "addWishList() function invoked" }
    val wishlist = addWishlistInput()

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


fun listWishlists() {
    if (wishlistAPI.numberOfWishlists() > 0) {
        val option = readNextInt(
            """
                  > --------------------------------
                  > |   1) View ALL wishlists        |
                  > |   2) View ACTIVE wishslists    |
                  > |   3) View ARCHIVED wishslists  |
                  > --------------------------------
         > ==>> """.trimMargin(">"))

        when (option) {
            1 -> listAllWishlists();
            2 -> listActiveWishlists();
            3 -> listArchivedWishlists();
            else -> println("Invalid option entered: " + option);
        }
    } else {
        println("Option Invalid - No wishlists stored");
    }
}

fun archiveWishlist() {
listActiveWishlists()
    if (wishlistAPI.numberOfActiveWishlits() >0){
        val indexToArchive = readNextInt("Enter the index of the wishlist to archive: ")
        if (wishlistAPI.archiveWishlist(indexToArchive)){
            println("Archive Successful!")
        }else{
            println("Archive NOT Successful")
        }
    }
}

fun listActiveWishlists() {
    println(wishlistAPI.listActiveWishlists())

}
fun listAllWishlists() {
    println(wishlistAPI.listAllWishlists())
}

fun listArchivedWishlists() {
    println(wishlistAPI.listArchivedWishlists())
}

fun updateWishList() {
//    logger.info { "upadteWlishList() function invoked" }
    listWishlists()
    if (wishlistAPI.numberOfWishlists() > 0) {
        val indxeToUpdate = readNextInt("Enter the index of the wishlist to update: ")
        if (wishlistAPI.isValidIndex(indxeToUpdate)) {
            val wishlistName = readNextLine("Enter a name for the wishlist: ")
            val wishlistUserName = readNextLine("Enter the person the wishlist belongs to: ")
            val wishlistPriority = readNextInt("Enter a priority(1-low, 2,3,4, 5-high): ")
            val wishlistCategory = readNextLine("Enter a category for the wishlist: ")
            val wishlistDate = LocalDate.now()

            if (wishlistAPI.updateWishlist(indxeToUpdate, Wishlist(wishlistName, wishlistDate,wishlistUserName,wishlistCategory,wishlistPriority,false))){
                println("Update Successful")
            }else{
                println("Update Failed")
            }
        }else{
            println("There is no wishlists for this index number")
        }

    }}


    fun deleteWishlist() {
        //logger.info { "deleteNotes() function invoked" }
        listWishlists()
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


