package vanrrtech.app.ajaib_app_sample.domain.UseCases.github


import android.view.View
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.slot
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
import vanrrtech.app.ajaib_app_sample.domain.data_model.github.request.UserDetailRequest
import vanrrtech.app.ajaib_app_sample.domain.data_model.github.response.UserDetails
import vanrrtech.app.ajaib_app_sample.domain.data_model.github.response.UserRepoDetails
import vanrrtech.app.ajaib_app_sample.features.github.UserItemViewHolder

@RunWith(MockitoJUnitRunner::class)

internal class GetGithubUserRepoContentUseCaseTest{
    lateinit var SUT: GetGithubUserRepoContentUseCase

    @MockK
    lateinit var mRepo: RemoteApiRetrofitClient

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    val dispatcher = TestCoroutineDispatcher()

    val data = ArrayList<UserRepoDetails>().also {
        it.addAll(
            listOf(
                UserRepoDetails("",
                    GithubUserItemResponse(1,"","","",""),
                    "",1,""),
                UserRepoDetails("",
                    GithubUserItemResponse(1,"","","",""),
                    "",1,""),
            )
        )
    }

    val request = UserDetailRequest(
        ""
    )


    @Before
    @Throws(Exception::class)
    fun setup() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(dispatcher)
        SUT = GetGithubUserRepoContentUseCase(mRepo)
    }


    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun setupCall_success() {
        var isNotified = false
        var result  : ResourceState<List<UserRepoDetails>>? = null
        SUT?.currentData?.observeForever {
            it.contentIfNotHandled?.let {
                isNotified = true; result = it
            }
        }
        runTest {
            coEvery { mRepo.getUserRepo(request) }.returns(data.toList())
            SUT?.setup(request)
            while (!isNotified){
                delay(250)
            }
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
            coEvery { mRepo.getUserRepo(request) }.returns(null)
            SUT?.setup(request)
            while (!isNotified){ delay(250) }
            coVerify(exactly = 1){ mRepo.getUserRepo(request)}
            advanceUntilIdle()
        }
    }

    @Test
    fun setupCall_ParamPassedCorrectly() {
        var isNotified = false
        var slot = slot<UserDetailRequest>()
        SUT?.currentData?.observeForever { isNotified = true }
        runTest {
            coEvery { mRepo.getUserRepo(capture(slot)) }.returns(data)
            SUT?.setup(request)
            while (!isNotified){ delay(250) }
            assertThat(request, `is`(slot.captured))
        }
    }

    @Test
    fun setupCall_apiFail() {
        var isNotified = false
        var result  : ResourceState<List<UserRepoDetails>>? = null
        SUT?.currentData?.observeForever {
            it.contentIfNotHandled?.let {
                isNotified = true; result = it
            }
        }
        runTest {
            coEvery { mRepo.getUserRepo(request) }.returns(null)
            SUT?.setup(request)
            while (!isNotified){
                delay(250)
            }
            advanceUntilIdle()
        }
        if(!(result is ResourceState.Failure)){
            throw Throwable("response wrong")
        }
    }
}