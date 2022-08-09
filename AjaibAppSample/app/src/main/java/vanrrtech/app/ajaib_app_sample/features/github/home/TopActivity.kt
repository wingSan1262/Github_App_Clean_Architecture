package vanrrtech.app.ajaib_app_sample.features.github.home

import android.content.pm.ActivityInfo
import android.os.Bundle
import vanrrtech.app.ajaib_app_sample.base_components.base_classes.BaseActivity
import vanrrtech.app.ajaib_app_sample.databinding.TopActivityLayoutBinding

class TopActivity : BaseActivity<TopActivityLayoutBinding>() {

    val topFragment = TopFragment()
    override fun onCreate(savedInstanceState: Bundle?) {
        activityComponent.inject(this)
        initUi()
        super.onCreate(savedInstanceState)
        supportFragmentManager.beginTransaction().apply {
            add(viewBinding.fragmentContainer.id, topFragment)
            commit()
        }
    }

    override fun onResume() {
        super.onResume()
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }

    fun initUi(){
        bindThisView(this, layoutInflater, null)
    }

    override fun onBackPressed() {
        topFragment.onBackPressed()
    }


}