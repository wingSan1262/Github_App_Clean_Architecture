package vanrrtech.app.ajaib_app_sample.domain.UseCases.github


import android.view.View
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.SpyK
import io.mockk.slot
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.times
import org.mockito.junit.MockitoJUnitRunner
import vanrrtech.app.ajaib_app_sample.databinding.UserRepoItemBinding
import vanrrtech.app.ajaib_app_sample.domain.data_model.github.response.GithubUserItemResponse
import vanrrtech.app.ajaib_app_sample.domain.data_model.github.response.UserRepoDetails

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.CoreMatchers.`is`
import org.junit.After
import org.junit.Rule
import org.junit.rules.TestRule
import org.mockito.stubbing.Answer
import retrofit2.Retrofit
import vanrrtech.app.ajaib_app_sample.base_components.UtilServices.LoginHandler.RandomHandler
import vanrrtech.app.ajaib_app_sample.base_components.base_classes.BaseUseCaseTest
import vanrrtech.app.ajaib_app_sample.base_components.entities.ResourceState
import vanrrtech.app.ajaib_app_sample.data.remote_repository.RemoteApiRetrofitClient
import vanrrtech.app.ajaib_app_sample.databinding.SearchUserItemBinding
import vanrrtech.app.ajaib_app_sample.domain.data_model.github.request.UserDetailRequest
import vanrrtech.app.ajaib_app_sample.domain.data_model.github.response.UserDetails
import vanrrtech.app.ajaib_app_sample.features.github.UserItemViewHolder

@RunWith(MockitoJUnitRunner::class)
internal class GetGithubUserDetailsTest{
    lateinit var SUT: GetGithubUserDetails

    @MockK
    lateinit var mRepo: RemoteApiRetrofitClient

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    val dispatcher = TestCoroutineDispatcher()

    val data = UserDetails(
        "","","","","","","","")

    val request = UserDetailRequest(
        ""
    )


    @Before
    @Throws(Exception::class)
    fun setup() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(dispatcher)
        SUT = GetGithubUserDetails(mRepo)
    }


    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun setupCall_success() {
        var isNotified = false
        var result  : ResourceState<UserDetails>? = null
        SUT?.currentData?.observeForever {
            it.contentIfNotHandled?.let {
                isNotified = true;result = it
            }
        }
        runTest {
            coEvery { mRepo.getUserDetail(request) }.returns(data)
            SUT?.setup(request)
            while(!isNotified){delay(250)}
            advanceUntilIdle()
        }
        if(!(result is ResourceState.Success)){
            throw Throwable("response wrong")
        }
    }

    @Test
    fun setupCall_InvokedCorrectly() {
        var isNotified = false
        SUT?.currentData?.observeForever { isNotified = true }
        runTest {
            coEvery { mRepo.getUserDetail(request) }.returns(null)
            SUT?.setup(request)
            while (!isNotified){ delay(250) }
            coVerify(exactly = 1){ mRepo.getUserDetail(request)}
            advanceUntilIdle()
        }
    }

    @Test
    fun setupCall_ParamPassedCorrectly() {
        var isNotified = false
        var slot = slot<UserDetailRequest>()
        SUT?.currentData?.observeForever { isNotified = true }
        runTest {
            coEvery { mRepo.getUserDetail(capture(slot)) }.returns(null)
            SUT?.setup(request)
            while (!isNotified){ delay(250) }
            assertThat(request, `is`(slot.captured))
        }
    }

    @Test
    fun setupCall_apiFail() {
        var isNotified = false
        var result  : ResourceState<UserDetails>? = null
        SUT?.currentData?.observeForever {
            it.contentIfNotHandled?.let {
                isNotified = true; result = it
            }
        }
        runTest {
            coEvery { mRepo.getUserDetail(request) }.returns(null)
            SUT?.setup(request)
            while(!isNotified){delay(250)}
        }
        if(!(result is ResourceState.Failure)){
            throw Throwable("response wrong")
        }
    }


}