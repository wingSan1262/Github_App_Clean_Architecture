package vanrrtech.app.ajaib_app_sample.di.Fragments

import dagger.Subcomponent
import vanrrtech.app.ajaib_app_sample.di.Fragments.FragmentModule
import vanrrtech.app.ajaib_app_sample.di.Fragments.FragmentScope
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