package vanrrtech.app.prodiaappsample.features.github.home

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.result.ActivityResult
import androidx.appcompat.app.AppCompatActivity
import vanrrtech.app.prodiaappsample.base_components.base_classes.BaseActivity
import vanrrtech.app.prodiaappsample.databinding.TopActivityLayoutBinding

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