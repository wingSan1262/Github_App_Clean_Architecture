package vanrrtech.app.ajaib_app_sample.domain.UseCases.github




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
import vanrrtech.app.ajaib_app_sample.data.SQDb.github.UserListDao
import vanrrtech.app.ajaib_app_sample.data.remote_repository.RemoteApiRetrofitClient
import vanrrtech.app.ajaib_app_sample.domain.data_model.github.request.SearchUserRequest
import vanrrtech.app.ajaib_app_sample.domain.data_model.github.request.UserDetailRequest
import vanrrtech.app.ajaib_app_sample.domain.data_model.github.response.SearchResult
import vanrrtech.app.ajaib_app_sample.domain.data_model.github.response.UserDetails
import vanrrtech.app.ajaib_app_sample.domain.data_model.github.response.UserRepoDetails
import vanrrtech.app.ajaib_app_sample.features.github.UserItemViewHolder

@RunWith(MockitoJUnitRunner::class)

internal class SearchUserGithubUseCaseTest{
    lateinit var SUT: SearchUserGithubUseCase

    @MockK
    lateinit var mRepo: RemoteApiRetrofitClient

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    val dispatcher = TestCoroutineDispatcher()

    val data = SearchResult(
        ArrayList<GithubUserItemResponse>().also {
            it.addAll(
                listOf(
                    GithubUserItemResponse(1,"","","",""),
                    GithubUserItemResponse(1,"","","",""),
                )
            )
        }, 2
    )

    val request = SearchUserRequest("")


    @Before
    @Throws(Exception::class)
    fun setup() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(dispatcher)
        SUT = SearchUserGithubUseCase(mRepo)
    }


    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun setupCall_success() {
        var isNotified = false
        var result  : ResourceState<SearchResult>? = null
        SUT?.currentData?.observeForever {
            it.contentIfNotHandled?.let {
                isNotified = true
                result = it
            }
        }
        runTest {
            coEvery { mRepo.searchUserResult(request) }.returns(data)
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
            coEvery { mRepo.searchUserResult(request) }.returns(null)
            SUT?.setup(request)
            while (!isNotified){ delay(250) }
            coVerify(exactly = 1){ mRepo.searchUserResult(request)}
            advanceUntilIdle()
        }
    }

    @Test
    fun setupCall_passedParamCorrect() {
        var isNotified = false
        var slot = slot<SearchUserRequest>()
        SUT?.currentData?.observeForever { isNotified = true }
        runTest {
            coEvery { mRepo.searchUserResult(capture(slot)) }.returns(data)
            SUT?.setup(request)
            while (!isNotified){ delay(250) }
            assertThat(request, `is`(slot.captured))
            advanceUntilIdle()
        }
    }

    @Test
    fun setupCall_apiFail() {

        var isNotified = false
        var result  : ResourceState<SearchResult>? = null
        SUT?.currentData?.observeForever {
            it.contentIfNotHandled?.let {
                isNotified = true
                result = it
            }
        }
        runTest {
            coEvery { mRepo.searchUserResult(request) }.returns(null)
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