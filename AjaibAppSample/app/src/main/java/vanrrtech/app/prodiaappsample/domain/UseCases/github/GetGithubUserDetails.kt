package vanrrtech.app.prodiaappsample.domain.UseCases.github

import kotlinx.coroutines.launch
import vanrrtech.app.prodiaappsample.base_components.base_classes.BaseUseCase
import vanrrtech.app.prodiaappsample.data.remote_repository.RemoteApiRetrofitClient
import vanrrtech.app.prodiaappsample.domain.data_model.github.request.UserDetailRequest
import vanrrtech.app.prodiaappsample.domain.data_model.github.response.UserDetails

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
