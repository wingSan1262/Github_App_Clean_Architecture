package vanrrtech.app.ajaib_app_sample.di.App

import dagger.Component
import vanrrtech.app.ajaib_app_sample.di.Activity.ActivityComponent
import vanrrtech.app.ajaib_app_sample.di.Activity.ActivityModule

@AppScope
@Component(modules = [
    AppModule::class,
    UseCasesModules::class
])
interface AppComponent {
    fun newActivityComponent (activityModule: ActivityModule) : ActivityComponent
}