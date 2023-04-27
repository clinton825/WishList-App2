import controllers.WishlistAPI
import models.Product
import models.Wishlist
import mu.KotlinLogging
import persistence.JSONSerializer
import persistence.Serializer
import persistence.XMLSerializer
import persistence.YAMlSerializer
import utils.ScannerInput.readNextDouble
import utils.ScannerInput.readNextInt
import utils.ScannerInput.readNextLine
import java.io.File
import java.lang.System.exit
import java.time.LocalDate
import java.util.Date

private val logger = KotlinLogging.logger {}

//private val wishlistAPI = WishlistAPI(XMLSerializer(File("wishlists.xml")))
private val wishlistAPI = WishlistAPI(JSONSerializer(File("wishlists.json")))
//private  val wishlistAPI = WishlistAPI(YAMlSerializer(File("wishlists.yaml")))


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
            6 -> addProductToWishlist()
            //7 -> updateProductInWishlists()
            //8 -> deleteAProuduct()
            //9 -> labelProductStatus()
            10 -> searchWishlist()
            11 -> sortNotes()
            8 -> categoryWishlists()
            20 -> saveWishlist()
            21 -> loadWishlist()
            0 -> exitApp()
            else -> println("Invalid option entered: ${option}")

        }
    } while (true)
}

fun askUserToChooseActiveWishlist(): Wishlist? {
    //show the list of Wish
    //ask them to chose one
    listWishlists()
    if (wishlistAPI.numberOfWishlists() > 0) {
        val indxeToSee = readNextInt("Enter the index of the wishlist to see: ")
        if (wishlistAPI.isValidIndex(indxeToSee)) {
            return wishlistAPI.findWishlist(indxeToSee)

        }
    }
    return null
}

fun addProductToWishlist() {
    val wishlist: Wishlist? = askUserToChooseActiveWishlist()
    if (wishlist != null) {
        if (wishlist.addProduct(
                Product(
                    productDescription = readNextLine("\t Product Description: "),
                    productBrand = readNextLine("\t Product Brand: "),
                    productPrice = readNextDouble("\t Product Price: "),
                    productName = readNextLine("\t Product Name: "),
                    productType = readNextLine("\t Product Type: "),
                    productId = readNextInt("\t Product ID: "),
                    productQuantity = readNextInt("\t Product Quantity: ")
                )
            )
        )
            println("Add Successful!")
        else println("Add NOT Successful")
    }

}


fun categoryWishlists() {
    println(wishlistAPI.countWishlistsOfaSpecificCategory(readNextLine("Enter category to see how many there is: ")))
}

fun sortNotes() {
    TODO("Not yet implemented")
}

fun searchWishlist() {
    val searchName = readNextLine("Enter the description to search by: ")
    val searchResults = wishlistAPI.searchByName(searchName)
    if (searchResults.isEmpty()) {
        println("No wishlists found")
    } else {
        println(searchResults)
    }
}


fun loadWishlist() {
    try {
        wishlistAPI.load()
    } catch (e: Exception) {
        System.err.println("Error reading  from file: $e")
    }
}

fun saveWishlist() {
    try {
        wishlistAPI.store()
    } catch (e: Exception) {
        System.err.println("Error reading  from file: $e")
    }
}


fun mainMenu(): Int {
    return readNextInt(
        """ 
         > -------------------------------------------------------
         > |                 SHOPPING WISHLIST APP                |
         > -------------------------------------------------------
         > | WISHLIST MENU                                        |
         > |   1) Add a wishlists                                 |
         > |   2) List wishlists                                  |
         > |   3) Update a wishlist                               |
         > |   4) Delete a wishlist                               |
         > |   5) Archive a wishlist                              |
         > -------------------------------------------------------
         > | PRODUCT MENU                                         | 
         > |   6) Add product to wishlist                         |
         > |   7) Update product material on a wishlist           |
         > |   8) Delete product from a wishlist                  |
         > |   9) Label product as favourite                      | 
         > --------------------------------------------------------  
         > | REPORT MENU FOR WISHLISTS                            | 
         > |   10) Search for all wishlists (by wishlist name)    |
         > |   11) Number of wishlists by category                |
         > |   12) .....                                          |
         > |   13) .....                                          |
         > |   14) .....                                          |
         > ----------------------------------------------------- -- 
         > | REPORT MENU FOR PRODUCT                              |                                
         > |   15) Search for all product (by product description)|
         > |   16) List Favourite Products                        |
         > |   17) .....                                          |
         > |   18) .....                                          |
         > |   19) .....                                          |
         > --------------------------------------------------------
         > |   SAVE AND LOAD OPTIONS                              |
         > |   20) Save Wishlists                                 |
         > |   21) Load Wishlists                                 |
         > --------------------------------------------------------  
         > |   0) Exit                                            |
         > ---------------------------------------------------------  
         > ==>> """.trimMargin(">")
    )

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
         > ==>> """.trimMargin(">")
        )

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
    if (wishlistAPI.numberOfActiveWishlits() > 0) {
        val indexToArchive = readNextInt("Enter the index of the wishlist to archive: ")
        if (wishlistAPI.archiveWishlist(indexToArchive)) {
            println("Archive Successful!")
        } else {
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

            if (wishlistAPI.updateWishlist(
                    indxeToUpdate,
                    Wishlist(
                        wishlistName,
                        wishlistDate,
                        wishlistUserName,
                        wishlistCategory,
                        wishlistPriority,
                        false
                    )
                )
            ) {
                println("Update Successful")
            } else {
                println("Update Failed")
            }
        } else {
            println("There is no wishlists for this index number")
        }

    }
}


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


