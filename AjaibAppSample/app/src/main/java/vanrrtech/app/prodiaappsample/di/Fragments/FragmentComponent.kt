package vanrrtech.app.kompasgithubapp.app.DependancyInjenction.Activity

import dagger.Subcomponent
import vanrrtech.app.prodiaappsample.features.github.SearchFragment
import vanrrtech.app.prodiaappsample.features.github.UserDetailFragment
import vanrrtech.app.prodiaappsample.features.github.home.TopFragment

@FragmentScope
@Subcomponent(modules = [
    FragmentModule::class
])
interface FragmentComponent {

    fun inject(fragment: TopFragment)
    fun inject(fragment: SearchFragment)
    fun inject(fragment: UserDetailFragment)

}