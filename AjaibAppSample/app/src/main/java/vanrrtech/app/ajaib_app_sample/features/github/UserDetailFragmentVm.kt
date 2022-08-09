package vanrrtech.app.ajaib_app_sample.features.github


import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import vanrrtech.app.ajaib_app_sample.domain.UseCases.github.*
import vanrrtech.app.ajaib_app_sample.domain.data_model.github.request.UserDetailRequest
import vanrrtech.app.ajaib_app_sample.domain.data_model.github.response.GithubUserItemResponse

class UserDetailFragmentVm(
    val getGithubUserDetails: GetGithubUserDetails,
    val getGithubUserRepoContent: GetGithubUserRepoContentUseCase
) : ViewModel() {

    var userClickedModel : GithubUserItemResponse? = null

    val userDetailLiveData = getGithubUserDetails.currentData
    fun fetchUserData(param : UserDetailRequest) {
        getGithubUserDetails.setup(param)
    }

    val userRepoLiveData = getGithubUserRepoContent.currentData
    fun fetchRepoList(param : UserDetailRequest){
        getGithubUserRepoContent.setup(param)
    }

    fun onStart(owner: LifecycleOwner){
        getGithubUserRepoContent.currentData.removeObservers(owner)
        getGithubUserDetails.currentData.removeObservers(owner)
    }

    fun onDestroy() {
        getGithubUserRepoContent.cancel()
        getGithubUserDetails.cancel()
    }


}