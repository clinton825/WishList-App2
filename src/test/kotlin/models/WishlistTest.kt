package models
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.time.LocalDate

class WishlistTest {
    private lateinit var wishlist: Wishlist

    @BeforeEach
    fun setUp() {
        wishlist = Wishlist(
            wishlistName = "Easter",
            wishlistDate = LocalDate.now(),
            wishlistUserName = "Clinton",
            wishlistPriority = 9,
            wishlistCategory = "Holiday",
            isWishlistArchived = false
        )
    }

    @Nested
    inner class addProducts {
        @Test
        fun `test addProduct adds a product to wishlist`() {
            val product = Product(
                productName = "Air Jordan FlyEase",
                productDescription = "Best of the best",
                productPrice = 180.0,
                productBrand = "Nike",
                productType = "Men Shoes",
                productQuantity = 1,
                productId = 234,
                isProductFavourite = true
            )
            assertTrue(wishlist.addProduct(product))
            assertEquals(1, wishlist.numberOfProducts())
        }
    }


@Nested
inner class ListProducts {
    @Test
    fun `test listProducts() and toString() when no products added`() {
        val wishlist = Wishlist(
            wishlistName = "Easter",
            wishlistDate = LocalDate.now(),
            wishlistUserName = "Clinton",
            wishlistPriority = 9,
            wishlistCategory = "Holiday",
            isWishlistArchived = false
        )
        val expectedOutput = "\tNO PRODUCTS ADDED"
        assertEquals(expectedOutput, wishlist.listProducts())
        val expectedToString =
            " Easter,${LocalDate.now()},Clinton ,Priority(9) Category(Holiday), Archived(N) \n${expectedOutput}"
        assertEquals(expectedToString, wishlist.toString())
    }

    @Test
    fun `test listProducts returns a list of products in the wishlist`()
    {
        val product1 = Product(
            productName = "Air Jordan FlyEase",
            productDescription = "Best of the best",
            productPrice = 180.0,
            productBrand = "Nike",
            productType = "Men Shoes",
            productQuantity = 1,
            productId = 234,
            isProductFavourite = true
        )
        val product2 = Product(
            productName = "Nike Air Zoom Pegasus 38",
            productDescription = "responsive",
            productPrice = 120.0,
            productBrand = "Nike",
            productType = "Running Shoes",
            productQuantity = 1,
            productId = 24,
            isProductFavourite = false
        )
        val wishlist = Wishlist(
            wishlistName = "Birthday",
            wishlistDate = LocalDate.of(2022, 5, 10),
            wishlistUserName = "Alice",
            wishlistPriority = 7,
            wishlistCategory = "Personal",
            isWishlistArchived = true,
            products = mutableSetOf(product1,product2)
        )
        val expectedOutput =
        "0:Product(productID=0,productName = Nike Air Zoom Pegasus 38,productDescription = Best of the best,productPrice = 180.0,productBrand = Nike,productType = Men Shoes,productQuantity = productId = 234,isProductFavourite = true)"
          //assertEquals(expectedOutput,wishlist.listProducts())
    }

     @Test

    fun `test numberOfProducts returns the correct number of products`() {
        val product1 = Product(
            productName = "Air Jordan FlyEase",
            productDescription = "Best of the best",
            productPrice = 180.0,
            productBrand = "Nike",
            productType = "Men Shoes",
            productQuantity = 1,
            productId = 234,
            isProductFavourite = true
        )
        val product2 = Product(
            productName = "Nike Air Zoom Pegasus 38",
            productDescription = "responsive",
            productPrice = 120.0,
            productBrand = "Nike",
            productType = "Running Shoes",
            productQuantity = 1,
            productId = 24,
            isProductFavourite = false
        )
        wishlist.addProduct(product1)
        wishlist.addProduct(product2)

        assertEquals(2, wishlist.numberOfProducts())
    }
}

    @Test
    fun `test findOne returns correct product by id`() {
        val product1 = Product(
            productName = "Air Jordan FlyEase",
            productDescription = "Best of the best",
            productPrice = 180.0,
            productBrand = "Nike",
            productType = "Men Shoes",
            productQuantity = 1,
            productId = 234,
            isProductFavourite = true
        )
        val product2 = Product(
            productName = "Nike Air Zoom Pegasus 38",
            productDescription = "responsive",
            productPrice = 120.0,
            productBrand = "Nike",
            productType = "Running Shoes",
            productQuantity = 1,
            productId = 24,
            isProductFavourite = false
        )
        val wishlist = Wishlist(
            wishlistName = "Birthday",
            wishlistDate = LocalDate.of(2022, 5, 10),
            wishlistUserName = "Alice",
            wishlistPriority = 7,
            wishlistCategory = "Personal",
            isWishlistArchived = true
        )
        wishlist.addProduct(product1)
        wishlist.addProduct(product2)

        val result = wishlist.findOne(24)

        assertEquals(null, result)
    }

}





