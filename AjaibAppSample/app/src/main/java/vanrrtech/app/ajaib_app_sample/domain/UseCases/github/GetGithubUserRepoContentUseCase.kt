package vanrrtech.app.ajaib_app_sample.domain.UseCases.github

import kotlinx.coroutines.launch
import vanrrtech.app.ajaib_app_sample.base_components.base_classes.BaseUseCase
import vanrrtech.app.ajaib_app_sample.data.remote_repository.RemoteApiRetrofitClient
import vanrrtech.app.ajaib_app_sample.domain.data_model.github.request.UserDetailRequest
import vanrrtech.app.ajaib_app_sample.domain.data_model.github.response.UserRepoDetails

class GetGithubUserRepoContentUseCase(
    val myApi : RemoteApiRetrofitClient
) : BaseUseCase<UserDetailRequest, List<UserRepoDetails>>() {

    override fun setup(parameter: UserDetailRequest) {
        super.setup(parameter)
        launch(coroutineContext) {
            execute {
                var data : List<UserRepoDetails>? = null
                myApi.getUserRepo(parameter).let {
                    data = it
                    return@execute data
                }
            }}
    }
}
