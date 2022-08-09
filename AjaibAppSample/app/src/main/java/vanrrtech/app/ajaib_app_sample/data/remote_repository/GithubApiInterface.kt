package vanrrtech.app.ajaib_app_sample.data.remote_repository

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import vanrrtech.app.ajaib_app_sample.domain.data_model.github.response.GithubUserItemResponse
import vanrrtech.app.ajaib_app_sample.domain.data_model.github.response.SearchResult
import vanrrtech.app.ajaib_app_sample.domain.data_model.github.response.UserDetails
import vanrrtech.app.ajaib_app_sample.domain.data_model.github.response.UserRepoDetails

interface GithubApiInterface {
    @GET("users")
    suspend fun getUsersList(): List<GithubUserItemResponse>

    @GET("search/users")
    suspend fun searchUser(@Query("q") searchKey : String, @Query("type") type : String): SearchResult?

    @GET("users/{USERNAME}/repos")
    suspend fun getUserRepos(@Path("USERNAME") userName : String) : List<UserRepoDetails>

    @GET("users/{USERNAME}")
    suspend fun getUserDetails(@Path("USERNAME") userName : String) : UserDetails?
}