package vanrrtech.app.ajaib_app_sample.features.Imdb


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import vanrrtech.app.ajaib_app_sample.base_components.entities.Event
import vanrrtech.app.ajaib_app_sample.base_components.entities.ResourceState
import vanrrtech.app.ajaib_app_sample.domain.UseCases.github.GetGithubUserListUseCase
import vanrrtech.app.ajaib_app_sample.domain.UseCases.github.GetOfflineGithubUserListUseCase
import vanrrtech.app.ajaib_app_sample.domain.UseCases.github.SearchUserGithubUseCase
import vanrrtech.app.ajaib_app_sample.domain.UseCases.github.UpdateOfflineGithubUserListUseCase
import vanrrtech.app.ajaib_app_sample.domain.UseCases.imdb.GetMovieListUseCase
import vanrrtech.app.ajaib_app_sample.domain.data_model.github.request.SearchUserRequest
import vanrrtech.app.ajaib_app_sample.domain.data_model.github.response.GithubUserItemResponse
import vanrrtech.app.ajaib_app_sample.domain.data_model.imdb.MovieItem

class MovieListModel(
    val movieUseCase: GetMovieListUseCase,
) : ViewModel() {

    var firstOpen: Boolean = false
    var isSearchQueried = false

    val movieLiveData = Transformations.switchMap(movieUseCase.currentData) { it ->
        val livedata = MutableLiveData<Event<ResourceState<List<MovieItem>>>>()
        it.contentIfNotHandled?.let {

            if(it is ResourceState.Success){
                // safe to data base
                livedata.value = Event(ResourceState.Success(it.body.items))
            }
        }
        livedata
    }

    fun


    fun onDestroy(){
        getGithubUserListUseCase.cancel()
        getOfflineGithubUserListUseCase.cancel()
        updateGithubUserListUseCase.cancel()
        searchUserGithubUseCase.cancel()
    }
}