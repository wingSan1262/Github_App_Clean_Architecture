package vanrrtech.app.ajaib_app_sample.data.remote_repository

import android.view.View
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.mockk.*
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.CoreMatchers.`is`
import io.mockk.impl.annotations.SpyK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import vanrrtech.app.ajaib_app_sample.databinding.UserRepoItemBinding
import vanrrtech.app.ajaib_app_sample.domain.data_model.github.response.GithubUserItemResponse
import vanrrtech.app.ajaib_app_sample.domain.data_model.github.response.UserRepoDetails

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.CoreMatchers.`is`
import org.junit.After
import org.junit.Rule
import org.junit.rules.TestRule
import org.mockito.Mockito.*
import org.mockito.stubbing.Answer
import retrofit2.Retrofit
import vanrrtech.app.ajaib_app_sample.base_components.UtilServices.LoginHandler.RandomHandler
import vanrrtech.app.ajaib_app_sample.base_components.base_classes.BaseUseCaseTest
import vanrrtech.app.ajaib_app_sample.base_components.entities.ResourceState
import vanrrtech.app.ajaib_app_sample.data.remote_repository.RemoteApiRetrofitClient
import vanrrtech.app.ajaib_app_sample.databinding.SearchUserItemBinding
import vanrrtech.app.ajaib_app_sample.domain.UseCases.github.GetGithubUserDetails
import vanrrtech.app.ajaib_app_sample.domain.data_model.github.request.SearchUserRequest
import vanrrtech.app.ajaib_app_sample.domain.data_model.github.request.UserDetailRequest
import vanrrtech.app.ajaib_app_sample.domain.data_model.github.response.UserDetails
import vanrrtech.app.ajaib_app_sample.features.github.UserItemViewHolder

@RunWith(MockitoJUnitRunner::class)
internal class RemoteApiRetrofitClientTest{
    lateinit var SUT: RemoteApiRetrofitClient
    @MockK lateinit var retrofit: Retrofit
    @MockK lateinit var retrofitBuilder: Retrofit.Builder
    @MockK lateinit var retrofitInterface: GithubApiInterface
    @get:Rule val instantExecutorRule = InstantTaskExecutorRule()
    val dispatcher = TestCoroutineDispatcher()

    @Before @Throws(Exception::class)
    fun setup() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(dispatcher)
        SUT = RemoteApiRetrofitClient(retrofit) }
    @After
    fun tearDown() = Dispatchers.resetMain()

    @Test fun setupCall_success() {
        crazyMethodMocking()
        if(SUT.getGithubRetrofit() == null)
            throw Throwable("fail, null")
    }

    @Test
    fun setupCall_getUserList() {
        crazyMethodMocking()
        coEvery{retrofit.newBuilder()
            .baseUrl(RemoteApiRetrofitClient.BASE_URL_GITHUB)
            .build()
            .create(GithubApiInterface::class.java)
            .getUsersList()}.returns(null)
        runTest {
            SUT.getUserList()
            coVerify (exactly = 1){ retrofit.newBuilder() }
        }
    }

    @Test
    fun setupCall_searchUserResult() {
        val data = SearchUserRequest("")
        val slot1 = slot<String>()
        val slot2 = slot<String>()
        crazyMethodMocking()
        coEvery{retrofit.newBuilder()
            .baseUrl(RemoteApiRetrofitClient.BASE_URL_GITHUB)
            .build()
            .create(GithubApiInterface::class.java)
            .searchUser(capture(slot1), capture(slot2))}.returns(null)
        runTest {
            SUT.searchUserResult(data)
            coVerify (exactly = 1){ retrofit.newBuilder() }
            assertThat(slot1.captured, `is`(data.query) )
            assertThat(slot2.captured, `is`(data.type) )
        }
    }

    @Test
    fun setupCall_getUserDetail() {
        val data = UserDetailRequest("kosong")
        val slot1 = slot<String>()
        crazyMethodMocking()
        coEvery{retrofit.newBuilder()
            .baseUrl(RemoteApiRetrofitClient.BASE_URL_GITHUB)
            .build()
            .create(GithubApiInterface::class.java)
            .getUserDetails(capture(slot1))}.returns(null)
        runTest {
            SUT.getUserDetail(data)
            coVerify (exactly = 1){ retrofit.newBuilder() }
            assertThat(slot1.captured, `is`(data.userName) )
        }
    }

    @Test
    fun setupCall_getUserRepo() {
        val data = UserDetailRequest("kosong")
        val slot1 = slot<String>()
        crazyMethodMocking()
        coEvery{retrofit.newBuilder()
            .baseUrl(RemoteApiRetrofitClient.BASE_URL_GITHUB)
            .build()
            .create(GithubApiInterface::class.java)
            .getUserRepos(capture(slot1))}.returns(null)
        runTest {
            SUT.getUserRepo(data)
            coVerify (exactly = 1){ retrofit.newBuilder() }
            assertThat(slot1.captured, `is`(data.userName) )
        }
    }


    fun crazyMethodMocking(){
        every{retrofit.newBuilder()}.returns(retrofitBuilder)
        every{retrofit.newBuilder()
            .baseUrl(RemoteApiRetrofitClient.BASE_URL_GITHUB)}.returns(retrofitBuilder)
        every{retrofit.newBuilder()
            .baseUrl(RemoteApiRetrofitClient.BASE_URL_GITHUB)
            .build()}.returns(retrofit)
        every{retrofit.newBuilder()
            .baseUrl(RemoteApiRetrofitClient.BASE_URL_GITHUB)
            .build()
            .create(GithubApiInterface::class.java)}.returns(retrofitInterface)
    }
}