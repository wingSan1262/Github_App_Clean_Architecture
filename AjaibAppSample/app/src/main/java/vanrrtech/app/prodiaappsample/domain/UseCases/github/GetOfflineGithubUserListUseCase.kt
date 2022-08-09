package vanrrtech.app.prodiaappsample.domain.UseCases.github

import kotlinx.coroutines.launch
import vanrrtech.app.prodiaappsample.base_components.base_classes.BaseUseCase
import vanrrtech.app.prodiaappsample.data.SQDb.github.UserListDao
import vanrrtech.app.prodiaappsample.domain.data_model.github.response.GithubUserItemResponse

class GetOfflineGithubUserListUseCase(
    val myApi : UserListDao
) : BaseUseCase<Any?, List<GithubUserItemResponse>>() {
    override fun setup(parameter: Any?) {
        super.setup(parameter)
        launch(coroutineContext) {
            execute {
                var data = ArrayList<GithubUserItemResponse>()
                myApi.loadAllUser()?.let {
                    data.addAll(it)
                    if(data.isEmpty())
                        return@execute null else return@execute data
                }
            }}
    }
}
