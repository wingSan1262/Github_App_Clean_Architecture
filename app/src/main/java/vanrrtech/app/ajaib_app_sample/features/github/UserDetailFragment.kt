package vanrrtech.app.ajaib_app_sample.features.github

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import vanrrtech.app.ajaib_app_sample.base_components.base_classes.BaseFragment
import vanrrtech.app.ajaib_app_sample.base_components.constants.PARAMETERS
import vanrrtech.app.ajaib_app_sample.base_components.entities.ResourceState
import vanrrtech.app.ajaib_app_sample.base_components.extensions.loadImage
import vanrrtech.app.ajaib_app_sample.base_components.extensions.observeEvent
import vanrrtech.app.ajaib_app_sample.base_components.extensions.setVisibility
import vanrrtech.app.ajaib_app_sample.databinding.UserDetailFragmentBinding
import vanrrtech.app.ajaib_app_sample.domain.data_model.github.request.UserDetailRequest
import vanrrtech.app.ajaib_app_sample.domain.data_model.github.response.GithubUserItemResponse
import vanrrtech.app.ajaib_app_sample.domain.data_model.github.response.UserDetails
import vanrrtech.app.ajaib_app_sample.domain.data_model.github.response.UserRepoDetails

class UserDetailFragment : BaseFragment<UserDetailFragmentBinding>() {

    var repoAdapter : RepoAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        fragmentComponent.inject(this)
        super.onCreate(savedInstanceState)
        (arguments?.getSerializable(PARAMETERS.USER_CLICKED_MODEL)
                as? GithubUserItemResponse)?.let {
            userDetailViewModel.userClickedModel = it
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        container?.let {
            bindThisView(this, layoutInflater, it)
        }
        return viewBinding.root
    }

    override fun onResume() {
        super.onResume()
        userDetailViewModel.onStart(viewLifecycleOwner)
        fetchAllData()
        initUi()
        observerData()
    }

    override fun onDestroy() {
        userDetailViewModel.onDestroy()
        super.onDestroy()
    }

    fun fetchAllData(){
        userDetailViewModel.run{
            userClickedModel?.login?.let {
                UserDetailRequest(it).run{
                    fetchRepoList(this)
                    fetchUserData(this)
                }
            }
        }
    }
    fun initUi(){
        withBinding {
            repoAdapter = RepoAdapter();
            userRepoRv.apply {
                layoutManager = LinearLayoutManager(context,
                    LinearLayoutManager.VERTICAL,
                    false)
                isNestedScrollingEnabled = false
                adapter = repoAdapter
            }
        }
    }

    fun updateList(items: List<UserRepoDetails>){
        if(items.isEmpty()){
            snackBarHandler.showSnackBar("no repo found here sorry . . .")
            return
        }
        repoAdapter?.clearList()
        lifecycleScope.launch {
                items.forEach {
                    repoAdapter?.insertAtTop(it)
                    delay(200)
                    withBinding { userRepoRv.smoothScrollToPosition(0) }
                }
        }
    }

    fun updateUserDetails(data: UserDetails) {
        withBinding {
            userImage.loadImage(data.avatarUrl)
            userName.text = data.name ?: data.login
            userAccountId.text = "@${data.login}"
            userDescTv.apply {
                data.bio?.let{ text = it } ?: run { setVisibility(false)}
            }
            followerTv.text = "${data.followers} Followers"
            followingTv.text = "${data.following} Following"
            locationTvTv.text = data.location ?: "Not Available"
            emailTvDetail.text = data.email ?: "Not Available"
        }
    }

    fun observerData(){
        observeEvent(userDetailViewModel.userRepoLiveData){
            when (it){
                is ResourceState.Success ->
                    updateList(it.body)
                is ResourceState.Failure ->
                    snackBarHandler.showSnackBar("Oops, please check your internet connection")
                else -> {} // do nothing
            }
        }
        observeEvent(userDetailViewModel.userDetailLiveData){
            when (it){
                is ResourceState.Success -> {
                    updateUserDetails(it.body)
                }
                is ResourceState.Failure ->
                    snackBarHandler.showSnackBar("Oops, please check your internet connection")
                else -> {} // do nothing
            }
        }
    }
}