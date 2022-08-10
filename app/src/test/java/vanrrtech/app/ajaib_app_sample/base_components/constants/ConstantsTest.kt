package vanrrtech.app.ajaib_app_sample.base_components.constants


import android.view.View
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import vanrrtech.app.ajaib_app_sample.domain.data_model.github.response.GithubUserItemResponse

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.CoreMatchers.`is`
import org.junit.After
import org.junit.Rule
import vanrrtech.app.ajaib_app_sample.base_components.entities.ResourceState
import vanrrtech.app.ajaib_app_sample.data.remote_repository.RemoteApiRetrofitClient
import vanrrtech.app.ajaib_app_sample.domain.UseCases.github.GetGithubUserListUseCase
import vanrrtech.app.ajaib_app_sample.domain.data_model.github.request.UserDetailRequest
import vanrrtech.app.ajaib_app_sample.domain.data_model.github.response.UserDetails
import vanrrtech.app.ajaib_app_sample.features.github.UserItemViewHolder

@RunWith(MockitoJUnitRunner::class)
internal class ConstantsTest{
    @Before
    @Throws(Exception::class)
    fun setup() {
        MockKAnnotations.init(this)
    }


    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun setupCall_success() {
        assertThat(Constants.USER_CLICKED_MODEL , `is`("clicked_user_model"))
    }
}