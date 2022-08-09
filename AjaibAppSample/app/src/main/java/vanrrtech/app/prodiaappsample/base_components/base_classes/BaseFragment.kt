package vanrrtech.app.prodiaappsample.base_components.base_classes

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import vanrrtech.app.kompasgithubapp.app.DependancyInjenction.Activity.FragmentModule
import vanrrtech.app.prodiaappsample.di.Activity.ViewModelProducer.VmFactory
import vanrrtech.app.prodiaappsample.features.github.SearchFragmentVm
import vanrrtech.app.prodiaappsample.features.github.UserDetailFragmentVm
import vanrrtech.app.prodiaappsample.features.github.home.TopActivity
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


    /** Common View model **/
    @Inject
    lateinit var searchViewModel: SearchFragmentVm
    @Inject
    lateinit var userDetailViewModel: UserDetailFragmentVm


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