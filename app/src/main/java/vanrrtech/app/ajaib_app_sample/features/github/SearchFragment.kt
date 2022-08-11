package vanrrtech.app.ajaib_app_sample.features.github

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import vanrrtech.app.ajaib_app_sample.R
import vanrrtech.app.ajaib_app_sample.base_components.base_classes.BaseFragment
import vanrrtech.app.ajaib_app_sample.base_components.constants.PARAMETERS
import vanrrtech.app.ajaib_app_sample.base_components.entities.ResourceState
import vanrrtech.app.ajaib_app_sample.base_components.extensions.*
import vanrrtech.app.ajaib_app_sample.databinding.SearchUserGithubFragmentBinding
import vanrrtech.app.ajaib_app_sample.domain.data_model.github.request.SearchUserRequest
import vanrrtech.app.ajaib_app_sample.domain.data_model.github.response.GithubUserItemResponse

class SearchFragment : BaseFragment<SearchUserGithubFragmentBinding>() {

    var userListAdapter : UserListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        fragmentComponent.inject(this)
        super.onCreate(savedInstanceState)
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
        initUi()
        observerData()
        if(!searchViewModel.isSearchQueried){
            searchViewModel.fetchUserList()
            searchViewModel.firstOpen = userListAdapter?.listItems?.isEmpty() == true
        }
    }

    override fun onDestroy() {
        searchViewModel.onDestroy()
        super.onDestroy()
    }
    fun initUi(){

        withBinding {
            userRv.setVisibility(false)
            if(userListAdapter == null) {
                userListAdapter = UserListAdapter(loginHandler) {
                    it as GithubUserItemResponse
                    findNullableNavController()?.navigateSafe(
                        R.id.movieList_to_movieDetail,
                        bundleOf(
                            PARAMETERS.USER_CLICKED_MODEL to it
                        )
                    )
                }
            }
            userRv.let {
                it.layoutManager = LinearLayoutManager(context,
                    LinearLayoutManager.VERTICAL,
                    false)
                it.isNestedScrollingEnabled = false
                it.adapter = userListAdapter
            }
            searchField.textChanges()?.debounce(400)?.onEach { s ->
                if (s.isNullOrBlank()) {
                    if(searchViewModel.isSearchQueried )
                        searchViewModel.fetchUserList(); searchViewModel.isSearchQueried = false
                    return@onEach
                }
                searchViewModel.isSearchQueried = true
                searchViewModel.fetchSearchResult(SearchUserRequest(
                    s.toString()
                ))
            }?.launchIn(lifecycleScope)
        }
    }

    fun updateList(items: List<GithubUserItemResponse>, isSearch: Boolean = false){
        if(items.isEmpty()){
            snackBarHandler.showSnackBar("no user found here sorry . . .")
            return
        }
        userListAdapter?.clearList()
        lifecycleScope.launch {
            withBinding {
                cvLoading.setVisibility(true)
                userRv.setVisibility(true)
            }
            if(!isSearch && searchViewModel.firstOpen)
                items.asReversed().forEach {
                    userListAdapter?.insertAtTop(it)
                    delay(50)
                    withBinding { userRv.smoothScrollToPosition(0) }
                } else userListAdapter?.insertAll(items)

            withBinding { cvLoading.setVisibility(false) }
            searchViewModel.firstOpen = false
        }
    }

    fun observerData(){
        observeEvent(searchViewModel.githubUserLiveData){
            when (it) {
                is ResourceState.Success -> updateList(it.body)
                is ResourceState.Failure -> {
                    searchViewModel.fetchOfflineUserList()
                    snackBarHandler.showSnackBar("Oops, please check your internet connection")
                }
                else -> {}
            }
        }

        observeEvent(searchViewModel.offlineGithubUserLiveData){
            when (it) {
                is ResourceState.Success -> {
                    updateList(it.body)
                    withBinding { cvLoading.setVisibility(false) }
                }
                is ResourceState.Failure -> {
                    withBinding { cvLoading.setVisibility(false) }
                    snackBarHandler.showSnackBar("Oops, please check your internet connection")
                }
                else -> {}
            }
        }

        observeEvent(searchViewModel.updateOfflineUserLiveData){
            when (it) {
                is ResourceState.Failure -> {
                    withBinding { cvLoading.setVisibility(false) }
                    snackBarHandler.showSnackBar(it.exception.message.toString())
                }
                else -> {}
            }
        }

        observeEvent(searchViewModel.searchResultLiveData){
            when (it) {
                is ResourceState.Success -> {
                    if(it.body.items.isNotEmpty())
                        updateList(it.body.items as List<GithubUserItemResponse>, isSearch = true) else {
                        snackBarHandler.showSnackBar("no user found here sorry . . .")
                        searchViewModel.fetchUserList()
                    }
                }
                is ResourceState.Failure -> {
                    snackBarHandler.showSnackBar(it.exception.message.toString())
                }
                else -> {}
            }
        }
    }
}