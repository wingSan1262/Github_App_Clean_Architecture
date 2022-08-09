package vanrrtech.app.prodiaappsample.features.github


import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import vanrrtech.app.prodiaappsample.base_components.entities.Event
import vanrrtech.app.prodiaappsample.base_components.entities.ResourceState
import vanrrtech.app.prodiaappsample.domain.UseCases.github.*
import vanrrtech.app.prodiaappsample.domain.data_model.github.request.UserDetailRequest
import vanrrtech.app.prodiaappsample.domain.data_model.github.response.GithubUserItemResponse
import vanrrtech.app.prodiaappsample.domain.data_model.github.response.UserRepoDetails

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


}