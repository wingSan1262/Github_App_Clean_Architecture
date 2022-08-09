package vanrrtech.app.prodiaappsample.domain.UseCases.github

import kotlinx.coroutines.launch
import vanrrtech.app.prodiaappsample.base_components.base_classes.BaseUseCase
import vanrrtech.app.prodiaappsample.data.remote_repository.RemoteApiRetrofitClient
import vanrrtech.app.prodiaappsample.domain.data_model.github.request.SearchUserRequest
import vanrrtech.app.prodiaappsample.domain.data_model.github.response.SearchResult

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
