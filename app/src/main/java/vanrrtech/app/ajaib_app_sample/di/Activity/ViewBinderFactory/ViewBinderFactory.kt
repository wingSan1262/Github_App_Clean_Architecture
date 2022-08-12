package vanrrtech.app.ajaib_app_sample.di.Activity.ViewBinderFactory

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import vanrrtech.app.ajaib_app_sample.databinding.*
import vanrrtech.app.ajaib_app_sample.features.Imdb.MovieDetailFragment
import vanrrtech.app.ajaib_app_sample.features.Imdb.MovieListFragment
import vanrrtech.app.ajaib_app_sample.features.home.TopActivity
import vanrrtech.app.ajaib_app_sample.features.home.TopFragment
class ViewBinderFactory() {

    fun <S> bindViewFragment(modelClass : Class<S>,
                             fragment : Fragment,
                             inflater: LayoutInflater,
                             container : ViewGroup) {
        when (modelClass){
            TopFragment::class.java -> {
                (fragment as TopFragment).viewBinding = TopFragmentLayoutBinding.inflate(inflater,
                    container,
                    false
                )
            }
            MovieListFragment::class.java -> {
                (fragment as MovieListFragment).viewBinding = MovieListFragmentBinding.inflate(inflater,
                    container,
                    false
                )
            }

            MovieDetailFragment::class.java -> {
                (fragment as MovieDetailFragment).viewBinding = DetailMovieFragmentBinding.inflate(inflater,
                    container,
                    false
                )
            }

            else -> { throw Exception("no vb class found")}
        }
    }

    fun <S> bindViewActivity(modelClass : Class<S>,
                             activity : AppCompatActivity,
                             inflater: LayoutInflater) {
        when (modelClass){
            TopActivity::class.java -> {
                (activity as TopActivity).viewBinding = TopActivityLayoutBinding.inflate(inflater,
                    null,
                    false
                )
            }

            else -> { throw Exception("no vb class found")}
        }
    }
}