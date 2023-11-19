package com.example.myapplication.view


import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.example.myapplication.R
import com.example.myapplication.view.utils.EspressoTestUtils
import com.example.myapplication.view.utils.EspressoTestUtils.checkActionBarTitle
import com.example.myapplication.view.utils.EspressoTestUtils.checkMultipleItemsInRecyclerView
import com.example.myapplication.view.utils.EspressoTestUtils.clickBottomNavigationItem
import com.example.myapplication.view.utils.EspressoTestUtils.clickItemInRecyclerView
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class AlbumDetailTest {

    companion object {
        const val ALBUM_TAB = "Albums"
        const val DETAIL_TITLE = "Buscando América"
        const val TRACK_1 = "Decisiones"
        const val TRACK_2 = "Desapariciones"
    }

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun testAlbumItemDetails() {
        Thread.sleep(1000)
        // Navigate to collector's tab and click on the first item
        clickBottomNavigationItem(R.id.navigation_album_list, ALBUM_TAB)
        clickItemInRecyclerView(R.id.albumRv, 1)

        Thread.sleep(1000)

        checkActionBarTitle(DETAIL_TITLE)

        onView(withId(R.id.albumSV)).perform(swipeUp());


        // Asserting items in two RecyclerViews
        checkMultipleItemsInRecyclerView(
            R.id.trackRv,
            R.id.trackName,
            listOf(TRACK_1, TRACK_2)
        )

        Thread.sleep(1000)
    }
}