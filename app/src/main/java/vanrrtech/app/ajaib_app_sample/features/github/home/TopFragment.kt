package vanrrtech.app.ajaib_app_sample.features.github.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import vanrrtech.app.ajaib_app_sample.R
import vanrrtech.app.ajaib_app_sample.base_components.base_classes.BaseFragment
import vanrrtech.app.ajaib_app_sample.base_components.extensions.findNullableNavController
import vanrrtech.app.ajaib_app_sample.databinding.TopFragmentLayoutBinding

class TopFragment : BaseFragment<TopFragmentLayoutBinding>() {

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

    fun onBackPressed(){
        val navHost = childFragmentManager.fragments[0]

        if(navHost?.findNullableNavController()?.currentDestination?.id == R.id.searchFragment){
            hostActivity.finish(); return
        }
        navHost?.findNullableNavController()?.navigateUp()
    }
}