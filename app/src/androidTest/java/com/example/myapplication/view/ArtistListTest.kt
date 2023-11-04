package com.example.myapplication.view

import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.example.myapplication.R
import org.junit.Rule
import org.junit.runner.RunWith
import com.example.myapplication.view.utils.EspressoTestUtils.clickBottomNavigationItem
import com.example.myapplication.view.utils.EspressoTestUtils.checkItemInRecyclerView
import com.example.myapplication.view.utils.EspressoTestUtils.checkHeaderTitle

import org.junit.Test

@LargeTest
@RunWith(AndroidJUnit4::class)
class ArtistListTest {

    companion object {
        const val NAVIGATION_ARTIST_TAB = "Artistas"
        const val NAVIGATION_COLLECTOR_TAB = "Coleccionistas"
        const val ARTIST_NAME = "Rubén Blades Bellido de Luna"
        const val ARTIST_DESCRIPTION = "Es un cantante, compositor, músico, actor, abogado, político y activista panameño. Ha desarrollado gran parte de su carrera artística en la ciudad de Nueva York."
        const val TITLE = "Artistas"
    }

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(MainActivity::class.java)
    @Test
    fun testFirstCollectorDetails() {
        Thread.sleep(1000)
        clickBottomNavigationItem(R.id.navigation_collectors, NAVIGATION_COLLECTOR_TAB)
        clickBottomNavigationItem(R.id.navigation_artist, NAVIGATION_ARTIST_TAB)

        checkHeaderTitle(TITLE)

        Thread.sleep(2000)
        checkItemInRecyclerView(R.id.artistRv, 0, R.id.artistImage)
        checkItemInRecyclerView(R.id.artistRv, 0, R.id.artistName, ARTIST_NAME)
        checkItemInRecyclerView (R.id.artistRv, 0, R.id.artistDescription, ARTIST_DESCRIPTION)
    }
}
