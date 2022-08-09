package vanrrtech.app.kompasgithubapp.app.DependancyInjenction.Activity

import android.app.Application
import android.content.Context
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import dagger.Module
import dagger.Provides
import vanrrtech.app.ajaib_app_sample.base_components.UtilServices.KeyboardDismisser
import vanrrtech.app.ajaib_app_sample.base_components.UtilServices.LoginHandler.RandomHandler
import vanrrtech.app.ajaib_app_sample.base_components.UtilServices.UnixDateConverter

@Module
class ActivityModule (val activity: AppCompatActivity, val context : Context) {

    @Provides
    fun activity() = activity

    @Provides
    fun context(): Context = context

    @Provides
    fun layoutInflater() = LayoutInflater.from(activity)

    @Provides
    fun fragmentManager() : FragmentManager = activity.supportFragmentManager


    @Provides
    @ActivityScope // dummy to make up content that not available in free API
    fun getRandomGenerator() : RandomHandler = RandomHandler()


    @Provides
    fun getDateConverterService(): UnixDateConverter = UnixDateConverter()

    @Provides
    @ActivityScope
    fun getSoftKeyDismisser(application : Application, activity: AppCompatActivity) =
        KeyboardDismisser(application, activity)
}