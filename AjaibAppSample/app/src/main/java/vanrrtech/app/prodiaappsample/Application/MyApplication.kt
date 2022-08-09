package vanrrtech.app.prodiaappsample.Application

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import vanrrtech.app.kompasgithubapp.app.DependancyInjenction.AppComponent
import vanrrtech.app.kompasgithubapp.app.DependancyInjenction.AppModule
import vanrrtech.app.kompasgithubapp.app.DependancyInjenction.DaggerAppComponent

class MyApplication : Application() {
    val myAppComponent : AppComponent by lazy {
            DaggerAppComponent.builder().appModule(AppModule(this)).build()
    }

    override fun onCreate() {
        super.onCreate()
        context = this.applicationContext
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        private var context: Context? = null
        val appContext: Context?
            get() = context
    }

}