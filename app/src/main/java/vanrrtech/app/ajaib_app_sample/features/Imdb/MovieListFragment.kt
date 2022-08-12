package vanrrtech.app.ajaib_app_sample.features.Imdb

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
import vanrrtech.app.ajaib_app_sample.databinding.MovieListFragmentBinding
import vanrrtech.app.ajaib_app_sample.domain.data_model.imdb.MovieItem
import javax.inject.Inject

class MovieListFragment : BaseFragment<MovieListFragmentBinding>() {

    var movieListAdapter : MovieListAdapter? = null
    @Inject
    lateinit var presenter: MovieListPresenter

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
        presenter.onScreenStart()
    }

    override fun onDestroy() {
        presenter.removeListener()
        presenter.onDestroy()
        super.onDestroy()
    }
    fun initUi(){
        presenter.setListener(
            MovieListViewCallback(
                showLoading = { showLoading() },
                showError = {
                    snackBarHandler.showSnackBar(it)
                    presenter.isQuerying = false
                },
                showListVideo = {
                    updateList(it)
                }
            )
        )
        withBinding {
            if(movieListAdapter == null) {
                movieListAdapter = MovieListAdapter{
                    it as MovieItem
                    findNullableNavController()?.navigateSafe(
                        R.id.movieList_to_movieDetail,
                        bundleOf(
                            MovieDetailFragment.PARAM_ARG to it
                        )
                    )
                }
            }
            movieRv.let {
                it.layoutManager = LinearLayoutManager(context,
                    LinearLayoutManager.VERTICAL,
                    false)
                it.isNestedScrollingEnabled = false
                it.adapter = movieListAdapter
            }
        }
        setOnLoadMore()
    }

    fun setOnLoadMore(){
        withBinding {
            movieRv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    (recyclerView.layoutManager as? LinearLayoutManager)?.let {
                        if (dy >= 0 && !presenter.isQuerying) {
                            val visibleItemCount = it.childCount
                            val totalItemCount = it.itemCount
                            val pastVisibleItems = it.findFirstVisibleItemPosition()
                            if (visibleItemCount + pastVisibleItems >= totalItemCount) {
                                showLoading()
                                lifecycleScope.launch {
                                    delay(1000)
                                    presenter.loadMore()
                                }
                            }
                        }
                    }
                }
            })
        }
    }

    fun showLoading(){
        lifecycleScope.launch{
            withBinding {
                swipeRefresh.isRefreshing = true
             }
            delay(1000)
            withBinding {
                swipeRefresh.isRefreshing = false
            }
        }
    }
    fun updateList(items: List<MovieItem>){
        lifecycleScope.launch {
            if(items.isEmpty()){
                snackBarHandler.showSnackBar("no movie found here sorry . . .")
                presenter.isQuerying = false
                return@launch
            }
            if(presenter.currentPage == 0){movieListAdapter?.clearList()}
            if(presenter.currentPage <= 0){
                items.asReversed().forEach {
                    delay(70)
                    movieListAdapter?.insertAtTop(it)
                    withBinding { movieRv.smoothScrollToPosition(0) }
                }
            } else {
                movieListAdapter?.insertAllAtBottom(items)
            }
            delay(500)
            presenter.isQuerying = false
        }
    }
}