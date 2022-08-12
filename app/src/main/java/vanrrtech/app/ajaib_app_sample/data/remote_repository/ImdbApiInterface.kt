package vanrrtech.app.ajaib_app_sample.data.remote_repository

import retrofit2.http.GET
import vanrrtech.app.ajaib_app_sample.domain.data_model.imdb.MovieItemResponse

interface ImdbApiInterface {
    @GET("en/API/Top250Movies/k_ud2ge058")
    suspend fun getAllMovieList(): MovieItemResponse

}