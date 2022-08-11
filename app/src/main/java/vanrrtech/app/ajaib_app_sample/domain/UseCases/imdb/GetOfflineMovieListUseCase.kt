package vanrrtech.app.ajaib_app_sample.domain.UseCases.imdb

import kotlinx.coroutines.launch
import vanrrtech.app.ajaib_app_sample.base_components.base_classes.BaseUseCase
import vanrrtech.app.ajaib_app_sample.data.SQDb.imdb.MovieListDao
import vanrrtech.app.ajaib_app_sample.data.remote_repository.RemoteApiRetrofitClient
import vanrrtech.app.ajaib_app_sample.domain.data_model.github.response.GithubUserItemResponse
import vanrrtech.app.ajaib_app_sample.domain.data_model.imdb.MovieItem
import vanrrtech.app.ajaib_app_sample.domain.data_model.imdb.MovieItemResponse

class GetOfflineMovieListUseCase(
    val myApi : MovieListDao
) : BaseUseCase<Any?, List<MovieItem>>() {
    override fun setup(parameter: Any?) {
        super.setup(parameter)
        launch(coroutineContext) {
            execute {
                var data : List<MovieItem>? = null
                myApi.loadAll().let {
                    data = it
                    return@execute data
                }
            }}
    }
}
