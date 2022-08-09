package vanrrtech.app.kompasgithubapp.app.DependancyInjenction.Activity

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import dagger.Module
import dagger.Provides
import vanrrtech.app.prodiaappsample.di.Activity.ViewModelProducer.VmFactory
import vanrrtech.app.prodiaappsample.features.github.SearchFragmentVm
import vanrrtech.app.prodiaappsample.features.github.UserDetailFragmentVm

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

}