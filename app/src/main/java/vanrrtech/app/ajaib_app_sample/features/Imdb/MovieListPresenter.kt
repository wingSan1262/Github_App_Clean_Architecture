package vanrrtech.app.ajaib_app_sample.features.Imdb

import vanrrtech.app.ajaib_app_sample.base_components.base_interface.BaseModel
import vanrrtech.app.ajaib_app_sample.base_components.entities.Event
import vanrrtech.app.ajaib_app_sample.base_components.entities.ResourceState
import vanrrtech.app.ajaib_app_sample.domain.data_model.imdb.MovieItem

class MovieListPresenter(
    private val model : MovieListModel
) {
    var currentPage = 0
    var isOnline = true
    var isQuerying = true
    var viewCallback : MovieListViewCallback? = null
    fun setListener(cb: MovieListViewCallback){ viewCallback = cb }
    fun removeListener(){viewCallback = null}

    fun loadMore(){
        if(!isQuerying){
            currentPage += 1
            if(((currentPage+1) * 5)-1 >= 250){
                currentPage -= 1
            }
            isQuerying = true
            model.fetchOfflineMovieList()
        }
    }

    fun onScreenStart(){
        currentPage = 0
        viewCallback?.showLoading?.invoke()
        model.setListener(
            cbMovie = {
                it?.let {
                    if(it is ResourceState.Success){
                        if(it.body.isEmpty()){
                            viewCallback?.showError?.invoke("no movie"); return@setListener
                        }
                        if(currentPage == 0) {
                            if(isOnline) {model.updateMovieOfflineLiveData(it.body)}
                        }
                        viewCallback?.showListVideo?.invoke(
                            // TODO pagination limitaion . . ., api do not provide pagination
                            it.body.subList(
                                currentPage * 5,
                                ((currentPage+1) * 5),
                            )
                        )
                        return@setListener
                    }
                    /** error **/
                    if(it is ResourceState.Failure){
                        isOnline = false
                        model.fetchOfflineMovieList()
                        viewCallback?.showError?.invoke(it.exception.message.toString())
                        return@setListener
                    }
                    viewCallback?.showError?.invoke("Please check your internet") } ?: kotlin.run {
                    viewCallback?.showError?.invoke("Please check your internet")
                }
             }
            ,
            cbDbStatus = {
                it?.let {
                    if(it is ResourceState.Failure){
                        viewCallback?.showError?.invoke(it.exception.message.toString())
                        return@setListener
                    }
                } ?: kotlin.run { viewCallback?.showError?.invoke("Please check your internet")  }
            }
        )
        model.fetchMovieData()
    }

    fun onDestroy(){
        model.removeListener()
        model.onDestroy()
    }

}

data class MovieListViewCallback(
    val showLoading : ()->Unit={},
    val showError : (String)->Unit={},
    val showListVideo : (List<MovieItem>) -> Unit ={}
)