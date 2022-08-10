package vanrrtech.app.ajaib_app_sample.di.Activity.ViewBinderFactory

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import vanrrtech.app.ajaib_app_sample.databinding.*
import vanrrtech.app.ajaib_app_sample.features.github.home.TopActivity
import vanrrtech.app.ajaib_app_sample.features.github.SearchFragment
import vanrrtech.app.ajaib_app_sample.features.github.UserDetailFragment
import vanrrtech.app.ajaib_app_sample.features.github.home.TopFragment
class ViewBinderFactory() {

    fun <S> bindViewFragment(modelClass : Class<S>,
                             fragment : Fragment,
                             inflater: LayoutInflater,
                             container : ViewGroup) {
        when (modelClass){
            TopFragment::class.java -> {
                (fragment as TopFragment).viewBinding = TopFragmentLayoutBinding.inflate(inflater,
                    container,
                    false
                )
            }
            SearchFragment::class.java -> {
                (fragment as SearchFragment).viewBinding = SearchUserGithubFragmentBinding.inflate(inflater,
                    container,
                    false
                )
            }
            UserDetailFragment::class.java -> {
                (fragment as UserDetailFragment).viewBinding = UserDetailFragmentBinding.inflate(inflater,
                    container,
                    false
                )
            }

            else -> { throw Exception("no vb class found")}
        }
    }

    fun <S> bindViewActivity(modelClass : Class<S>,
                             activity : AppCompatActivity,
                             inflater: LayoutInflater) {
        when (modelClass){
            TopActivity::class.java -> {
                (activity as TopActivity).viewBinding = TopActivityLayoutBinding.inflate(inflater,
                    null,
                    false
                )
            }

            else -> { throw Exception("no vb class found")}
        }
    }
}