package vanrrtech.app.ajaib_app_sample.features.Imdb


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import vanrrtech.app.ajaib_app_sample.base_components.entities.Event
import vanrrtech.app.ajaib_app_sample.base_components.entities.ResourceState
import vanrrtech.app.ajaib_app_sample.domain.UseCases.imdb.GetMovieListUseCase
import vanrrtech.app.ajaib_app_sample.domain.UseCases.imdb.GetOfflineMovieListUseCase
import vanrrtech.app.ajaib_app_sample.domain.UseCases.imdb.MovieOfflineUpdateUseCase
import vanrrtech.app.ajaib_app_sample.domain.data_model.imdb.MovieItem

class MovieListModel(
    val movieUseCase: GetMovieListUseCase,
    val getOfflineMovieListUseCase: GetOfflineMovieListUseCase,
    val movieOfflineUpdateUseCase: MovieOfflineUpdateUseCase,
) : ViewModel()  {

    var onNotifyMovieList : (ResourceState<List<MovieItem>>?) -> Unit = {}
    var onNotifyDbError : (ResourceState<Boolean>?) -> Unit = {}
    fun setListener(
        cbMovie : (ResourceState<List<MovieItem>>?) -> Unit,
        cbDbStatus :  (ResourceState<Boolean>?) -> Unit = {}){
        onNotifyMovieList = cbMovie
        onNotifyDbError = cbDbStatus }
    fun removeListener(){ onNotifyMovieList = {}; onNotifyDbError = {}}

    val movieLiveData = Transformations.switchMap(movieUseCase.currentData) { it ->
        val livedata = MutableLiveData<Event<ResourceState<List<MovieItem>>>>()
        it.contentIfNotHandled?.let {
            if(it is ResourceState.Success){
                // safe to data base
                livedata.value = Event(ResourceState.Success(it.body.items))
                updateMovieOfflineLiveData
            } else {
                livedata.value = Event(ResourceState.Failure(Throwable("Api Call Fail, Please check internet")))
            }
        }
        livedata
    }
    fun fetchMovieData(){ movieUseCase.setup(null) }

    val offlineMovieLiveData = getOfflineMovieListUseCase.currentData
    fun fetchOfflineMovieList() = getOfflineMovieListUseCase.setup(null)

    val updateMovieOfflineLiveData = movieOfflineUpdateUseCase.currentData
    fun updateMovieOfflineLiveData(list : List<MovieItem>) = movieOfflineUpdateUseCase.setup(list)


    init {
        movieLiveData.observeForever{ onNotifyMovieList(it.contentIfNotHandled) }
        offlineMovieLiveData.observeForever{ onNotifyMovieList(it.contentIfNotHandled) }
        updateMovieOfflineLiveData.observeForever{ onNotifyDbError(it.contentIfNotHandled) }
    }

    fun onDestroy(){
        movieUseCase.cancel()
        getOfflineMovieListUseCase.cancel()
        movieOfflineUpdateUseCase
    }

}