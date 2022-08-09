package vanrrtech.app.kompasgithubapp.app.DependancyInjenction

import android.app.Application
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import vanrrtech.app.prodiaappsample.di.Activity.ViewBinderFactory.ViewBinderFactory
import vanrrtech.app.prodiaappsample.di.Activity.ViewModelProducer.VmFactory
import vanrrtech.app.prodiaappsample.data.remote_repository.RemoteApiRetrofitClient
import vanrrtech.app.prodiaappsample.data.SQDb.github.GithubUserDb
import vanrrtech.app.prodiaappsample.data.SQDb.github.UserListDao
import vanrrtech.app.prodiaappsample.domain.UseCases.github.*
import java.lang.StringBuilder

@Module
class AppModule(val application: Application) {

    @Provides
    @AppScope
    fun getClientLogger(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Provides
    @AppScope
    fun getOkHttpClientBuilder(logger : HttpLoggingInterceptor): OkHttpClient.Builder {
        return OkHttpClient.Builder().addInterceptor(logger)
    }

    @Provides
    @AppScope
    fun getRetrofitClient(
        logger : HttpLoggingInterceptor,
        okHttpBuilder: OkHttpClient.Builder
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(RemoteApiRetrofitClient.BASE_URL_WEATHER)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpBuilder.build())
            .build()
    }

    @Provides
    @AppScope
    fun getClient(retrofit: Retrofit): RemoteApiRetrofitClient {
        return RemoteApiRetrofitClient(retrofit)
    }

    @Provides
    @AppScope
    fun getUserListDataDB(application: Application): GithubUserDb {
        return GithubUserDb.getInstance(application.applicationContext)
    }

    @Provides
    @AppScope
    fun getUserListDataDBDao(db : GithubUserDb): UserListDao {
        return db.userItemDao()
    }

    @Provides
    fun application() = application


    @Provides
    @AppScope
    fun getVmFactory(mApplication: Application,
                     githubUserListUseCase: GetGithubUserListUseCase,
                     getOfflineGithubUserListUseCase: GetOfflineGithubUserListUseCase,
                     updateOfflineGithubUserListUseCase: UpdateOfflineGithubUserListUseCase,
                     searchUserGithubUseCase: SearchUserGithubUseCase,
                     getGithubUserDetails: GetGithubUserDetails,
                     getGithubUserRepoContent: GetGithubUserRepoContentUseCase
    ) : VmFactory =
        VmFactory(
            mApplication,
            githubUserListUseCase,
            getOfflineGithubUserListUseCase,
            updateOfflineGithubUserListUseCase,
            searchUserGithubUseCase,
            getGithubUserDetails,
            getGithubUserRepoContent
        )


    @Provides
    @AppScope
    fun getViewBinderFactory() : ViewBinderFactory = ViewBinderFactory()



}