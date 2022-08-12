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
import vanrrtech.app.ajaib_app_sample.base_components.extensions.*
import vanrrtech.app.ajaib_app_sample.databinding.DetailMovieFragmentBinding
import vanrrtech.app.ajaib_app_sample.domain.data_model.imdb.MovieItem
import javax.inject.Inject

class MovieDetailFragment : BaseFragment<DetailMovieFragmentBinding>() {

    companion object{
        val PARAM_ARG = "param_args"
    }

    var movieListAdapter : MovieListAdapter? = null

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
    }

    fun initUi(){
        // since this UI is simple i'm not using any presenter . . .
        arguments?.getSerializable(PARAM_ARG)?.let { data ->
            if(!( data is MovieItem))
                return@let
            withBinding {
                detailPosterIv.loadImage(data.image)
                idTv.text = "Movie id ${data.id}"
                rankTv.text = "rank ${data.rank}"
                fulltitleTv.text = data.fullTitle
                yearTv.text = "Year ${data.year}"
                crewTv.text = "Crew ${data.crew}"
                imdbRating.text = "imdb rating ${data.imDbRating}"
                imdbRatingCount.text = "imdb rating ${data.imDbRatingCount}"
            }
        }
    }
}