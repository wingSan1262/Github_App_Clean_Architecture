package vanrrtech.app.ajaib_app_sample.data.remote_repository

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import vanrrtech.app.ajaib_app_sample.domain.data_model.github.response.GithubUserItemResponse
import vanrrtech.app.ajaib_app_sample.domain.data_model.github.response.SearchResult
import vanrrtech.app.ajaib_app_sample.domain.data_model.github.response.UserDetails
import vanrrtech.app.ajaib_app_sample.domain.data_model.github.response.UserRepoDetails
import vanrrtech.app.ajaib_app_sample.domain.data_model.imdb.MovieItemResponse

interface ImdbApiInterface {
    @GET("en/API/Top250Movies/k_ae9hkfd4")
    suspend fun getAllMovieList(): MovieItemResponse

}