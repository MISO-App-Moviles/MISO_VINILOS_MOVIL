package com.example.myapplication.view


import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.example.myapplication.R
import com.example.myapplication.view.utils.EspressoTestUtils.checkActionBarTitle
import com.example.myapplication.view.utils.EspressoTestUtils.checkHeaderTitle
import com.example.myapplication.view.utils.EspressoTestUtils.checkMultipleItemsInRecyclerView
import com.example.myapplication.view.utils.EspressoTestUtils.clickBottomNavigationItem
import com.example.myapplication.view.utils.EspressoTestUtils.clickItemInRecyclerView
import org.hamcrest.Matchers
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class CollectorItemDetailTest {

    companion object {
        const val COLLECTOR_TAB = "Coleccionistas"
        const val DETAIL_TITLE = "María Alejandra Palacios"
        const val ALBUM_NAME_1 = "Buscando América"
        const val ARTIST_NAME_1 = "Rubén Blades Bellido de Luna"
        const val ALBUM_NAME_2 = "Poeta del Pueblo"
        const val ARTIST_NAME_2 = "Joan Manuel Serrat Teresa"
        const val ALBUM_NAME_3 = "Buscando América"
        const val ARTIST_NAME_3 = "Andrés Cepeda Cediel"
    }

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun testCollectorItemDetails() {
        Thread.sleep(1000)
        // Navigate to collector's tab and click on the first item
        clickBottomNavigationItem(R.id.navigation_collectors, COLLECTOR_TAB)
        clickItemInRecyclerView(R.id.collectorRv, 0)

        Thread.sleep(1000)

        checkActionBarTitle(DETAIL_TITLE)
//
//        // Asserting items in two RecyclerViews
        checkMultipleItemsInRecyclerView(
            R.id.albumPreviewRv,
            R.id.albumName,
            listOf(ALBUM_NAME_1, ALBUM_NAME_2, ALBUM_NAME_3)
        )
        checkMultipleItemsInRecyclerView(
            R.id.artistPreviewRv,
            R.id.artistName,
            listOf(ARTIST_NAME_1, ARTIST_NAME_2, ARTIST_NAME_3)
        )
//
        // Navigate to the detail of the first album
        clickItemInRecyclerView(R.id.artistPreviewRv, 0)

        Thread.sleep(1000)

        // Asserting the title of the album detail page
        checkActionBarTitle(ARTIST_NAME_1)
    }
}