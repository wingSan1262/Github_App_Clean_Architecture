package vanrrtech.app.ajaib_app_sample.base_components.base_classes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import vanrrtech.app.ajaib_app_sample.di.Fragments.FragmentModule
import vanrrtech.app.ajaib_app_sample.features.home.TopActivity
import javax.inject.Inject

abstract class BaseFragment<VB : ViewBinding> : Fragment() {

    /** Scoping Injector Component **/
    val activityComponent by lazy {(this.activity as BaseActivity<*>).activityComponent}
    val fragmentComponent by lazy {
        activityComponent.newFragmentComponent(FragmentModule(this))
    }

    /** Host Activity Reference **/
    val hostActivity by lazy {this.activity as TopActivity }

    val loginHandler by lazy { hostActivity.loginHandler }
    val snackBarHandler by lazy { hostActivity.snackBarHandler }
    val viewBinderFactory by lazy { hostActivity.viewBinderFactory }
    fun dismissKey(){ hostActivity.dismissKey()}


    /** View binding common **/
    lateinit var viewBinding : VB
    fun <T>bindThisView(modelClass : T, layoutInflater: LayoutInflater, container: ViewGroup){
        viewBinderFactory.bindViewFragment(modelClass!!::class.java, this, layoutInflater, container)
    }
    fun withBinding(block: (VB.() -> Unit)): VB{
        val bindingAfterRunning: VB = viewBinding.apply { block.invoke(this) }
        return bindingAfterRunning
    }

}