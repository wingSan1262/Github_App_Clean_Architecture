package vanrrtech.app.ajaib_app_sample.data.remote_repository

import retrofit2.Retrofit
import vanrrtech.app.ajaib_app_sample.domain.data_model.imdb.MovieItemResponse

class RemoteApiRetrofitClient(
    val retrofit : Retrofit
) {

    companion object{
        val BASE_URL_WEATHER = "https://api.openweathermap.org/"
        val BASE_URL_GITHUB = "https://api.github.com/"
        val BASE_IMDB = "https://imdb-api.com/"
    }

    fun getImdbRetrofit(): ImdbApiInterface {
        return retrofit
            .newBuilder()
            .baseUrl(BASE_IMDB)
            .build()
            .create(ImdbApiInterface::class.java)
    }

    suspend fun getMovieList() : MovieItemResponse? {
        return getImdbRetrofit().getAllMovieList()
    }
}