package vanrrtech.app.ajaib_app_sample.domain.UseCases.github


import android.view.View
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
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
import vanrrtech.app.ajaib_app_sample.domain.data_model.github.request.UserDetailRequest
import vanrrtech.app.ajaib_app_sample.domain.data_model.github.response.UserDetails
import vanrrtech.app.ajaib_app_sample.domain.data_model.github.response.UserRepoDetails
import vanrrtech.app.ajaib_app_sample.features.github.UserItemViewHolder

@RunWith(MockitoJUnitRunner::class)

internal class GetOfflineGithubUserListUseCaseTest{
    lateinit var SUT: GetOfflineGithubUserListUseCase

    @MockK
    lateinit var mRepo: UserListDao

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    val dispatcher = TestCoroutineDispatcher()

    val data = ArrayList<GithubUserItemResponse>().also {
        it.addAll(
            listOf(
                GithubUserItemResponse(1,"","","",""),
                GithubUserItemResponse(1,"","","",""),
            )
        )
    }


    @Before
    @Throws(Exception::class)
    fun setup() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(dispatcher)
        SUT = GetOfflineGithubUserListUseCase(mRepo)
    }


    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun setupCall_success() {
        var isNotified = false
        var result  : ResourceState<List<GithubUserItemResponse>>? = null
        SUT?.currentData?.observeForever {
            it.contentIfNotHandled?.let {
                isNotified = true
                result = it
            }
        }
        runTest {
            coEvery { mRepo.loadAllUser() }.returns(data.toList())
            SUT?.setup(null)
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
            coEvery { mRepo.loadAllUser() }.returns(null)
            SUT?.setup(null)
            while (!isNotified){ delay(250) }
            coVerify(exactly = 1){ mRepo.loadAllUser()}
            advanceUntilIdle()
        }
    }

    @Test
    fun setupCall_apiFail() {
        var isNotified = false
        var result  : ResourceState<List<GithubUserItemResponse>>? = null
        SUT?.currentData?.observeForever {
            it.contentIfNotHandled?.let {
                isNotified = true; result = it
            }
        }
        runTest {
            coEvery { mRepo.loadAllUser() }.returns(null)
            SUT?.setup(null)
            while (!isNotified){
                delay(250)
            }
            advanceUntilIdle()
        }

        if(!(result is ResourceState.Failure)){
            throw Throwable(result.toString())
        }
    }
}