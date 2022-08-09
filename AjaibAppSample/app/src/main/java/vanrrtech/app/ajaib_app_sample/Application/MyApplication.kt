package vanrrtech.app.ajaib_app_sample.Application

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import vanrrtech.app.ajaib_app_sample.di.App.AppComponent
import vanrrtech.app.ajaib_app_sample.di.App.AppModule
import vanrrtech.app.ajaib_app_sample.di.App.DaggerAppComponent

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