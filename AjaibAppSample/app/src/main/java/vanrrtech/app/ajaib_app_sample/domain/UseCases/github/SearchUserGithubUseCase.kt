package vanrrtech.app.ajaib_app_sample.domain.UseCases.github

import kotlinx.coroutines.launch
import vanrrtech.app.ajaib_app_sample.base_components.base_classes.BaseUseCase
import vanrrtech.app.ajaib_app_sample.data.remote_repository.RemoteApiRetrofitClient
import vanrrtech.app.ajaib_app_sample.domain.data_model.github.request.SearchUserRequest
import vanrrtech.app.ajaib_app_sample.domain.data_model.github.response.SearchResult

class SearchUserGithubUseCase(
    val myApi : RemoteApiRetrofitClient
) : BaseUseCase<SearchUserRequest, SearchResult>() {
    override fun setup(parameter: SearchUserRequest) {
        super.setup(parameter)
        launch(coroutineContext) {
            execute {
                var data : SearchResult? = null
                myApi.searchUserResult(parameter).let {
                    data = it
                    return@execute data
                }
            }}
    }
}
