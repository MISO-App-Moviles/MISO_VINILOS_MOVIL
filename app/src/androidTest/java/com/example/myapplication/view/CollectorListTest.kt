package com.example.myapplication.view

import androidx.test.espresso.Espresso
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

import org.hamcrest.Matchers
import org.junit.Test

@LargeTest
@RunWith(AndroidJUnit4::class)
class CollectorListTest {

    companion object {
        const val NAVIGATION_ARTIST_TAB = "Artistas"
        const val NAVIGATION_COLLECTOR_TAB = "Coleccionistas"
        const val COLLECTOR_NAME = "Manolo Bellon"
        const val COLLECTOR_TELEPHONE = "3502457896"
        const val COLLECTOR_EMAIL = "manollo@caracol.com.co"
        const val TITLE = "Coleccionistas"
    }

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(MainActivity::class.java)
    @Test
    fun testFirstCollectorDetails() {
        Thread.sleep(1000)
        clickBottomNavigationItem(R.id.navigation_artist, NAVIGATION_ARTIST_TAB)
        clickBottomNavigationItem(R.id.navigation_collectors, NAVIGATION_COLLECTOR_TAB)

        checkHeaderTitle(TITLE)

        Thread.sleep(2000)
        checkItemInRecyclerView(R.id.collectorRv, 0, R.id.imageView)
        checkItemInRecyclerView(R.id.collectorRv, 0, R.id.collectorName, COLLECTOR_NAME)
        checkItemInRecyclerView(R.id.collectorRv, 0, R.id.collectorEmail, COLLECTOR_EMAIL)
        checkItemInRecyclerView(R.id.collectorRv, 0, R.id.collectorTelephone, COLLECTOR_TELEPHONE)
    }
}