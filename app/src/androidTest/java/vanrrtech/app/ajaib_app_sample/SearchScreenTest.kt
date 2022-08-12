package vanrrtech.app.ajaib_app_sample

import android.content.Intent
import android.content.res.Resources
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.BoundedMatcher
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
import org.junit.FixMethodOrder
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters
import org.mockito.internal.verification.Description
import vanrrtech.app.ajaib_app_sample.features.Imdb.MovieListHolder
import vanrrtech.app.ajaib_app_sample.features.home.TopActivity


@RunWith(AndroidJUnit4::class)
@LargeTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class SearchScreenTest {

    val intent = Intent(ApplicationProvider.getApplicationContext(), TopActivity::class.java).putExtra("title", "Testing rules!")
    @Rule @JvmField val activityScenario = ActivityScenarioRule<TopActivity>(intent)

    @Before
    fun setup(){
    }

    @Test fun A_ableToLoadMore(){
        runBlocking {
            delay(5000)
            onView(withId(R.id.movie_rv)).perform(
                // scrollTo will fail the test if no item matches.
                RecyclerViewActions.actionOnItemAtPosition<MovieListHolder>(
                    4,
                    scrollTo()
                )
            )
            delay(500)
            onView(withId(R.id.swipe_refresh)).check(matches(isRefreshing()))

            /** check for scroll and render capabilities**/
            delay(2000)
            onView(withId(R.id.movie_rv)).perform(
                // scrollTo will fail the test if no item matches.
                RecyclerViewActions.actionOnItemAtPosition<MovieListHolder>(
                    9,
                    scrollTo()
                )
            )
            /** check for child content**/
            onView(
                RecyclerViewMatcher(R.id.movie_rv)
                    .atPositionOnView(9, R.id.tv_title))
                .check(matches(isDisplayed()))
            onView(
                RecyclerViewMatcher(R.id.movie_rv)
                    .atPositionOnView(9, R.id.image_poster_list_item))
                .check(matches(isDisplayed()))

            val textContent1 = getText(RecyclerViewMatcher(R.id.movie_rv)
                .atPositionOnView(9, R.id.tv_title))
            if(textContent1?.isEmpty() == true) { throw Throwable("content should not be emoty")}

            /** check for scroll and render capabilities**/
            delay(2000)
            onView(withId(R.id.movie_rv)).perform(
                RecyclerViewActions.actionOnItemAtPosition<MovieListHolder>(
                    14,
                    scrollTo()
                )
            )


            /** check for child content**/
            onView(
                RecyclerViewMatcher(R.id.movie_rv)
                    .atPositionOnView(14, R.id.tv_title))
                .check(matches(isDisplayed()))
            onView(
                RecyclerViewMatcher(R.id.movie_rv)
                    .atPositionOnView(14, R.id.image_poster_list_item))
                .check(matches(isDisplayed()))

            val textContent2 = getText(RecyclerViewMatcher(R.id.movie_rv)
                .atPositionOnView(14, R.id.tv_title))
            if(textContent2?.isEmpty() == true) { throw Throwable("content should not be emoty")}

        }
    }


    @Test fun B_fragment_shownUiComponent() {
        runBlocking {
            delay(300)
            onView(withId(R.id.movie_rv)).check(matches(isDisplayed()))
            onView(withId(R.id.swipe_refresh)).check(matches(isRefreshing()))
        }
    }

}

fun isRefreshing(): Matcher<View> {
    return object : BoundedMatcher<View, SwipeRefreshLayout>(
        SwipeRefreshLayout::class.java) {

        override fun matchesSafely(view: SwipeRefreshLayout): Boolean {
            return view.isRefreshing
        }

        override fun describeTo(description: org.hamcrest.Description?) {
            description?.appendText("is refreshing")
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