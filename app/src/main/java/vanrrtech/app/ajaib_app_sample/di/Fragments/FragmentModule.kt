package vanrrtech.app.ajaib_app_sample.di.Fragments

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import dagger.Module
import dagger.Provides
import vanrrtech.app.ajaib_app_sample.di.Activity.ViewModelProducer.VmFactory
import vanrrtech.app.ajaib_app_sample.domain.UseCases.imdb.GetMovieListUseCase
import vanrrtech.app.ajaib_app_sample.domain.UseCases.imdb.GetOfflineMovieListUseCase
import vanrrtech.app.ajaib_app_sample.domain.UseCases.imdb.MovieOfflineUpdateUseCase
import vanrrtech.app.ajaib_app_sample.features.Imdb.MovieListModel
import vanrrtech.app.ajaib_app_sample.features.Imdb.MovieListPresenter
import vanrrtech.app.ajaib_app_sample.features.github.SearchFragmentVm
import vanrrtech.app.ajaib_app_sample.features.github.UserDetailFragmentVm

@Module
class FragmentModule (val fragment : Fragment) {

    @JvmName("getFragment1")
    @Provides
    fun getFragment() : Fragment = fragment

    @Provides
    fun getSearchViewModel(fragment: Fragment, viewModelFactory: VmFactory) : SearchFragmentVm =
        ViewModelProvider(fragment, viewModelFactory)
            .get(SearchFragmentVm::class.java)

    @Provides
    fun getUserDetailViewModel(fragment: Fragment, viewModelFactory: VmFactory) : UserDetailFragmentVm =
        ViewModelProvider(fragment, viewModelFactory)
            .get(UserDetailFragmentVm::class.java)

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