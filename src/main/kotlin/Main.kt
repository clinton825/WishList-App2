import utils.ScannerInput
import java.lang.System.exit




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
    println("You chose Add WishList")
}

fun listWishList() {
    println("You chose List WishLists")
}

fun updateWishList() {
    println("You chose Update WishList")
}

fun deleteWishList() {
    println("You chose Delete WishList")
}

fun exitApp() {
    println("Exiting...bye")
    exit(0)
}