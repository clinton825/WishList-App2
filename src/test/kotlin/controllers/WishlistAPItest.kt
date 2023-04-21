package controllers

import models.Wishlist
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.time.LocalDate
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue


class WishlistAPITest {
    private var christmas: Wishlist? = null
    private var summerVibe: Wishlist? = null
    private var wedding: Wishlist? = null
    private var winterTime: Wishlist? = null
    private var testApp: Wishlist? = null
    private var populatedWishlists: wishlistAPI?= wishlistAPI()
    private var emptyWishlists: wishlistAPI? = wishlistAPI()

    @BeforeEach
    fun setup() {
        christmas = Wishlist("Christmas ", LocalDate.now(), "Clinton", "Shopping", 5, false,true)
        summerVibe = Wishlist("SummerVibe", LocalDate.now(), "James", "Holiday", 8, false,true)
        wedding = Wishlist("Wedding Plan", LocalDate.now(), "Bempah", "Family", 10, false,true)
        winterTime = Wishlist("WinterTime SnowMan", LocalDate.now(), "Josh", "Holiday", 7, false,false)
        testApp = Wishlist("Test App", LocalDate.now(), "Busu", "Hobby", 9, false,false)

        // adding 5 Wishlist to the wishlists api
        populatedWishlists!!.add(christmas!!)
        populatedWishlists!!.add(summerVibe!!)
        populatedWishlists!!.add(wedding!!)
        populatedWishlists!!.add(winterTime!!)
        populatedWishlists!!.add(testApp!!)

    }
    @AfterEach
    fun tearDown() {
        christmas = null
        summerVibe = null
        wedding = null
        winterTime = null
        testApp = null
        populatedWishlists=null
        emptyWishlists = null
    }
    @Nested
    inner class AddWishLists {
        @Test
        fun ` adding a Wishlist to a populated list adds to ArrayList`() {
            val newWishlist = Wishlist("Football", LocalDate.now(), "John", "Sports", 6, false,false)
            assertEquals(5,populatedWishlists!!.numberOfWishlists())
            assertTrue(populatedWishlists!!.add(newWishlist))
            assertEquals(6,populatedWishlists!!.numberOfWishlists())
            assertEquals(newWishlist,populatedWishlists!!.findWishlist(populatedWishlists!!.numberOfWishlists() -1))

        }

        @Test
        fun ` adding  a Wishlist to an empty list adds to ArrayList`() {
            val newWishlist = Wishlist("Football", LocalDate.now(), "John", "Sports", 6, false,false)
            assertEquals(0,emptyWishlists!!.numberOfWishlists())
            assertTrue(emptyWishlists!!.add(newWishlist))
            assertEquals(1, emptyWishlists!!.numberOfWishlists())
            assertEquals(newWishlist,emptyWishlists!!.findWishlist(emptyWishlists!!.numberOfWishlists()-1))

        }
    }

    @Nested
    inner class ListWishlists {

        @Test
        fun `listAllWishlists returns No Wishlists Stored message When ArrayList is empty`() {
            assertEquals(0, emptyWishlists!!.numberOfWishlists())
            assertTrue(emptyWishlists!!.listAllWishlists().lowercase().contains("no wishlists stored"))
        }

       @Test
        fun `listAllWishlists returns Wishlists when ArrayList has wishlists stored`() {
            assertEquals(5, populatedWishlists!!.numberOfWishlists())
            val wishlistsString = populatedWishlists!!.listAllWishlists().lowercase()
            assertTrue(wishlistsString.contains("christmas"))
            assertTrue(wishlistsString.contains("summervibe"))
            assertTrue(wishlistsString.contains("wedding"))
            assertTrue(wishlistsString.contains("wintertime"))
            assertTrue(wishlistsString.contains("test app"))
        }
        @Test
       fun `listActiveNotes return no active wishlists stored when ArrayList is empty`(){
            assertEquals(0,emptyWishlists!!.numberOfActiveWishlits())
            assertTrue(emptyWishlists!!.listActiveWishlists().lowercase().contains("no active wishlists "))
        }

        @Test
        fun  `listActiveWishlists returns active wishlists when Arraylist has active wishlists stored`() {

            assertEquals(2,populatedWishlists!!.numberOfActiveWishlits())
            val activeWishlistsString = populatedWishlists!!.listActiveWishlists().lowercase()
            assertFalse(activeWishlistsString.contains("christmas"))
            assertFalse(activeWishlistsString.contains("summer vibe"))
            assertFalse(activeWishlistsString.contains("wedding"))
            assertTrue(activeWishlistsString.contains("wintertime"))
            assertFalse(activeWishlistsString.contains("testapp"))
        }
        @Test
        fun `listArchivedWishlists returns no archived wishlists when ArrayList is empty`(){
            assertEquals(0,emptyWishlists!!.numberOfArchivedWishlists())
            assertTrue(emptyWishlists!!.listArchivedWishlists().lowercase().contains("no archived wishlist "))

        }
        @Test
        fun `listArchivedWishlists returns active wishlists when Arraylist has archive wishlists stored`() {

            assertEquals(3,populatedWishlists!!.numberOfArchivedWishlists())
            val archiveWishlistsString = populatedWishlists!!.listArchivedWishlists().lowercase()
            assertTrue(archiveWishlistsString.contains("christmas"))
            assertFalse(archiveWishlistsString.contains("summer vibe"))
            assertTrue(archiveWishlistsString.contains("wedding"))
            assertFalse(archiveWishlistsString.contains("wintertime"))
            assertFalse(archiveWishlistsString.contains("test app"))
        }
    }
    @Test
    fun `listWishlistBySelectedPrioriry returns No Notes when ArrayList is empty`() {
        assertEquals(0, emptyWishlists!!.numberOfWishlists())
        assertTrue(emptyWishlists!!.listWishlistBySelectedPrioriry(1).lowercase().contains("no wishlists ")
        )
    }

    @Test
    fun `listWishlistBySelectedPrioriry returns no notes when no notes of that priority exist`() {
        //Priority 1 (1 note), 2 (none), 3 (1 note). 4 (2 notes), 5 (1 note)
        assertEquals(5, populatedWishlists!!.numberOfWishlists())
        val priority2String = populatedWishlists!!.listWishlistBySelectedPrioriry(2).lowercase()
        assertTrue(priority2String.contains("no wishlists"))
        assertTrue(priority2String.contains("2"))
    }

    @Test
    fun `listWishlistBySelectedPrioriry returns all notes that match that priority when notes of that priority exist`() {
        assertEquals(5, populatedWishlists!!.numberOfWishlists())
        val priority1String = populatedWishlists!!.listWishlistBySelectedPrioriry(5).lowercase()
        assertFalse(priority1String.contains("1 wishlist"))
        assertTrue(priority1String.contains("priority 5"))
        assertTrue(priority1String.contains("christmas"))
        assertFalse(priority1String.contains("summer vibe"))
        assertFalse(priority1String.contains("wedding"))
        assertFalse(priority1String.contains("wintertime"))
        assertFalse(priority1String.contains("test app"))


        val priority4String = populatedWishlists!!.listWishlistBySelectedPrioriry(9).lowercase(Locale.getDefault())
        assertFalse(priority4String.contains("2 wishlist"))
        assertFalse(priority4String.contains("priority 4"))
        assertFalse(priority4String.contains("christmas"))
        assertFalse(priority4String.contains("summer vibe"))
        assertFalse(priority4String.contains("wedding"))
        assertFalse(priority4String.contains("wintertime"))
        assertTrue(priority4String.contains("test app"))
    }
    @Nested
    inner class DeleteWishLists {

        @Test
        fun `deleting a Wishlist that does not exist, returns null`() {
            assertNull(emptyWishlists!!.deleteWishlist(0))
            assertNull(populatedWishlists!!.deleteWishlist(-1))
            assertNull(populatedWishlists!!.deleteWishlist(5))
        }

//        @Test
//        fun `deleting a wishlist that exists delete and returns deleted object`() {
//            assertEquals(5, populatedWishlists!!.numberOfWishlists())
////            assertEquals(Test App, populatedWishlists!!.deleteWishlist(4))
//            assertEquals(3, populatedWishlists!!.numberOfWishlists())
//            assertEquals(summerVibe, populatedWishlists!!.deleteWishlist(0))
//            assertEquals(3, populatedWishlists!!.numberOfWishlists())
//        }
    }

}
