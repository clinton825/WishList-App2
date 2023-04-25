package controllers

import models.Wishlist
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import persistence.JSONSerializer
import persistence.XMLSerializer
import java.io.File
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
    private var populatedWishlists: WishlistAPI?= WishlistAPI(XMLSerializer(File("wishlists.xml")))
    private var emptyWishlists: WishlistAPI?= WishlistAPI(XMLSerializer(File("wishlists.xml")))

    @BeforeEach
    fun setup() {
        christmas = Wishlist("Christmas ", LocalDate.now(), "Clinton", "Shopping", 5, true)
        summerVibe = Wishlist("SummerVibe", LocalDate.now(), "James", "Holiday", 8, true)
        wedding = Wishlist("Wedding Plan", LocalDate.now(), "Bempah", "Family", 10, true)
        winterTime = Wishlist("WinterTime SnowMan", LocalDate.now(), "Josh", "Holiday", 7, false)
        testApp = Wishlist("Test App", LocalDate.now(), "Busu", "Hobby", 9, false)

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
            val newWishlist = Wishlist("Football", LocalDate.now(), "John", "Sports", 6, false)
            assertEquals(5,populatedWishlists!!.numberOfWishlists())
            assertTrue(populatedWishlists!!.add(newWishlist))
            assertEquals(6,populatedWishlists!!.numberOfWishlists())
            assertEquals(newWishlist,populatedWishlists!!.findWishlist(populatedWishlists!!.numberOfWishlists() -1))

        }

        @Test
        fun ` adding  a Wishlist to an empty list adds to ArrayList`() {
            val newWishlist = Wishlist("Football", LocalDate.now(), "John", "Sports", 6, false)
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

       @Test
        fun `deleting a wishlist that exists delete and returns deleted object`() {
            assertEquals(5, populatedWishlists!!.numberOfWishlists())
            assertEquals(testApp, populatedWishlists!!.deleteWishlist(4))
            assertEquals(4, populatedWishlists!!.numberOfWishlists())
            assertEquals(christmas, populatedWishlists!!.deleteWishlist(0))
            assertEquals(3, populatedWishlists!!.numberOfWishlists())
        }
    }

    @Nested
    inner class UpdateWishlists {
        @Test
        fun `updating a wishlist that does not exist returns false`(){
            assertFalse(populatedWishlists!!.updateWishlist(6, Wishlist("Updating Wishlist", LocalDate.now(), "Shop","Work", 1,false )))
            assertFalse(populatedWishlists!!.updateWishlist(-1, Wishlist("Updating Wishlist", LocalDate.now(), "School","Work", 10,false)))
            assertFalse(emptyWishlists!!.updateWishlist(0, Wishlist("Updating Wishlist", LocalDate.now(), "Education","School" ,8,false)))
        }

        @Test
        fun `updating a wishlist that exists returns true and updates`() {
            //check note 5 exists and check the contents
            assertEquals(testApp, populatedWishlists!!.findWishlist(4))
            assertEquals("Test App", populatedWishlists!!.findWishlist(4)!!.wishlistName)
            assertEquals(9, populatedWishlists!!.findWishlist(4)!!.wishlistPriority)
            assertEquals("Hobby", populatedWishlists!!.findWishlist(4)!!.wishlistCategory)

         // update note 5 with new information and ensure contents updated successfully
           assertTrue(populatedWishlists!!.updateWishlist(4, Wishlist("Updating Wishlist",LocalDate.now(), "College", "Education",5,false)))
            assertEquals("Updating Wishlist", populatedWishlists!!.findWishlist(4)!!.wishlistName)
            assertEquals(5, populatedWishlists!!.findWishlist(4)!!.wishlistPriority)
           assertEquals("Education", populatedWishlists!!.findWishlist(4)!!.wishlistCategory)
        }


    }

    @Nested
    inner class PersistenceTests{

        @Test
        fun `saving and loading an empty collection in XML doesn't crash app` ()  {
            val storingWishlists = WishlistAPI(XMLSerializer(File("wishlists.xml")))
            storingWishlists.store()

            val loadedWishlists = WishlistAPI(XMLSerializer(File("wishlists.xml")))
            loadedWishlists.load()

            assertEquals(0,storingWishlists.numberOfWishlists())
            assertEquals(0,loadedWishlists.numberOfWishlists())
            assertEquals(storingWishlists.numberOfWishlists(), loadedWishlists.numberOfWishlists())

        }

        @Test
        fun `saving and loading a loaded collection in XML doesn't lose data`(){
            val storingWishlists = WishlistAPI(XMLSerializer(File("wishlists.xml")))
            storingWishlists.add(testApp!!)
            storingWishlists.add(christmas!!)
            storingWishlists.add(summerVibe!!)
            storingWishlists.store()

            val loadedWishlists = WishlistAPI(XMLSerializer(File("wishlists.xml")))
            loadedWishlists.load()

            assertEquals(3,storingWishlists.numberOfWishlists())
            assertEquals(3,loadedWishlists.numberOfWishlists())
            assertEquals(storingWishlists.numberOfWishlists(), loadedWishlists.numberOfWishlists())
            assertEquals(storingWishlists.findWishlist(0), loadedWishlists.findWishlist(0))
            assertEquals(storingWishlists.findWishlist(1), loadedWishlists.findWishlist(1))
            assertEquals(storingWishlists.findWishlist(2), loadedWishlists.findWishlist(2))



        }

        @Test
        fun `saving and loading an empty collection in JSON doesn't crash app` ()  {
            val storingWishlists = WishlistAPI(JSONSerializer(File("wishlists.json")))
            storingWishlists.store()

            val loadedWishlists = WishlistAPI(JSONSerializer(File("wishlists.json")))
            loadedWishlists.load()

            assertEquals(0,storingWishlists.numberOfWishlists())
            assertEquals(0,loadedWishlists.numberOfWishlists())
            assertEquals(storingWishlists.numberOfWishlists(), loadedWishlists.numberOfWishlists())

        }

        @Test
        fun `saving and loading a loaded collection in JSON doesn't lose data`(){
            val storingWishlists = WishlistAPI(JSONSerializer(File("wishlists.json")))
            storingWishlists.add(testApp!!)
            storingWishlists.add(christmas!!)
            storingWishlists.add(summerVibe!!)
            storingWishlists.store()

            val loadedWishlists = WishlistAPI(JSONSerializer(File("wishlists.json")))
            loadedWishlists.load()

            assertEquals(3,storingWishlists.numberOfWishlists())
            assertEquals(3,loadedWishlists.numberOfWishlists())
            assertEquals(storingWishlists.numberOfWishlists(), loadedWishlists.numberOfWishlists())
            assertEquals(storingWishlists.findWishlist(0), loadedWishlists.findWishlist(0))
            assertEquals(storingWishlists.findWishlist(1), loadedWishlists.findWishlist(1))
            assertEquals(storingWishlists.findWishlist(2), loadedWishlists.findWishlist(2))



        }
    }

    @Nested
    inner class ArchiveWislists {
        @Test
        fun `archiving a wishlist that does not exist returns false`(){
            assertFalse(populatedWishlists!!.archiveWishlist(6))
            assertFalse(populatedWishlists!!.archiveWishlist(-1))
            assertFalse(emptyWishlists!!.archiveWishlist(0))
        }

        @Test
        fun `archiving an already archived wishlist returns false`(){
            assertTrue(populatedWishlists!!.findWishlist(2)!!.isWishlistArchived)
            assertFalse(populatedWishlists!!.archiveWishlist(2))
        }

        @Test
        fun `archiving an active wishlist that exists returns true and archives`() {
            assertFalse(populatedWishlists!!.findWishlist(4)!!.isWishlistArchived)
            assertTrue(populatedWishlists!!.archiveWishlist(4))
            assertTrue(populatedWishlists!!.findWishlist(4)!!.isWishlistArchived)
        }
    }

    @Nested
    inner class CountingMethods {

        @Test
        fun numberOfWishlistsCalculatedCorrectly() {
            assertEquals(5, populatedWishlists!!.numberOfWishlists())
            assertEquals(0, emptyWishlists!!.numberOfWishlists())
        }

        @Test
        fun numberOfArchivedWishlistCalculatedCorrectly() {
            assertEquals(3, populatedWishlists!!.numberOfArchivedWishlists())
            assertEquals(0, emptyWishlists!!.numberOfArchivedWishlists())
        }

        @Test
        fun numberOfActiveWishlistsCalculatedCorrectly() {
            assertEquals(2, populatedWishlists!!.numberOfActiveWishlits())
            assertEquals(0, emptyWishlists!!.numberOfActiveWishlits())
        }

        @Test
        fun numberOfWishlistsByPriorityCalculatedCorrectly() {
            assertEquals(0, populatedWishlists!!.numberOfWishlistsByPriority(1))
            assertEquals(0, populatedWishlists!!.numberOfWishlistsByPriority(2))
            assertEquals(0, populatedWishlists!!.numberOfWishlistsByPriority(3))
            assertEquals(0, populatedWishlists!!.numberOfWishlistsByPriority(4))
            assertEquals(1, populatedWishlists!!.numberOfWishlistsByPriority(5))
            assertEquals(0, emptyWishlists!!.numberOfWishlistsByPriority(1))
        }
    }

}
