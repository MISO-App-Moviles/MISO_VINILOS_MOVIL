package com.example.myapplication.view.utils

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.matcher.BoundedMatcher
import org.hamcrest.Description
import org.hamcrest.TypeSafeMatcher
import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.espresso.action.ViewActions.click
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf

object EspressoTestUtils {

    fun clickBottomNavigationItem(itemId: Int, contentDesc: String) {
        onView(allOf(withId(itemId), withContentDescription(contentDesc), isDisplayed())).perform(
            click()
        )
    }

    fun checkItemInRecyclerView(
        recyclerViewId: Int,
        position: Int,
        viewId: Int,
        text: String? = null
    ) {
        val matcher: Matcher<View> = if (text != null) {
            hasDescendant(allOf(withId(viewId), withText(text)))
        } else {
            hasDescendant(withId(viewId))
        }
        onView(withId(recyclerViewId)).check(matches(atPositionInRecyclerView(position, matcher)))
    }

    fun atPositionInParent(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {
        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }

    fun atPositionInRecyclerView(
        position: Int, itemMatcher: Matcher<View>
    ): Matcher<View> {
        return object : BoundedMatcher<View, RecyclerView>(RecyclerView::class.java) {
            override fun describeTo(description: Description) {
                description.appendText("Item at position $position: ")
                itemMatcher.describeTo(description)
            }

            override fun matchesSafely(recyclerView: RecyclerView): Boolean {
                val viewHolder = recyclerView.findViewHolderForAdapterPosition(position)
                return viewHolder != null && itemMatcher.matches(viewHolder.itemView)
            }
        }
    }
}
