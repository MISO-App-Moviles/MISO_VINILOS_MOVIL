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
import org.junit.Test

@LargeTest
@RunWith(AndroidJUnit4::class)
class AlbumListTest {

    companion object {
        const val NAVIGATION_ARTIST_TAB = "Artistas"
        const val NAVIGATION_ALBUM_TAB = "Albumes"
        const val ALBUM_NAME = "Buscando América"
        const val RELEASE_DATE = "1984-08-01"
        const val GENRE = "Salsa"
    }

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(MainActivity::class.java)
    @Test
    fun testFirstAlbumDetails() {
        clickBottomNavigationItem(R.id.navigation_artist, NAVIGATION_ARTIST_TAB)
        clickBottomNavigationItem(R.id.navigation_album_list, NAVIGATION_ALBUM_TAB)

        checkItemInRecyclerView(R.id.albumRv, 0, R.id.imageView)
        checkItemInRecyclerView(R.id.albumRv, 0, R.id.textView6, ALBUM_NAME)
        checkItemInRecyclerView(R.id.albumRv, 0, R.id.textView, RELEASE_DATE)
        checkItemInRecyclerView(R.id.albumRv, 0, R.id.textView5, GENRE)
    }
}
