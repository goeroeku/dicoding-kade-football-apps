package ic.aiczone.cifootballapps

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.Espresso.pressBack
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.action.ViewActions.typeText
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import ic.aiczone.cifootballapps.R.id.*
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.allOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    @Rule
    @JvmField
    var mActivityRule: ActivityTestRule<MainActivity> = object : ActivityTestRule<MainActivity>(MainActivity::class.java) {}

    @Test
    fun testMainActivity() {

        // check recycler view
        onView(withTagValue(`is`("rvPrevEventFrag")))
                .check(matches(isDisplayed()))

        Thread.sleep(5000)

        // click 1st card
        onView(withTagValue(`is`("rvPrevEventFrag"))).perform(
                actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click())
        )

        Thread.sleep(3000)

        // check favorite button
        onView(withId(R.id.favorite_add))
                .check(matches(isDisplayed()))

        // click favorite button
        onView(withId(R.id.favorite_add))
                .perform(click())

        onView(withText("Added to favorite"))
                .check(matches(isDisplayed()))

        Thread.sleep(3000)

        // click back button
        pressBack()

        // check favorite navigation on bottom
        onView(withId(nav_favorite))
                .check(matches(isDisplayed()))
        // click favorite navigation
        onView(withId(nav_favorite))
                .perform(click())

        Thread.sleep(3000)

        // click 1st card
        onView(withTagValue(`is`("rvFavMatchesFrag"))).perform(
                actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click())
        )

        Thread.sleep(3000)

        // check favorite button
        onView(withId(R.id.favorite_add))
                .check(matches(isDisplayed()))

        // click favorite button
        onView(withId(R.id.favorite_add))
                .perform(click())

        onView(withText("Removed from favorite"))
                .check(matches(isDisplayed()))

        Thread.sleep(3000)

        // click back button
        pressBack()

        // check next navigation
        onView(withId(nav_match))
                .check(matches(isDisplayed()))

        Thread.sleep(3000)

        // click next navigation
        onView(withId(nav_team))
                .perform(click())

        // check recycler view
        onView(withTagValue(`is`("rvTeamFrag")))
                .check(matches(isDisplayed()))

        Thread.sleep(5000)

        // click 1st card
        onView(withTagValue(`is`("rvTeamFrag"))).perform(
                actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click())
        )

        onView(withId(R.id.main_layout_detai_team))
                .check(matches(isDisplayed()))

        Thread.sleep(3000)

        // check favorite button
        onView(withId(R.id.favorite_add))
                .check(matches(isDisplayed()))

        // click favorite button
        onView(withId(R.id.favorite_add))
                .perform(click())

        onView(withText("Added to favorite"))
                .check(matches(isDisplayed()))

        Thread.sleep(3000)

        // click back button
        pressBack()

        // check next navigation
        onView(withId(nav_match))
                .check(matches(isDisplayed()))

        Thread.sleep(3000)

        // check favorite navigation on bottom
        onView(withId(nav_favorite))
                .check(matches(isDisplayed()))

        // click favorite navigation
        onView(withId(nav_favorite))
                .perform(click())

        Thread.sleep(3000)

        val matcher = allOf(withText("TEAMS"),
                isDescendantOfA(withId(R.id.fav_tabs)))

        onView(matcher).check(matches(isDisplayed()))

        onView(matcher).perform(click())

        // click 1st card
        onView(withTagValue(`is`("rvFavTeamFrag"))).perform(
                actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click())
        )

        Thread.sleep(3000)

        // check favorite button
        onView(withId(R.id.favorite_add))
                .check(matches(isDisplayed()))

        // click favorite button
        onView(withId(R.id.favorite_add))
                .perform(click())

        onView(withText("Removed from favorite"))
                .check(matches(isDisplayed()))

        Thread.sleep(3000)

        // click back button
        pressBack()

        // lanjut test pencarian
        testTeamFragment()

    }

    @Test
    fun testTeamFragment() {

        // check next navigation
        onView(withId(nav_match))
                .check(matches(isDisplayed()))

        Thread.sleep(3000)

        // click next navigation
        onView(withId(nav_team))
                .perform(click())

        // check recycler view
        onView(withTagValue(`is`("rvTeamFrag")))
                .check(matches(isDisplayed()))

        onView(withId(R.id.search))
                .check(matches(isDisplayed()))
        onView(withId(R.id.search))
                .perform(click())

        Thread.sleep(3000)

        onView(withId(search_src_text))
                .check(matches(isDisplayed()))

        onView(withId(search_src_text))
                .perform(typeText("Ars"))
        Thread.sleep(1000)
        onView(withId(search_src_text))
                .perform(typeText("enal"))

        Thread.sleep(5000)

        // check recycler view
        onView(withTagValue(`is`("rvTeamFrag")))
                .perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))

        Thread.sleep(5000)

        pressBack()

        Thread.sleep(5000)
    }
}