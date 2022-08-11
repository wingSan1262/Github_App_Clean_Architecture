package vanrrtech.app.ajaib_app_sample.domain.UseCases.imdb

import kotlinx.coroutines.launch
import vanrrtech.app.ajaib_app_sample.base_components.base_classes.BaseUseCase
import vanrrtech.app.ajaib_app_sample.data.SQDb.github.UserListDao
import vanrrtech.app.ajaib_app_sample.data.SQDb.imdb.MovieListDao
import vanrrtech.app.ajaib_app_sample.domain.data_model.github.response.GithubUserItemResponse
import vanrrtech.app.ajaib_app_sample.domain.data_model.imdb.MovieItem

class MovieOfflineUpdateUseCase(
    val myApi : MovieListDao
) : BaseUseCase<List<MovieItem>, Boolean>() {
    override fun setup(parameter: List<MovieItem>) {
        super.setup(parameter)
        launch(coroutineContext) {
            execute {
                try {
                    val param = parameter
                    myApi.nukeTable()
                    param.forEach {
                        myApi.insert(it)
                    }
                } catch (e : Throwable){
                    val e = e
                    return@execute null
                }
                return@execute true
            }}
    }
}
