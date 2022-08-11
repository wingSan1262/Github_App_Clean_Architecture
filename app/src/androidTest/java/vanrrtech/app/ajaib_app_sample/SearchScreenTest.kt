package vanrrtech.app.ajaib_app_sample

import android.content.Intent
import android.content.res.Resources
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.not
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import vanrrtech.app.ajaib_app_sample.features.github.UserItemViewHolder
import vanrrtech.app.ajaib_app_sample.features.github.home.TopActivity


@RunWith(AndroidJUnit4::class)
@LargeTest
class SearchScreenTest {
    val intent = Intent(ApplicationProvider.getApplicationContext(), TopActivity::class.java).putExtra("title", "Testing rules!")
    @Rule @JvmField val activityScenario = ActivityScenarioRule<TopActivity>(intent)

    @Before
    fun setup(){
        activityScenario.scenario.onActivity {

        }
    }

    @Test fun fragment_shownUiComponent() {
        runBlocking {
            delay(1000)
            onView(withId(R.id.cv_search)).check(matches(isDisplayed()))
            onView(withId(R.id.user_rv)).check(matches(isDisplayed()))
        }
    }
    @Test fun fragment_checkTheEditText() {
        runBlocking {
            delay(1000)
            onView(withId(R.id.cv_search)).check(matches(isDisplayed()))
            onView(withId(R.id.search_field)).perform(typeText("test"))
            Espresso.closeSoftKeyboard()
            onView(withId(R.id.search_field)).check(
                matches(withText("test")))
        }
    }

    @Test
    fun fragment_checkUserDetailsContent() {
        runBlocking {
            delay(3000)
            onView(withId(R.id.user_rv)).perform(
                // scrollTo will fail the test if no item matches.
                RecyclerViewActions.actionOnItemAtPosition<UserItemViewHolder>(
                    3,
                    click()
                )
            )
            delay(3000)
            onView(withId(R.id.user_account_id)).check(
                matches(withText("@wycats")))
            onView(withId(R.id.user_name)).check(
                matches(withText("Yehuda Katz")))
            onView(withId(R.id.user_desc_tv)).check(
                matches(not(isDisplayed())))
            onView(withId(R.id.follower_tv)).check(
                matches(withText("10000 Followers")))
            onView(withId(R.id.location_tv_tv)).check(
                matches(withText("Portland, OR")))
            onView(withId(R.id.following_tv)).check(
                matches(withText("11 Following")))
            onView(withId(R.id.emailTvDetail)).check(
                matches(withText("Not Available")))
        }
    }

    @Test
    fun fragment_scrollCheckRecyclerView() {
        runBlocking {
            delay(3000)
            onView(withId(R.id.user_rv)).perform(
                // scrollTo will fail the test if no item matches.
                RecyclerViewActions.actionOnItemAtPosition<UserItemViewHolder>(
                    28,
                    scrollTo()
                )
            )

            delay(3000)
            onView(
                RecyclerViewMatcher(R.id.user_rv)
                .atPositionOnView(28, R.id.name_tv))
                .check(matches(withText("mojodna")))
        }
    }

    @Test
    fun singleItem_searchingFunctionOnRecyclerViewItem() {
        runBlocking {
            delay(1000)
            onView(withId(R.id.cv_search)).check(matches(isDisplayed()))
            onView(withId(R.id.search_field)).perform(typeText("wingSan1262"))
            Espresso.closeSoftKeyboard()
            onView(withId(R.id.search_field)).check(
                matches(withText("wingSan1262")))
            delay(3000)
            val textContent = getText(RecyclerViewMatcher(R.id.user_rv)
                    .atPositionOnView(0, R.id.name_tv))
            if (textContent?.contains("wingSan1262", true) != true)
                throw Throwable("search function fail")
        }
    }

    @Test
    fun multipleItemResult_searchingFunctionOnRecyclerViewItem() {
        runBlocking {
            delay(1000)
            onView(withId(R.id.cv_search)).check(matches(isDisplayed()))
            onView(withId(R.id.search_field)).perform(typeText("tes")) // TODO only work for this query
            Espresso.closeSoftKeyboard()
            onView(withId(R.id.search_field)).check(
                matches(withText("tes")))
            delay(2000)
            val iterator = 1
            for(i in 0..15){

                onView(withId(R.id.user_rv)).perform(
                    // scrollTo will fail the test if no item matches.
                    RecyclerViewActions.actionOnItemAtPosition<UserItemViewHolder>(
                        i,
                        scrollTo()
                    )
                )

                delay(500)

                val textContent = getText(RecyclerViewMatcher(R.id.user_rv)
                    .atPositionOnView(i, R.id.name_tv))
                if (textContent?.contains("tes", true) != true)
                    throw Throwable("search function fail")
            }

            for(i in 15 downTo 0){

                onView(withId(R.id.user_rv)).perform(
                    // scrollTo will fail the test if no item matches.
                    RecyclerViewActions.actionOnItemAtPosition<UserItemViewHolder>(
                        i,
                        scrollTo()
                    )
                )

                delay(500)

                val textContent = getText(RecyclerViewMatcher(R.id.user_rv)
                    .atPositionOnView(i, R.id.name_tv))
                if (textContent?.contains("tes", true) != true)
                    throw Throwable("search function fail")
            }
        }
    }



}

fun getText(matcher: Matcher<View?>?): String? {
    val stringHolder = arrayOf<String?>(null)
    onView(matcher).perform(object : ViewAction {
        override fun getConstraints(): Matcher<View> {
            return isAssignableFrom(TextView::class.java)
        }

        override fun getDescription(): String {
            return "getting text from a TextView"
        }

        override fun perform(uiController: UiController, view: View) {
            val tv = view as TextView //Save, because of check in getConstraints()
            stringHolder[0] = tv.text.toString()
        }
    })
    return stringHolder[0]
}

/** Helper test class because android is horrible at testing !!!
 * or atleast provide with some decent API
 * gosh, you can provide that good looking coroutine
 * but cannot do the same with this
 * how about follow the step on Mockk . . .
 * Gee . . .
 * **/
class RecyclerViewMatcher(private val recyclerViewId: Int) {
    fun atPosition(position: Int): TypeSafeMatcher<View?> {
        return atPositionOnView(position, -1)
    }

    fun atPositionOnView(position: Int, targetViewId: Int): TypeSafeMatcher<View?> {
        return object : TypeSafeMatcher<View?>() {
            var resources: Resources? = null
            var childView: View? = null
            override fun describeTo(description: org.hamcrest.Description?) {
                var idDescription = Integer.toString(recyclerViewId)
                if (resources != null) {
                    idDescription = try {
                        resources!!.getResourceName(recyclerViewId)
                    } catch (var4: Resources.NotFoundException) {
                        String.format(
                            "%s (resource name not found)",
                            *arrayOf<Any>(Integer.valueOf(recyclerViewId))
                        )
                    }
                }
                description?.appendText("with id: $idDescription")
            }

            override fun matchesSafely(item: View?): Boolean {
                resources = item?.resources
                if (childView == null) {
                    val recyclerView = item?.rootView?.findViewById<View>(
                        recyclerViewId
                    ) as RecyclerView
                    childView = if (recyclerView != null && recyclerView.id == recyclerViewId) {
                        recyclerView.findViewHolderForAdapterPosition(position)!!.itemView
                    } else {
                        return false
                    }
                }
                return if (targetViewId == -1) {
                    item === childView
                } else {
                    val targetView = childView!!.findViewById<View>(targetViewId)
                    item === targetView
                }
            }
        }
    }
}