package vanrrtech.app.ajaib_app_sample.domain.UseCases.github

import kotlinx.coroutines.launch
import vanrrtech.app.ajaib_app_sample.base_components.base_classes.BaseUseCase
import vanrrtech.app.ajaib_app_sample.data.remote_repository.RemoteApiRetrofitClient
import vanrrtech.app.ajaib_app_sample.domain.data_model.github.request.UserDetailRequest
import vanrrtech.app.ajaib_app_sample.domain.data_model.github.response.UserDetails

class GetGithubUserDetails(
    val myApi : RemoteApiRetrofitClient
) : BaseUseCase<UserDetailRequest, UserDetails>() {

    override fun setup(parameter: UserDetailRequest) {
        super.setup(parameter)
        launch(coroutineContext) {
            execute {
                var data : UserDetails? = null
                myApi.getUserDetail(parameter).let {
                    data = it
                    return@execute data
                }
            }}
    }
}
