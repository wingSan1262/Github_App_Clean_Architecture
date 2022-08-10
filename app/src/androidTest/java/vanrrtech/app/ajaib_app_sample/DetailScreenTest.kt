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
class DetailScreenTest {
    val intent = Intent(ApplicationProvider.getApplicationContext(), TopActivity::class.java).putExtra("title", "Testing rules!")
    @Rule @JvmField val activityScenario = ActivityScenarioRule<TopActivity>(intent)

    @Before
    fun setup(){
        /** setting screen for detail**/
        runBlocking {
            delay(3000)
            onView(withId(R.id.user_rv)).perform(
                RecyclerViewActions.actionOnItemAtPosition<UserItemViewHolder>(
                    3,
                    click()
                )
            )
        }
    }

    @Test fun fragmentDetail_Visibility() {
        runBlocking {
            delay(3000)
            onView(withId(R.id.userImage)).check(
                matches(isDisplayed()))
            onView(withId(R.id.user_account_id)).check(
                matches(isDisplayed()))
            onView(withId(R.id.user_name)).check(
                matches(isDisplayed()))
            onView(withId(R.id.user_desc_tv)).check(
                matches(not(isDisplayed())))
            onView(withId(R.id.follower_tv)).check(
                matches(isDisplayed()))
            onView(withId(R.id.location_tv_tv)).check(
                matches(isDisplayed()))
            onView(withId(R.id.following_tv)).check(
                matches(isDisplayed()))
            onView(withId(R.id.emailTvDetail)).check(
                matches(isDisplayed()))
        }
    }

    @Test fun fragmentDetail_checkDetailScreen() {
        runBlocking {
            delay(3000)
            onView(withId(R.id.user_account_id)).check(
                matches(withText("@wycats")))
            onView(withId(R.id.user_name)).check(
                matches(withText("Yehuda Katz")))
            onView(withId(R.id.user_desc_tv)).check(
                matches(not(isDisplayed())))
            onView(withId(R.id.follower_tv)).check(
                matches(withText("10001 Followers")))
            onView(withId(R.id.location_tv_tv)).check(
                matches(withText("Portland, OR")))
            onView(withId(R.id.following_tv)).check(
                matches(withText("11 Following")))
            onView(withId(R.id.emailTvDetail)).check(
                matches(withText("Not Available")))
        }
    }

    @Test fun fragmentDetail_scrollingBackAndForth_componentVisibility() {
        runBlocking {
            delay(3000)
            for(i in 0..25){
                onView(withId(R.id.user_repo_rv)).perform(
                    // scrollTo will fail the test if no item matches.
                    RecyclerViewActions.actionOnItemAtPosition<UserItemViewHolder>(
                        i,
                        scrollTo()
                    )
                )
                delay(500)
                RecyclerViewMatcher(R.id.user_repo_rv)
                    .atPositionOnView(i, R.id.name_tv).matches(isDisplayed())
                RecyclerViewMatcher(R.id.user_repo_rv)
                    .atPositionOnView(i, R.id.repo_name).matches(isDisplayed())
                RecyclerViewMatcher(R.id.user_repo_rv)
                    .atPositionOnView(i, R.id.repo_desc).matches(isDisplayed())
                RecyclerViewMatcher(R.id.user_repo_rv)
                    .atPositionOnView(i, R.id.ic_star).matches(isDisplayed())
                RecyclerViewMatcher(R.id.user_repo_rv)
                    .atPositionOnView(i, R.id.repo_star).matches(isDisplayed())
                RecyclerViewMatcher(R.id.user_repo_rv)
                    .atPositionOnView(i, R.id.repo_last_update).matches(isDisplayed())
            }

            for(i in 15 downTo 0){

                onView(withId(R.id.user_repo_rv)).perform(
                    // scrollTo will fail the test if no item matches.
                    RecyclerViewActions.actionOnItemAtPosition<UserItemViewHolder>(
                        i,
                        scrollTo()
                    )
                )
                delay(500)
                RecyclerViewMatcher(R.id.user_repo_rv)
                    .atPositionOnView(i, R.id.name_tv).matches(isDisplayed())
                RecyclerViewMatcher(R.id.user_repo_rv)
                    .atPositionOnView(i, R.id.repo_name).matches(isDisplayed())
                RecyclerViewMatcher(R.id.user_repo_rv)
                    .atPositionOnView(i, R.id.repo_desc).matches(isDisplayed())
                RecyclerViewMatcher(R.id.user_repo_rv)
                    .atPositionOnView(i, R.id.ic_star).matches(isDisplayed())
                RecyclerViewMatcher(R.id.user_repo_rv)
                    .atPositionOnView(i, R.id.repo_star).matches(isDisplayed())
                RecyclerViewMatcher(R.id.user_repo_rv)
                    .atPositionOnView(i, R.id.repo_last_update).matches(isDisplayed())

            }
        }
    }

}
