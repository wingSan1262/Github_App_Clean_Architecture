package vanrrtech.app.ajaib_app_sample.domain.UseCases.imdb

import kotlinx.coroutines.launch
import vanrrtech.app.ajaib_app_sample.base_components.base_classes.BaseUseCase
import vanrrtech.app.ajaib_app_sample.data.remote_repository.RemoteApiRetrofitClient
import vanrrtech.app.ajaib_app_sample.domain.data_model.github.response.GithubUserItemResponse
import vanrrtech.app.ajaib_app_sample.domain.data_model.imdb.MovieItemResponse

class GetMovieListUseCase(
    val myApi : RemoteApiRetrofitClient
) : BaseUseCase<Any?, MovieItemResponse>() {
    override fun setup(parameter: Any?) {
        super.setup(parameter)
        launch(coroutineContext) {
            execute {
                var data : MovieItemResponse? = null
                myApi.getMovieList().let {
                    data = it
                    return@execute data
                }
            }}
    }
}
