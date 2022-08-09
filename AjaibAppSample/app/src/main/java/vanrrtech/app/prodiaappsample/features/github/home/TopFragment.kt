package vanrrtech.app.prodiaappsample.features.github.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.navigation.fragment.findNavController
import vanrrtech.app.prodiaappsample.R
import vanrrtech.app.prodiaappsample.base_components.base_classes.BaseFragment
import vanrrtech.app.prodiaappsample.base_components.extensions.findNullableNavController
import vanrrtech.app.prodiaappsample.databinding.TopFragmentLayoutBinding

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