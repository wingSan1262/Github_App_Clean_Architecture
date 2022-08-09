package vanrrtech.app.ajaib_app_sample.features.github



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
import vanrrtech.app.ajaib_app_sample.domain.UseCases.github.GetGithubUserDetails
import vanrrtech.app.ajaib_app_sample.domain.UseCases.github.GetGithubUserRepoContentUseCase
import vanrrtech.app.ajaib_app_sample.domain.data_model.github.request.UserDetailRequest
import vanrrtech.app.ajaib_app_sample.domain.data_model.github.response.UserDetails
import vanrrtech.app.ajaib_app_sample.domain.data_model.github.response.UserRepoDetails
import vanrrtech.app.ajaib_app_sample.features.github.UserItemViewHolder

@RunWith(MockitoJUnitRunner::class)

internal class UserDetailFragmentVmTest{
    lateinit var SUT: UserDetailFragmentVm

    var getGithubUserDetails :
            GetGithubUserDetails? = null
    var getGithubUserRepoContent :
            GetGithubUserRepoContentUseCase? = null

    @MockK
    lateinit var mRepo: RemoteApiRetrofitClient

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    val dispatcher = TestCoroutineDispatcher()

    @Before
    @Throws(Exception::class)
    fun setup() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(dispatcher)
        getGithubUserDetails = GetGithubUserDetails(mRepo)
        getGithubUserRepoContent = GetGithubUserRepoContentUseCase(mRepo)
        SUT = UserDetailFragmentVm(
            getGithubUserDetails!!,
            getGithubUserRepoContent!!
        )
    }

    @Test
    fun callApi_fetchUserData(){
        val req = UserDetailRequest("")
        val res = UserDetails("","","","","","","","")
        var isNotified = false
        var result  : ResourceState<UserDetails>? = null
        SUT.userDetailLiveData.observeForever{
            it.contentIfNotHandled?.let {
                isNotified = true; result = it
            }
        }

        runTest {
            coEvery { mRepo.getUserDetail(req)}.returns(res)
            SUT?.fetchUserData(req)
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
    fun callApi_fetchUserData_invokedCorrectly(){
        val req = UserDetailRequest("")
        var slot = slot<UserDetailRequest>()
        val res = UserDetails("","","","","","","","")
        var isNotified = false
        var result  : ResourceState<UserDetails>? = null
        SUT.userDetailLiveData.observeForever{
            it.contentIfNotHandled?.let {
                isNotified = true; result = it
            }
        }

        runTest {
            coEvery { mRepo.getUserDetail(capture(slot))}.returns(res)
            SUT?.fetchUserData(req)
            while (!isNotified){
                delay(250)
            }
            advanceUntilIdle()
            coVerify(exactly = 1){mRepo.getUserDetail(req)}
            assertThat(req, `is`(slot.captured))
        }
    }
    @Test
    fun callApi_fetchUserData_fail(){
        val req = UserDetailRequest("")
        val res = UserDetails("","","","","","","","")
        var isNotified = false
        var result  : ResourceState<UserDetails>? = null
        SUT.userDetailLiveData.observeForever{
            it.contentIfNotHandled?.let {
                isNotified = true; result = it
            }
        }

        runTest {
            coEvery { mRepo.getUserDetail(req)}.returns(null)
            SUT?.fetchUserData(req)
            while (!isNotified){
                delay(250)
            }
            advanceUntilIdle()
        }
        if(!(result is ResourceState.Failure)){
            throw Throwable("response wrong")
        }
    }

    @Test
    fun callApi_fetchRepoList(){
        val req = UserDetailRequest("")
        val res = listOf(
            UserRepoDetails("",
                GithubUserItemResponse(1,"","","",""),
                "",1,""),
            UserRepoDetails("",
                GithubUserItemResponse(1,"","","",""),
                "",1,""),
        )
        var isNotified = false
        var result  : ResourceState<List<UserRepoDetails>>? = null
        SUT.userRepoLiveData.observeForever{
            it.contentIfNotHandled?.let {
                isNotified = true; result = it
            }
        }

        runTest {
            coEvery { mRepo.getUserRepo(req)}.returns(res)
            SUT?.fetchRepoList(req)
            while (!isNotified){
                delay(250)
            }
            advanceUntilIdle()
        }
        if((result is ResourceState.Success)){
            assertThat((result as ResourceState.Success<List<UserRepoDetails>>).body
                , `is`(res))
        }

    }
    @Test
    fun callApi_fetchRepoList_invokedCorrectly(){

        val slot = slot<UserDetailRequest>()
        val req = UserDetailRequest("")
        val res = listOf(
            UserRepoDetails("",
                GithubUserItemResponse(1,"","","",""),
                "",1,""),
            UserRepoDetails("",
                GithubUserItemResponse(1,"","","",""),
                "",1,""),
        )
        var isNotified = false
        var result  : ResourceState<List<UserRepoDetails>>? = null
        SUT.userRepoLiveData.observeForever{
            it.contentIfNotHandled?.let {
                isNotified = true; result = it
            }
        }

        runTest {
            coEvery { mRepo.getUserRepo(capture(slot))}.returns(res)
            SUT?.fetchRepoList(req)
            while (!isNotified){
                delay(250)
            }
            coVerify(exactly = 1){mRepo.getUserRepo(req)}
            assertThat(req, `is`(slot.captured))
            if((result is ResourceState.Success)){
                assertThat((result as ResourceState.Success<List<UserRepoDetails>>).body
                    , `is`(res))
            }
            advanceUntilIdle()
        }

    }
    @Test
    fun callApi_fetchRepoList_fail(){
        val req = UserDetailRequest("")
        val res = listOf(
            UserRepoDetails("",
                GithubUserItemResponse(1,"","","",""),
                "",1,""),
            UserRepoDetails("",
                GithubUserItemResponse(1,"","","",""),
                "",1,""),
        )
        var isNotified = false
        var result  : ResourceState<List<UserRepoDetails>>? = null
        SUT.userRepoLiveData.observeForever{
            it.contentIfNotHandled?.let {
                isNotified = true; result = it
            }
        }

        runTest {
            coEvery { mRepo.getUserRepo(req)}.returns(null)
            SUT?.fetchRepoList(req)
            while (!isNotified){
                delay(250)
            }
            advanceUntilIdle()
        }
        if((result is ResourceState.Success)){
            throw Throwable("fail, it was succes")
        }
    }



    @Test
    fun tryThis(){}

}