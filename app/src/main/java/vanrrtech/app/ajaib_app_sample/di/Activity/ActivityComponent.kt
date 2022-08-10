package vanrrtech.app.ajaib_app_sample.di.Activity

import dagger.Subcomponent
import vanrrtech.app.ajaib_app_sample.features.github.home.TopActivity
import vanrrtech.app.ajaib_app_sample.di.Fragments.FragmentComponent
import vanrrtech.app.ajaib_app_sample.di.Fragments.FragmentModule

@ActivityScope
@Subcomponent(modules = [ActivityModule::class])
interface ActivityComponent {

    fun newFragmentComponent (fragmentModule: FragmentModule) : FragmentComponent
    fun inject(context: TopActivity)

}