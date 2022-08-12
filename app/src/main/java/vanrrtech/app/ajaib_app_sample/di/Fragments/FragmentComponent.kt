package vanrrtech.app.ajaib_app_sample.di.Fragments

import dagger.Subcomponent
import vanrrtech.app.ajaib_app_sample.features.Imdb.MovieDetailFragment
import vanrrtech.app.ajaib_app_sample.features.Imdb.MovieListFragment
import vanrrtech.app.ajaib_app_sample.features.home.TopFragment

@FragmentScope
@Subcomponent(modules = [
    FragmentModule::class
])
interface FragmentComponent {

    fun inject(fragment: TopFragment)
    fun inject(fragment: MovieListFragment)
    fun inject(fragment: MovieDetailFragment)

}