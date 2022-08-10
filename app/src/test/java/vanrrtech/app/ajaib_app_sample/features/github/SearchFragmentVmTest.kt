package vanrrtech.app.ajaib_app_sample.features.github


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
import vanrrtech.app.ajaib_app_sample.domain.UseCases.github.*
import vanrrtech.app.ajaib_app_sample.domain.data_model.github.request.SearchUserRequest
import vanrrtech.app.ajaib_app_sample.domain.data_model.github.request.UserDetailRequest
import vanrrtech.app.ajaib_app_sample.domain.data_model.github.response.SearchResult
import vanrrtech.app.ajaib_app_sample.domain.data_model.github.response.UserDetails
import vanrrtech.app.ajaib_app_sample.domain.data_model.github.response.UserRepoDetails
import vanrrtech.app.ajaib_app_sample.features.github.UserItemViewHolder

@RunWith(MockitoJUnitRunner::class)

internal class SearchFragmentVmTest{
    lateinit var SUT: SearchFragmentVm

    var getGithubUserListUseCase : GetGithubUserListUseCase? = null
    var getOfflineGithubUserListUseCase: GetOfflineGithubUserListUseCase? = null
    var updateGithubUserListUseCase: UpdateOfflineGithubUserListUseCase? = null
    var searchUserGithubUseCase: SearchUserGithubUseCase? = null
    @MockK
    lateinit var mRepo: RemoteApiRetrofitClient

    @MockK
    lateinit var mDb: UserListDao

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    val dispatcher = TestCoroutineDispatcher()

    @Before
    @Throws(Exception::class)
    fun setup() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(dispatcher)
        getGithubUserListUseCase = GetGithubUserListUseCase(mRepo)
        getOfflineGithubUserListUseCase = GetOfflineGithubUserListUseCase(mDb)
        updateGithubUserListUseCase = UpdateOfflineGithubUserListUseCase(mDb)
        searchUserGithubUseCase = SearchUserGithubUseCase(mRepo)
        SUT = SearchFragmentVm(
            getGithubUserListUseCase!!,
            getOfflineGithubUserListUseCase!!,
            updateGithubUserListUseCase!!,
            searchUserGithubUseCase!!
        )
    }

    @Test
    fun callApi_fetchUserList_invokedCorrectlyAndSuccess(){
        val listSlot = mutableListOf<GithubUserItemResponse>()
        val res = listOf(
            GithubUserItemResponse(1,"","","",""),
            GithubUserItemResponse(1,"","","",""),
        )
        var isNotified = false
        var result  : ResourceState<List<GithubUserItemResponse>>? = null
        SUT.githubUserLiveData.observeForever{
            it.contentIfNotHandled?.let {
                isNotified = true; result = it
            }
        }
        runTest {
            coEvery { mRepo.getUserList()}.returns(res)
            coEvery { mDb.insertUsers(capture(listSlot)) }.returns(Unit)
            coEvery { mDb.nukeTable()}.returns(Unit)
            SUT?.fetchUserList()
            while (!isNotified){
                delay(250)
            }
            coVerify(exactly = 1){mRepo.getUserList()}
            coVerify(exactly = 1) { mDb.nukeTable() }
            listSlot.forEachIndexed() { index, it ->
                assertThat(res.get(index), `is`(it))
            }
            if((result is ResourceState.Success)){
                assertThat((result as ResourceState.Success<List<UserRepoDetails>>).body
                    , `is`(res))
            }
            advanceUntilIdle()
        }
    }
    @Test
    fun callApi_fetchUserList_fail(){
        var isNotified = false
        var result  : ResourceState<List<GithubUserItemResponse>>? = null
        SUT.githubUserLiveData.observeForever{
            it.contentIfNotHandled?.let {
                isNotified = true; result = it
            }
        }

        runTest {
            coEvery { mRepo.getUserList()}.returns(null)
            SUT?.fetchUserList()
            while (!isNotified){
                delay(250)
            }
            coVerify(exactly = 1){mRepo.getUserList()}
            if((result is ResourceState.Success)){
                throw Throwable("fail, it is success")
            }
            advanceUntilIdle()
        }
    }

    @Test
    fun callApi_fetchUserListOffline_invokedCorrectlyAndSuccess(){
        val res = listOf(
            GithubUserItemResponse(1,"","","",""),
            GithubUserItemResponse(1,"","","",""),
        )
        var isNotified = false
        var result  : ResourceState<List<GithubUserItemResponse>>? = null
        SUT.offlineGithubUserLiveData.observeForever{
            it.contentIfNotHandled?.let {
                isNotified = true; result = it
            }
        }

        runTest {
            coEvery { mDb.loadAllUser()}.returns(res)
            SUT?.fetchOfflineUserList()
            while (!isNotified){
                delay(250)
            }
            coVerify(exactly = 1){mDb.loadAllUser()}
            if((result is ResourceState.Success)){
                assertThat((result as ResourceState.Success<List<UserRepoDetails>>).body
                    , `is`(res))
            }
            advanceUntilIdle()
        }
    }
    @Test
    fun callApi_fetchUserListOffline_fail(){
        var isNotified = false
        var result  : ResourceState<List<GithubUserItemResponse>>? = null
        SUT.offlineGithubUserLiveData.observeForever{
            it.contentIfNotHandled?.let {
                isNotified = true; result = it
            } }
        runTest {
            coEvery { mDb.loadAllUser()}.returns(null)
            SUT?.fetchOfflineUserList()
            while (!isNotified){
                delay(250) }
            coVerify(exactly = 1){mDb.loadAllUser()}
            if(!(result is ResourceState.Failure)){
                throw Throwable("fail, supposed")
            }
            advanceUntilIdle()
        }
    }

    @Test
    fun callApi_updateUserOfflineList_invokedCorrectlyAndSuccess(){
        val listSlot = mutableListOf<GithubUserItemResponse>()
        val req = listOf(
            GithubUserItemResponse(1,"","","",""),
            GithubUserItemResponse(1,"","","",""),
        )
        var isNotified = false
        var result  : ResourceState<Boolean>? = null
        SUT.updateOfflineUserLiveData.observeForever{
            it.contentIfNotHandled?.let {
                isNotified = true; result = it
            }
        }

        runTest {
            coEvery { mDb.nukeTable()}.returns(Unit)
            coEvery { mDb.insertUsers(capture(listSlot))}.returns(Unit)
            SUT?.updateGithubUserList(req)
            while (!isNotified){
                delay(250)
            }
            coVerify(exactly = 2){mDb.insertUsers(any())}
            listSlot.forEachIndexed() { index, it ->
                assertThat(req.get(index), `is`(it))
            }
            if((result is ResourceState.Success)){
                assertThat((result as ResourceState.Success<List<UserRepoDetails>>).body
                    , `is`(true))
            }
            advanceUntilIdle()
        }
    }
    @Test
    fun callApi_updateRepoList_fail(){
        val listSlot = mutableListOf<GithubUserItemResponse>()
        val req = listOf(
            GithubUserItemResponse(1,"","","",""),
            GithubUserItemResponse(1,"","","",""),
        )
        var isNotified = false
        var result  : ResourceState<Boolean>? = null
        SUT.updateOfflineUserLiveData.observeForever{
            it.contentIfNotHandled?.let {
                isNotified = true; result = it
            }
        }

        runTest {
            coEvery { mDb.nukeTable()}.returns(Unit)
            coEvery { mDb.insertUsers(capture(listSlot))}.throws(Throwable("something"))
            SUT?.updateGithubUserList(req)
            while (!isNotified){
                delay(250)
            }
            listSlot.forEachIndexed() { index, it ->
                assertThat(req.get(index), `is`(it))
            }
            if((result is ResourceState.Success)){
                assertThat(((result as ResourceState.Success<Boolean>).body)
                    , `is`(false))
                return@runTest
            }
            advanceUntilIdle()
        }
    }

    @Test
    fun callApi_searchUserList_invokedCorrectlyAndSuccess(){
        val slot = slot<SearchUserRequest>()
        val res = SearchResult(listOf(
            GithubUserItemResponse(1,"","","",""),
            GithubUserItemResponse(1,"","","",""),
        ), 2)
        val req = SearchUserRequest("kosong")
        var isNotified = false
        var result  : ResourceState<SearchResult>? = null
        SUT.searchResultLiveData.observeForever{
            it.contentIfNotHandled?.let {
                isNotified = true; result = it
            }
        }
        runTest {
            coEvery { mRepo.searchUserResult(capture(slot))}.returns(res)
            SUT?.fetchSearchResult(req)
            while (!isNotified){
                delay(250)
            }
            coVerify(exactly = 1){mRepo.searchUserResult(req)}
            assertThat(req, `is`(slot.captured))
            if((result is ResourceState.Success)){
                assertThat((result as ResourceState.Success<List<UserRepoDetails>>).body
                    , `is`(res))
            }
            advanceUntilIdle()
        }
    }
    @Test
    fun callApi_searchUserList_fail(){
        val slot = slot<SearchUserRequest>()
        val res = SearchResult(listOf(
            GithubUserItemResponse(1,"","","",""),
            GithubUserItemResponse(1,"","","",""),
        ), 2)
        val req = SearchUserRequest("kosong")
        var isNotified = false
        var result  : ResourceState<SearchResult>? = null
        SUT.searchResultLiveData.observeForever{
            it.contentIfNotHandled?.let {
                isNotified = true; result = it
            }
        }
        runTest {
            coEvery { mRepo.searchUserResult(capture(slot))}.returns(null)
            SUT?.fetchSearchResult(req)
            while (!isNotified){
                delay(250)
            }
            coVerify(exactly = 1){mRepo.searchUserResult(req)}
            assertThat(req, `is`(slot.captured))
            if(!(result is ResourceState.Failure)){
                throw Throwable("fail, not failure here")
            }
            advanceUntilIdle()
        }
    }
}