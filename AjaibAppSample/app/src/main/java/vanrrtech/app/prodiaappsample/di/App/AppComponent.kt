package vanrrtech.app.kompasgithubapp.app.DependancyInjenction

import dagger.Component
import vanrrtech.app.kompasgithubapp.app.DependancyInjenction.Activity.ActivityComponent
import vanrrtech.app.kompasgithubapp.app.DependancyInjenction.Activity.ActivityModule
import vanrrtech.app.prodiaappsample.di.App.UseCasesModules

@AppScope
@Component(modules = [
    AppModule::class,
    UseCasesModules::class
])
interface AppComponent {
    fun newActivityComponent (activityModule: ActivityModule) : ActivityComponent
}