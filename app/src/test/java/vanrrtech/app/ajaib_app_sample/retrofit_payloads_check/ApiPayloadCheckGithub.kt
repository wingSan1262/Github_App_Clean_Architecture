package vanrrtech.app.ajaib_app_sample.retrofit_payloads_check


import android.R
import android.content.res.Resources
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import vanrrtech.app.ajaib_app_sample.data.remote_repository.GithubApiInterface
import vanrrtech.app.ajaib_app_sample.data.remote_repository.RemoteApiRetrofitClient
import java.util.concurrent.TimeUnit

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.CoreMatchers.`is`


@RunWith(MockitoJUnitRunner::class)

class ApiPayloadCheckGithub {

    lateinit var SUT: GithubApiInterface
    private val mockWebServer = MockWebServer()
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    val dispatcher = TestCoroutineDispatcher()

    @Before
    @Throws(Exception::class)
    fun setup() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(dispatcher)

        val client = OkHttpClient.Builder()
            .connectTimeout(1, TimeUnit.SECONDS)
            .readTimeout(1, TimeUnit.SECONDS)
            .writeTimeout(1, TimeUnit.SECONDS)
            .build()

        SUT = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GithubApiInterface::class.java)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun testUserList_success(){
        mockWebServer.enqueue(
            MockResponse().also {
                it.setBody(Payload.USER_LIST)
                it.setResponseCode(200)
            }
        )
        runTest {
            val payloadObject = SUT.getUsersList()
            payloadObject?.run {
                assertThat(get(0).login, `is`("mojombo"))
                assertThat(get(0).id, `is`(1))
                assertThat(get(0).url, `is`("https://api.github.com/users/mojombo"))
                assertThat(get(0).avatarUrl, `is`("https://avatars.githubusercontent.com/u/1?v=4"))
            }
            advanceUntilIdle()
        }
    }

    @Test
    fun testUserList_withEmptyField(){
        mockWebServer.enqueue(
            MockResponse().also {
                it.setBody(Payload.USER_LIST_EMPzTY)
                it.setResponseCode(200)
            }
        )
        runTest {
            val payloadObject = SUT.getUsersList()
            payloadObject?.run {
                if(get(0).login.isNotEmpty()) throw  Throwable("not empty")
                if(get(0).id != -1) throw  Throwable("not -1")
            }
            advanceUntilIdle()
        }
    }

    @Test
    fun testSearchUser_success(){
        mockWebServer.enqueue(
            MockResponse().also {
                it.setBody(Payload.SEARCH_PAYLOAD)
                it.setResponseCode(200)
            }
        )
        runTest {
            val payloadObject = SUT.searchUser("wingSan1262", "Users")
            payloadObject?.run {
                assertThat(items.get(0)?.login, `is`("wingSan1262"))
                assertThat(items.get(0)?.id, `is`(72028903))
                assertThat(items.get(0)?.url, `is`("https://api.github.com/users/wingSan1262"))
                assertThat(items.get(0)?.avatarUrl, `is`("https://avatars.githubusercontent.com/u/72028903?v=4"))

                assertThat(total, `is`(1))
            }
            advanceUntilIdle()
        }
    }

    @Test
    fun testSearchUser_withEmptyField(){
        mockWebServer.enqueue(
            MockResponse().also {
                it.setBody(Payload.SEARCH_PAYLOAD_EMPTY_FIELD)
                it.setResponseCode(200)
            }
        )
        runTest {
            val payloadObject = SUT.searchUser("wingSan1262", "Users")
            payloadObject?.run {
                if(total != 0) throw Throwable("total suppose to be null")
                if(items.get(0)?.login?.isNotEmpty() == true) throw Throwable("total suppose to be null")
            }
            advanceUntilIdle()
        }
    }

    @Test
    fun testGetUserRepos_success(){
        mockWebServer.enqueue(
            MockResponse().also {
                it.setBody(Payload.USER_REPO_PAYLOAD)
                it.setResponseCode(200)
            }
        )
        runTest {
            val payloadObject = SUT.getUserRepos("errfree")
            payloadObject?.run {
                assertThat(get(0)?.name, `is`("test"))
                assertThat(get(0)?.watcher_count, `is`(1))
                if(get(0)?.description != (null)) throw  Throwable("fail it's null")
                assertThat(get(0)?.update_at, `is`("2021-08-14T00:51:37Z"))

                assertThat(get(0)?.owner?.login, `is`("errfree"))
                assertThat(get(0)?.owner?.id, `is`(44))
                assertThat(get(0)?.owner?.avatarUrl, `is`("https://avatars.githubusercontent.com/u/44?v=4"))
                assertThat(get(0)?.owner?.url, `is`("https://api.github.com/users/errfree"))
            }
            advanceUntilIdle()
        }
    }

    @Test
    fun testGetUserRepos_withEmptyField(){
        mockWebServer.enqueue(
            MockResponse().also {
                it.setBody(Payload.USER_REPO_PAYLOAD_EMPTY)
                it.setResponseCode(200)
            }
        )
        runTest {
            val payloadObject = SUT.getUserRepos("errfree")
            payloadObject?.run {
                if(this.get(0)?.owner?.login?.isNotEmpty() == true) throw Throwable("suppose to be emptu")
            }
            advanceUntilIdle()
        }
    }

    @Test
    fun testGetUserDetail_success(){
        mockWebServer.enqueue(
            MockResponse().also {
                it.setBody(Payload.USER_DETAIL_PAYLOAD)
                it.setResponseCode(200)
            }
        )
        runTest {
            val payloadObject = SUT.getUserDetails("wingSan1262")
            payloadObject?.run {
                assertThat(login, `is`("wingSan1262"))
                assertThat(this.followers, `is`("1"))
                assertThat(this.following, `is`("1"))
                assertThat(this.name, `is`("wingSan1262"))
                if(this.bio == null) throw Throwable("cannot be null for this payload")
                if(this.email != null) throw Throwable("fail email")
                if(this.avatarUrl != "https://avatars.githubusercontent.com/u/72028903?v=4") throw Throwable("fail avatar")
                if(this.location != "Indonesia") throw Throwable("fail location")
            }
            advanceUntilIdle()
        }
    }
}

class Payload{
    companion object{
        val USER_LIST = "[{\"login\":\"mojombo\",\"id\":1,\"node_id\":\"MDQ6VXNlcjE=\",\"avatar_url\":\"https://avatars.githubusercontent.com/u/1?v=4\",\"gravatar_id\":\"\",\"url\":\"https://api.github.com/users/mojombo\",\"html_url\":\"https://github.com/mojombo\",\"followers_url\":\"https://api.github.com/users/mojombo/followers\",\"following_url\":\"https://api.github.com/users/mojombo/following{/other_user}\",\"gists_url\":\"https://api.github.com/users/mojombo/gists{/gist_id}\",\"starred_url\":\"https://api.github.com/users/mojombo/starred{/owner}{/repo}\",\"subscriptions_url\":\"https://api.github.com/users/mojombo/subscriptions\",\"organizations_url\":\"https://api.github.com/users/mojombo/orgs\",\"repos_url\":\"https://api.github.com/users/mojombo/repos\",\"events_url\":\"https://api.github.com/users/mojombo/events{/privacy}\",\"received_events_url\":\"https://api.github.com/users/mojombo/received_events\",\"type\":\"User\",\"site_admin\":false}]"
        val USER_LIST_EMPzTY = "[{\"node_id\":\"MDQ6VXNlcjE=\",\"avatar_url\":\"https://avatars.githubusercontent.com/u/1?v=4\",\"gravatar_id\":\"\",\"url\":\"https://api.github.com/users/mojombo\",\"html_url\":\"https://github.com/mojombo\",\"followers_url\":\"https://api.github.com/users/mojombo/followers\",\"following_url\":\"https://api.github.com/users/mojombo/following{/other_user}\",\"gists_url\":\"https://api.github.com/users/mojombo/gists{/gist_id}\",\"starred_url\":\"https://api.github.com/users/mojombo/starred{/owner}{/repo}\",\"subscriptions_url\":\"https://api.github.com/users/mojombo/subscriptions\",\"organizations_url\":\"https://api.github.com/users/mojombo/orgs\",\"repos_url\":\"https://api.github.com/users/mojombo/repos\",\"events_url\":\"https://api.github.com/users/mojombo/events{/privacy}\",\"received_events_url\":\"https://api.github.com/users/mojombo/received_events\",\"type\":\"User\",\"site_admin\":false}]"
        val SEARCH_PAYLOAD = "{\"total_count\":1,\"incomplete_results\":false,\"items\":[{\"login\":\"wingSan1262\",\"id\":72028903,\"node_id\":\"MDQ6VXNlcjcyMDI4OTAz\",\"avatar_url\":\"https://avatars.githubusercontent.com/u/72028903?v=4\",\"gravatar_id\":\"\",\"url\":\"https://api.github.com/users/wingSan1262\",\"html_url\":\"https://github.com/wingSan1262\",\"followers_url\":\"https://api.github.com/users/wingSan1262/followers\",\"following_url\":\"https://api.github.com/users/wingSan1262/following{/other_user}\",\"gists_url\":\"https://api.github.com/users/wingSan1262/gists{/gist_id}\",\"starred_url\":\"https://api.github.com/users/wingSan1262/starred{/owner}{/repo}\",\"subscriptions_url\":\"https://api.github.com/users/wingSan1262/subscriptions\",\"organizations_url\":\"https://api.github.com/users/wingSan1262/orgs\",\"repos_url\":\"https://api.github.com/users/wingSan1262/repos\",\"events_url\":\"https://api.github.com/users/wingSan1262/events{/privacy}\",\"received_events_url\":\"https://api.github.com/users/wingSan1262/received_events\",\"type\":\"User\",\"site_admin\":false,\"score\":1.0}]}"
        val SEARCH_PAYLOAD_EMPTY_FIELD = "{\"incomplete_results\":false,\"items\":[{\"id\":72028903,\"node_id\":\"MDQ6VXNlcjcyMDI4OTAz\",\"avatar_url\":\"https://avatars.githubusercontent.com/u/72028903?v=4\",\"gravatar_id\":\"\",\"url\":\"https://api.github.com/users/wingSan1262\",\"html_url\":\"https://github.com/wingSan1262\",\"followers_url\":\"https://api.github.com/users/wingSan1262/followers\",\"following_url\":\"https://api.github.com/users/wingSan1262/following{/other_user}\",\"gists_url\":\"https://api.github.com/users/wingSan1262/gists{/gist_id}\",\"starred_url\":\"https://api.github.com/users/wingSan1262/starred{/owner}{/repo}\",\"subscriptions_url\":\"https://api.github.com/users/wingSan1262/subscriptions\",\"organizations_url\":\"https://api.github.com/users/wingSan1262/orgs\",\"repos_url\":\"https://api.github.com/users/wingSan1262/repos\",\"events_url\":\"https://api.github.com/users/wingSan1262/events{/privacy}\",\"received_events_url\":\"https://api.github.com/users/wingSan1262/received_events\",\"type\":\"User\",\"site_admin\":false,\"score\":1.0}]}"


        val USER_REPO_PAYLOAD = "[{\"id\":2277888,\"node_id\":\"MDEwOlJlcG9zaXRvcnkyMjc3ODg4\",\"name\":\"test\",\"full_name\":\"errfree/test\",\"private\":false,\"owner\":{\"login\":\"errfree\",\"id\":44,\"node_id\":\"MDEyOk9yZ2FuaXphdGlvbjQ0\",\"avatar_url\":\"https://avatars.githubusercontent.com/u/44?v=4\",\"gravatar_id\":\"\",\"url\":\"https://api.github.com/users/errfree\",\"html_url\":\"https://github.com/errfree\",\"followers_url\":\"https://api.github.com/users/errfree/followers\",\"following_url\":\"https://api.github.com/users/errfree/following{/other_user}\",\"gists_url\":\"https://api.github.com/users/errfree/gists{/gist_id}\",\"starred_url\":\"https://api.github.com/users/errfree/starred{/owner}{/repo}\",\"subscriptions_url\":\"https://api.github.com/users/errfree/subscriptions\",\"organizations_url\":\"https://api.github.com/users/errfree/orgs\",\"repos_url\":\"https://api.github.com/users/errfree/repos\",\"events_url\":\"https://api.github.com/users/errfree/events{/privacy}\",\"received_events_url\":\"https://api.github.com/users/errfree/received_events\",\"type\":\"Organization\",\"site_admin\":false},\"html_url\":\"https://github.com/errfree/test\",\"description\":null,\"fork\":false,\"url\":\"https://api.github.com/repos/errfree/test\",\"forks_url\":\"https://api.github.com/repos/errfree/test/forks\",\"keys_url\":\"https://api.github.com/repos/errfree/test/keys{/key_id}\",\"collaborators_url\":\"https://api.github.com/repos/errfree/test/collaborators{/collaborator}\",\"teams_url\":\"https://api.github.com/repos/errfree/test/teams\",\"hooks_url\":\"https://api.github.com/repos/errfree/test/hooks\",\"issue_events_url\":\"https://api.github.com/repos/errfree/test/issues/events{/number}\",\"events_url\":\"https://api.github.com/repos/errfree/test/events\",\"assignees_url\":\"https://api.github.com/repos/errfree/test/assignees{/user}\",\"branches_url\":\"https://api.github.com/repos/errfree/test/branches{/branch}\",\"tags_url\":\"https://api.github.com/repos/errfree/test/tags\",\"blobs_url\":\"https://api.github.com/repos/errfree/test/git/blobs{/sha}\",\"git_tags_url\":\"https://api.github.com/repos/errfree/test/git/tags{/sha}\",\"git_refs_url\":\"https://api.github.com/repos/errfree/test/git/refs{/sha}\",\"trees_url\":\"https://api.github.com/repos/errfree/test/git/trees{/sha}\",\"statuses_url\":\"https://api.github.com/repos/errfree/test/statuses/{sha}\",\"languages_url\":\"https://api.github.com/repos/errfree/test/languages\",\"stargazers_url\":\"https://api.github.com/repos/errfree/test/stargazers\",\"contributors_url\":\"https://api.github.com/repos/errfree/test/contributors\",\"subscribers_url\":\"https://api.github.com/repos/errfree/test/subscribers\",\"subscription_url\":\"https://api.github.com/repos/errfree/test/subscription\",\"commits_url\":\"https://api.github.com/repos/errfree/test/commits{/sha}\",\"git_commits_url\":\"https://api.github.com/repos/errfree/test/git/commits{/sha}\",\"comments_url\":\"https://api.github.com/repos/errfree/test/comments{/number}\",\"issue_comment_url\":\"https://api.github.com/repos/errfree/test/issues/comments{/number}\",\"contents_url\":\"https://api.github.com/repos/errfree/test/contents/{+path}\",\"compare_url\":\"https://api.github.com/repos/errfree/test/compare/{base}...{head}\",\"merges_url\":\"https://api.github.com/repos/errfree/test/merges\",\"archive_url\":\"https://api.github.com/repos/errfree/test/{archive_format}{/ref}\",\"downloads_url\":\"https://api.github.com/repos/errfree/test/downloads\",\"issues_url\":\"https://api.github.com/repos/errfree/test/issues{/number}\",\"pulls_url\":\"https://api.github.com/repos/errfree/test/pulls{/number}\",\"milestones_url\":\"https://api.github.com/repos/errfree/test/milestones{/number}\",\"notifications_url\":\"https://api.github.com/repos/errfree/test/notifications{?since,all,participating}\",\"labels_url\":\"https://api.github.com/repos/errfree/test/labels{/name}\",\"releases_url\":\"https://api.github.com/repos/errfree/test/releases{/id}\",\"deployments_url\":\"https://api.github.com/repos/errfree/test/deployments\",\"created_at\":\"2011-08-27T04:08:47Z\",\"updated_at\":\"2021-08-14T00:51:37Z\",\"pushed_at\":null,\"git_url\":\"git://github.com/errfree/test.git\",\"ssh_url\":\"git@github.com:errfree/test.git\",\"clone_url\":\"https://github.com/errfree/test.git\",\"svn_url\":\"https://github.com/errfree/test\",\"homepage\":\"\",\"size\":48,\"stargazers_count\":1,\"watchers_count\":1,\"language\":null,\"has_issues\":true,\"has_projects\":true,\"has_downloads\":true,\"has_wiki\":true,\"has_pages\":false,\"forks_count\":0,\"mirror_url\":null,\"archived\":false,\"disabled\":false,\"open_issues_count\":0,\"license\":null,\"allow_forking\":true,\"is_template\":false,\"web_commit_signoff_required\":false,\"topics\":[],\"visibility\":\"public\",\"forks\":0,\"open_issues\":0,\"watchers\":1,\"default_branch\":\"master\"},{\"id\":789949,\"node_id\":\"MDEwOlJlcG9zaXRvcnk3ODk5NDk=\",\"name\":\"test1\",\"full_name\":\"errfree/test1\",\"private\":false,\"owner\":{\"login\":\"errfree\",\"id\":44,\"node_id\":\"MDEyOk9yZ2FuaXphdGlvbjQ0\",\"avatar_url\":\"https://avatars.githubusercontent.com/u/44?v=4\",\"gravatar_id\":\"\",\"url\":\"https://api.github.com/users/errfree\",\"html_url\":\"https://github.com/errfree\",\"followers_url\":\"https://api.github.com/users/errfree/followers\",\"following_url\":\"https://api.github.com/users/errfree/following{/other_user}\",\"gists_url\":\"https://api.github.com/users/errfree/gists{/gist_id}\",\"starred_url\":\"https://api.github.com/users/errfree/starred{/owner}{/repo}\",\"subscriptions_url\":\"https://api.github.com/users/errfree/subscriptions\",\"organizations_url\":\"https://api.github.com/users/errfree/orgs\",\"repos_url\":\"https://api.github.com/users/errfree/repos\",\"events_url\":\"https://api.github.com/users/errfree/events{/privacy}\",\"received_events_url\":\"https://api.github.com/users/errfree/received_events\",\"type\":\"Organization\",\"site_admin\":false},\"html_url\":\"https://github.com/errfree/test1\",\"description\":null,\"fork\":false,\"url\":\"https://api.github.com/repos/errfree/test1\",\"forks_url\":\"https://api.github.com/repos/errfree/test1/forks\",\"keys_url\":\"https://api.github.com/repos/errfree/test1/keys{/key_id}\",\"collaborators_url\":\"https://api.github.com/repos/errfree/test1/collaborators{/collaborator}\",\"teams_url\":\"https://api.github.com/repos/errfree/test1/teams\",\"hooks_url\":\"https://api.github.com/repos/errfree/test1/hooks\",\"issue_events_url\":\"https://api.github.com/repos/errfree/test1/issues/events{/number}\",\"events_url\":\"https://api.github.com/repos/errfree/test1/events\",\"assignees_url\":\"https://api.github.com/repos/errfree/test1/assignees{/user}\",\"branches_url\":\"https://api.github.com/repos/errfree/test1/branches{/branch}\",\"tags_url\":\"https://api.github.com/repos/errfree/test1/tags\",\"blobs_url\":\"https://api.github.com/repos/errfree/test1/git/blobs{/sha}\",\"git_tags_url\":\"https://api.github.com/repos/errfree/test1/git/tags{/sha}\",\"git_refs_url\":\"https://api.github.com/repos/errfree/test1/git/refs{/sha}\",\"trees_url\":\"https://api.github.com/repos/errfree/test1/git/trees{/sha}\",\"statuses_url\":\"https://api.github.com/repos/errfree/test1/statuses/{sha}\",\"languages_url\":\"https://api.github.com/repos/errfree/test1/languages\",\"stargazers_url\":\"https://api.github.com/repos/errfree/test1/stargazers\",\"contributors_url\":\"https://api.github.com/repos/errfree/test1/contributors\",\"subscribers_url\":\"https://api.github.com/repos/errfree/test1/subscribers\",\"subscription_url\":\"https://api.github.com/repos/errfree/test1/subscription\",\"commits_url\":\"https://api.github.com/repos/errfree/test1/commits{/sha}\",\"git_commits_url\":\"https://api.github.com/repos/errfree/test1/git/commits{/sha}\",\"comments_url\":\"https://api.github.com/repos/errfree/test1/comments{/number}\",\"issue_comment_url\":\"https://api.github.com/repos/errfree/test1/issues/comments{/number}\",\"contents_url\":\"https://api.github.com/repos/errfree/test1/contents/{+path}\",\"compare_url\":\"https://api.github.com/repos/errfree/test1/compare/{base}...{head}\",\"merges_url\":\"https://api.github.com/repos/errfree/test1/merges\",\"archive_url\":\"https://api.github.com/repos/errfree/test1/{archive_format}{/ref}\",\"downloads_url\":\"https://api.github.com/repos/errfree/test1/downloads\",\"issues_url\":\"https://api.github.com/repos/errfree/test1/issues{/number}\",\"pulls_url\":\"https://api.github.com/repos/errfree/test1/pulls{/number}\",\"milestones_url\":\"https://api.github.com/repos/errfree/test1/milestones{/number}\",\"notifications_url\":\"https://api.github.com/repos/errfree/test1/notifications{?since,all,participating}\",\"labels_url\":\"https://api.github.com/repos/errfree/test1/labels{/name}\",\"releases_url\":\"https://api.github.com/repos/errfree/test1/releases{/id}\",\"deployments_url\":\"https://api.github.com/repos/errfree/test1/deployments\",\"created_at\":\"2010-07-22T00:40:28Z\",\"updated_at\":\"2022-04-16T14:36:00Z\",\"pushed_at\":null,\"git_url\":\"git://github.com/errfree/test1.git\",\"ssh_url\":\"git@github.com:errfree/test1.git\",\"clone_url\":\"https://github.com/errfree/test1.git\",\"svn_url\":\"https://github.com/errfree/test1\",\"homepage\":\"\",\"size\":48,\"stargazers_count\":3,\"watchers_count\":3,\"language\":null,\"has_issues\":true,\"has_projects\":true,\"has_downloads\":true,\"has_wiki\":true,\"has_pages\":false,\"forks_count\":0,\"mirror_url\":null,\"archived\":false,\"disabled\":false,\"open_issues_count\":0,\"license\":null,\"allow_forking\":true,\"is_template\":false,\"web_commit_signoff_required\":false,\"topics\":[],\"visibility\":\"public\",\"forks\":0,\"open_issues\":0,\"watchers\":3,\"default_branch\":\"master\"}]"
        val USER_REPO_PAYLOAD_EMPTY = "[{\"id\":2277888,\"node_id\":\"MDEwOlJlcG9zaXRvcnkyMjc3ODg4\",\"name\":\"test\",\"full_name\":\"errfree/test\",\"private\":false,\"description\":null,\"fork\":false,\"url\":\"https://api.github.com/repos/errfree/test\",\"forks_url\":\"https://api.github.com/repos/errfree/test/forks\",\"keys_url\":\"https://api.github.com/repos/errfree/test/keys{/key_id}\",\"collaborators_url\":\"https://api.github.com/repos/errfree/test/collaborators{/collaborator}\",\"teams_url\":\"https://api.github.com/repos/errfree/test/teams\",\"hooks_url\":\"https://api.github.com/repos/errfree/test/hooks\",\"issue_events_url\":\"https://api.github.com/repos/errfree/test/issues/events{/number}\",\"events_url\":\"https://api.github.com/repos/errfree/test/events\",\"assignees_url\":\"https://api.github.com/repos/errfree/test/assignees{/user}\",\"branches_url\":\"https://api.github.com/repos/errfree/test/branches{/branch}\",\"tags_url\":\"https://api.github.com/repos/errfree/test/tags\",\"blobs_url\":\"https://api.github.com/repos/errfree/test/git/blobs{/sha}\",\"git_tags_url\":\"https://api.github.com/repos/errfree/test/git/tags{/sha}\",\"git_refs_url\":\"https://api.github.com/repos/errfree/test/git/refs{/sha}\",\"trees_url\":\"https://api.github.com/repos/errfree/test/git/trees{/sha}\",\"statuses_url\":\"https://api.github.com/repos/errfree/test/statuses/{sha}\",\"languages_url\":\"https://api.github.com/repos/errfree/test/languages\",\"stargazers_url\":\"https://api.github.com/repos/errfree/test/stargazers\",\"contributors_url\":\"https://api.github.com/repos/errfree/test/contributors\",\"subscribers_url\":\"https://api.github.com/repos/errfree/test/subscribers\",\"subscription_url\":\"https://api.github.com/repos/errfree/test/subscription\",\"commits_url\":\"https://api.github.com/repos/errfree/test/commits{/sha}\",\"git_commits_url\":\"https://api.github.com/repos/errfree/test/git/commits{/sha}\",\"comments_url\":\"https://api.github.com/repos/errfree/test/comments{/number}\",\"issue_comment_url\":\"https://api.github.com/repos/errfree/test/issues/comments{/number}\",\"contents_url\":\"https://api.github.com/repos/errfree/test/contents/{+path}\",\"compare_url\":\"https://api.github.com/repos/errfree/test/compare/{base}...{head}\",\"merges_url\":\"https://api.github.com/repos/errfree/test/merges\",\"archive_url\":\"https://api.github.com/repos/errfree/test/{archive_format}{/ref}\",\"downloads_url\":\"https://api.github.com/repos/errfree/test/downloads\",\"issues_url\":\"https://api.github.com/repos/errfree/test/issues{/number}\",\"pulls_url\":\"https://api.github.com/repos/errfree/test/pulls{/number}\",\"milestones_url\":\"https://api.github.com/repos/errfree/test/milestones{/number}\",\"notifications_url\":\"https://api.github.com/repos/errfree/test/notifications{?since,all,participating}\",\"labels_url\":\"https://api.github.com/repos/errfree/test/labels{/name}\",\"releases_url\":\"https://api.github.com/repos/errfree/test/releases{/id}\",\"deployments_url\":\"https://api.github.com/repos/errfree/test/deployments\",\"created_at\":\"2011-08-27T04:08:47Z\",\"updated_at\":\"2021-08-14T00:51:37Z\",\"pushed_at\":null,\"git_url\":\"git://github.com/errfree/test.git\",\"ssh_url\":\"git@github.com:errfree/test.git\",\"clone_url\":\"https://github.com/errfree/test.git\",\"svn_url\":\"https://github.com/errfree/test\",\"homepage\":\"\",\"size\":48,\"stargazers_count\":1,\"watchers_count\":1,\"language\":null,\"has_issues\":true,\"has_projects\":true,\"has_downloads\":true,\"has_wiki\":true,\"has_pages\":false,\"forks_count\":0,\"mirror_url\":null,\"archived\":false,\"disabled\":false,\"open_issues_count\":0,\"license\":null,\"allow_forking\":true,\"is_template\":false,\"web_commit_signoff_required\":false,\"topics\":[],\"visibility\":\"public\",\"forks\":0,\"open_issues\":0,\"watchers\":1,\"default_branch\":\"master\"},{\"id\":789949,\"node_id\":\"MDEwOlJlcG9zaXRvcnk3ODk5NDk=\",\"name\":\"test1\",\"full_name\":\"errfree/test1\",\"private\":false,\"owner\":{\"login\":\"errfree\",\"id\":44,\"node_id\":\"MDEyOk9yZ2FuaXphdGlvbjQ0\",\"avatar_url\":\"https://avatars.githubusercontent.com/u/44?v=4\",\"gravatar_id\":\"\",\"url\":\"https://api.github.com/users/errfree\",\"html_url\":\"https://github.com/errfree\",\"followers_url\":\"https://api.github.com/users/errfree/followers\",\"following_url\":\"https://api.github.com/users/errfree/following{/other_user}\",\"gists_url\":\"https://api.github.com/users/errfree/gists{/gist_id}\",\"starred_url\":\"https://api.github.com/users/errfree/starred{/owner}{/repo}\",\"subscriptions_url\":\"https://api.github.com/users/errfree/subscriptions\",\"organizations_url\":\"https://api.github.com/users/errfree/orgs\",\"repos_url\":\"https://api.github.com/users/errfree/repos\",\"events_url\":\"https://api.github.com/users/errfree/events{/privacy}\",\"received_events_url\":\"https://api.github.com/users/errfree/received_events\",\"type\":\"Organization\",\"site_admin\":false},\"html_url\":\"https://github.com/errfree/test1\",\"description\":null,\"fork\":false,\"url\":\"https://api.github.com/repos/errfree/test1\",\"forks_url\":\"https://api.github.com/repos/errfree/test1/forks\",\"keys_url\":\"https://api.github.com/repos/errfree/test1/keys{/key_id}\",\"collaborators_url\":\"https://api.github.com/repos/errfree/test1/collaborators{/collaborator}\",\"teams_url\":\"https://api.github.com/repos/errfree/test1/teams\",\"hooks_url\":\"https://api.github.com/repos/errfree/test1/hooks\",\"issue_events_url\":\"https://api.github.com/repos/errfree/test1/issues/events{/number}\",\"events_url\":\"https://api.github.com/repos/errfree/test1/events\",\"assignees_url\":\"https://api.github.com/repos/errfree/test1/assignees{/user}\",\"branches_url\":\"https://api.github.com/repos/errfree/test1/branches{/branch}\",\"tags_url\":\"https://api.github.com/repos/errfree/test1/tags\",\"blobs_url\":\"https://api.github.com/repos/errfree/test1/git/blobs{/sha}\",\"git_tags_url\":\"https://api.github.com/repos/errfree/test1/git/tags{/sha}\",\"git_refs_url\":\"https://api.github.com/repos/errfree/test1/git/refs{/sha}\",\"trees_url\":\"https://api.github.com/repos/errfree/test1/git/trees{/sha}\",\"statuses_url\":\"https://api.github.com/repos/errfree/test1/statuses/{sha}\",\"languages_url\":\"https://api.github.com/repos/errfree/test1/languages\",\"stargazers_url\":\"https://api.github.com/repos/errfree/test1/stargazers\",\"contributors_url\":\"https://api.github.com/repos/errfree/test1/contributors\",\"subscribers_url\":\"https://api.github.com/repos/errfree/test1/subscribers\",\"subscription_url\":\"https://api.github.com/repos/errfree/test1/subscription\",\"commits_url\":\"https://api.github.com/repos/errfree/test1/commits{/sha}\",\"git_commits_url\":\"https://api.github.com/repos/errfree/test1/git/commits{/sha}\",\"comments_url\":\"https://api.github.com/repos/errfree/test1/comments{/number}\",\"issue_comment_url\":\"https://api.github.com/repos/errfree/test1/issues/comments{/number}\",\"contents_url\":\"https://api.github.com/repos/errfree/test1/contents/{+path}\",\"compare_url\":\"https://api.github.com/repos/errfree/test1/compare/{base}...{head}\",\"merges_url\":\"https://api.github.com/repos/errfree/test1/merges\",\"archive_url\":\"https://api.github.com/repos/errfree/test1/{archive_format}{/ref}\",\"downloads_url\":\"https://api.github.com/repos/errfree/test1/downloads\",\"issues_url\":\"https://api.github.com/repos/errfree/test1/issues{/number}\",\"pulls_url\":\"https://api.github.com/repos/errfree/test1/pulls{/number}\",\"milestones_url\":\"https://api.github.com/repos/errfree/test1/milestones{/number}\",\"notifications_url\":\"https://api.github.com/repos/errfree/test1/notifications{?since,all,participating}\",\"labels_url\":\"https://api.github.com/repos/errfree/test1/labels{/name}\",\"releases_url\":\"https://api.github.com/repos/errfree/test1/releases{/id}\",\"deployments_url\":\"https://api.github.com/repos/errfree/test1/deployments\",\"created_at\":\"2010-07-22T00:40:28Z\",\"updated_at\":\"2022-04-16T14:36:00Z\",\"pushed_at\":null,\"git_url\":\"git://github.com/errfree/test1.git\",\"ssh_url\":\"git@github.com:errfree/test1.git\",\"clone_url\":\"https://github.com/errfree/test1.git\",\"svn_url\":\"https://github.com/errfree/test1\",\"homepage\":\"\",\"size\":48,\"stargazers_count\":3,\"watchers_count\":3,\"language\":null,\"has_issues\":true,\"has_projects\":true,\"has_downloads\":true,\"has_wiki\":true,\"has_pages\":false,\"forks_count\":0,\"mirror_url\":null,\"archived\":false,\"disabled\":false,\"open_issues_count\":0,\"license\":null,\"allow_forking\":true,\"is_template\":false,\"web_commit_signoff_required\":false,\"topics\":[],\"visibility\":\"public\",\"forks\":0,\"open_issues\":0,\"watchers\":3,\"default_branch\":\"master\"}]"

        val USER_DETAIL_PAYLOAD = "{\"login\":\"wingSan1262\",\"id\":72028903,\"node_id\":\"MDQ6VXNlcjcyMDI4OTAz\",\"avatar_url\":\"https://avatars.githubusercontent.com/u/72028903?v=4\",\"gravatar_id\":\"\",\"url\":\"https://api.github.com/users/wingSan1262\",\"html_url\":\"https://github.com/wingSan1262\",\"followers_url\":\"https://api.github.com/users/wingSan1262/followers\",\"following_url\":\"https://api.github.com/users/wingSan1262/following{/other_user}\",\"gists_url\":\"https://api.github.com/users/wingSan1262/gists{/gist_id}\",\"starred_url\":\"https://api.github.com/users/wingSan1262/starred{/owner}{/repo}\",\"subscriptions_url\":\"https://api.github.com/users/wingSan1262/subscriptions\",\"organizations_url\":\"https://api.github.com/users/wingSan1262/orgs\",\"repos_url\":\"https://api.github.com/users/wingSan1262/repos\",\"events_url\":\"https://api.github.com/users/wingSan1262/events{/privacy}\",\"received_events_url\":\"https://api.github.com/users/wingSan1262/received_events\",\"type\":\"User\",\"site_admin\":false,\"name\":\"wingSan1262\",\"company\":null,\"blog\":\"\",\"location\":\"Indonesia\",\"email\":null,\"hireable\":null,\"bio\":\"MediocreProgrammedandabitofcircuitdesign.\\r\\n\\r\\nMainlyinandroidjava,C++,abitofhtml,css,php,c#unity.phythonforbasicml(pandaandsklearn)\",\"twitter_username\":null,\"public_repos\":15,\"public_gists\":0,\"followers\":1,\"following\":1,\"created_at\":\"2020-09-28T14:17:10Z\",\"updated_at\":\"2022-08-08T11:37:56Z\"}"
    }
}

