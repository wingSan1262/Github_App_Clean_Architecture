package vanrrtech.app.ajaib_app_sample.base_components.base_classes

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import vanrrtech.app.kompasgithubapp.app.DependancyInjenction.Activity.ActivityComponent
import vanrrtech.app.kompasgithubapp.app.DependancyInjenction.Activity.ActivityModule
import vanrrtech.app.kompasgithubapp.app.DependancyInjenction.AppComponent
import vanrrtech.app.ajaib_app_sample.Application.MyApplication
import vanrrtech.app.ajaib_app_sample.di.Activity.ViewBinderFactory.ViewBinderFactory
import vanrrtech.app.ajaib_app_sample.base_components.UtilServices.KeyboardDismisser
import vanrrtech.app.ajaib_app_sample.base_components.UtilServices.LoginHandler.RandomHandler
import vanrrtech.app.ajaib_app_sample.base_components.UtilServices.snack_bar_handler.SnackBarHandler
import javax.inject.Inject

abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity() {

    /** Scoping injection tools **/
    val appComponent : AppComponent by lazy { (application as MyApplication).myAppComponent}
    val activityComponent : ActivityComponent by lazy {
        appComponent.newActivityComponent(ActivityModule(this, this))
    }

    /**
     * Common Injection Service Component, add here if needed
     */
    @Inject lateinit var loginHandler : RandomHandler
    @Inject lateinit var viewBinderFactory: ViewBinderFactory
    @Inject lateinit var keyboardDismisser: KeyboardDismisser
    fun dismissKey(){ keyboardDismisser.dismissSoftKey() }
    val snackBarHandler by lazy { SnackBarHandler(viewBinding.root)}

    /** Common View Binding Operation **/
    lateinit var viewBinding : VB
    fun <T>bindThisView(host: T, layoutInflater: LayoutInflater, container: ViewGroup?){
        viewBinderFactory.bindViewActivity(host!!::class.java, this, layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)
    }

}