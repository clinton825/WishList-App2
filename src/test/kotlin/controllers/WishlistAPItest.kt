package controllers

import models.Wishlist
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue


class WishlistAPItest {
private var christmas: Wishlist? = null
    private var summerVibe: Wishlist? = null
    private var weeding: Wishlist? =null
    private  var winterTime: Wishlist? = null
    private var testApp : Wishlist? =null
    private var populateWishlist: wishlistAPI? = wishlistAPI()
    private var emptyWishlist: wishlistAPI? = wishlistAPI()

    @BeforeEach
    fun setup(){
        christmas = Wishlist("Christmas Wishlist",
            "20","clinton","Holiday",6, false)
        summerVibe = Wishlist("Hot Summer","2023","James","Fun",9,false)
        weeding = Wishlist("Weeding Plan","2023","Rachel","Family",10,false)
        winterTime = Wishlist("Snow Man","2020","Bempah","Fun ",8,false)
        testApp = Wishlist("Learning","2020","Ayo","Education",6,false)

        // adding 5 Wishlist to the wishlistsapi
        populateWishlist!!.add(christmas!!)
        populateWishlist!!.add(summerVibe!!)
        populateWishlist!!.add(weeding!!)
        populateWishlist!!.add(winterTime!!)
        populateWishlist!!.add(testApp!!)
    }
    @AfterEach
    fun tearDown(){
        christmas = null
        summerVibe = null
        weeding = null
        winterTime = null
        testApp = null
        populateWishlist= null
        emptyWishlist = null
    }
    @Test
    fun `adding a Wishlist to a populated list adds to Arraylist`(){
        val newWishlist = Wishlist ("Football","2023","Josh","Sports",8, false)
        assertEquals(5,populateWishlist!!.numberOfWishlists())
        assertTrue(populateWishlist!!.add(newWishlist))
        assertEquals(6,populateWishlist!!.numberOfWishlists())
        assertEquals(newWishlist,populateWishlist!!.findWishlist(populateWishlist!!.numberOfWishlists()-1))
    }
    @Test
    fun `adding a Wishlist to an empty list adds to Arraylist`(){
        val newWishlist = Wishlist ("Football","2023","Josh","Sports",8, false)
        assertEquals(0,emptyWishlist!!.numberOfWishlists())
        assertTrue(emptyWishlist!!.add(newWishlist))
        assertEquals(1,emptyWishlist!!.numberOfWishlists())
        assertEquals(newWishlist, emptyWishlist!!.findWishlist(emptyWishlist!!.numberOfWishlists()-1))
    }
}

