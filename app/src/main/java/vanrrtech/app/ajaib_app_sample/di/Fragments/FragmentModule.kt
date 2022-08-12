package vanrrtech.app.ajaib_app_sample.di.Fragments

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import dagger.Module
import dagger.Provides
import vanrrtech.app.ajaib_app_sample.domain.UseCases.imdb.GetMovieListUseCase
import vanrrtech.app.ajaib_app_sample.domain.UseCases.imdb.GetOfflineMovieListUseCase
import vanrrtech.app.ajaib_app_sample.domain.UseCases.imdb.MovieOfflineUpdateUseCase
import vanrrtech.app.ajaib_app_sample.features.Imdb.MovieListModel
import vanrrtech.app.ajaib_app_sample.features.Imdb.MovieListPresenter

@Module
class FragmentModule (val fragment : Fragment) {

    @JvmName("getFragment1")
    @Provides
    fun getFragment() : Fragment = fragment

    @Provides
    fun getMovieListModelClass(
        movieUseCase: GetMovieListUseCase,
        getOfflineMovieListUseCase: GetOfflineMovieListUseCase,
        movieOfflineUpdateUseCase: MovieOfflineUpdateUseCase,
    ) : MovieListModel = MovieListModel(
        movieUseCase, getOfflineMovieListUseCase, movieOfflineUpdateUseCase)

    @Provides
    fun movieListPresenter(
        model : MovieListModel
    ) : MovieListPresenter = MovieListPresenter(model)

}