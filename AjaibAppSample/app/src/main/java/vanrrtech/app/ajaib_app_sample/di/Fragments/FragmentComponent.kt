package vanrrtech.app.kompasgithubapp.app.DependancyInjenction.Activity

import dagger.Subcomponent
import vanrrtech.app.ajaib_app_sample.features.github.SearchFragment
import vanrrtech.app.ajaib_app_sample.features.github.UserDetailFragment
import vanrrtech.app.ajaib_app_sample.features.github.home.TopFragment

@FragmentScope
@Subcomponent(modules = [
    FragmentModule::class
])
interface FragmentComponent {

    fun inject(fragment: TopFragment)
    fun inject(fragment: SearchFragment)
    fun inject(fragment: UserDetailFragment)

}