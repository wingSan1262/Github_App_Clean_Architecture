package vanrrtech.app.ajaib_app_sample.base_components.extensions

import android.view.View
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.Navigation
import io.mockk.MockKAnnotations
import io.mockk.every
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.CoreMatchers.`is`
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.mockkObject
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Rule
import org.junit.Test
import vanrrtech.app.ajaib_app_sample.data.remote_repository.RemoteApiRetrofitClient
import vanrrtech.app.ajaib_app_sample.domain.UseCases.github.GetGithubUserListUseCase
import vanrrtech.app.ajaib_app_sample.domain.data_model.github.request.UserDetailRequest
import vanrrtech.app.ajaib_app_sample.domain.data_model.github.response.GithubUserItemResponse

internal class ActivityFragmentExtensionKtTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    val dispatcher = TestCoroutineDispatcher()

    @Test
    fun findNullableNavController_fail() {
        @MockK
        lateinit var view: View
        @InjectMockKs
        var fragment = Fragment()
        MockKAnnotations.init(this)
        if (fragment.findNullableNavController() != null)
            throw  Throwable("fail , it not null")
    }

    @Test
    fun navigateSafe() {
    }

    @Test
    fun setVisibility() {
    }

    @Test
    fun setVisibilityInvisible() {
    }

    @Test
    fun loadImage() {
    }

    @Test
    fun textChanges() {
    }

    @Test
    fun observeEvent() {
    }
}