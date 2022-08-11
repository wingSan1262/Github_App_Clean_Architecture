package vanrrtech.app.ajaib_app_sample.di.App

import android.app.Application
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import vanrrtech.app.ajaib_app_sample.di.Activity.ViewBinderFactory.ViewBinderFactory
import vanrrtech.app.ajaib_app_sample.di.Activity.ViewModelProducer.VmFactory
import vanrrtech.app.ajaib_app_sample.data.remote_repository.RemoteApiRetrofitClient
import vanrrtech.app.ajaib_app_sample.data.SQDb.AppDb
import vanrrtech.app.ajaib_app_sample.data.SQDb.github.UserListDao
import vanrrtech.app.ajaib_app_sample.data.SQDb.imdb.MovieListDao
import vanrrtech.app.ajaib_app_sample.domain.UseCases.github.*

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
    fun getUserListDataDB(application: Application): AppDb {
        return AppDb.getInstance(application.applicationContext)
    }

    @Provides
    @AppScope
    fun getUserListDataDBDao(db : AppDb): UserListDao {
        return db.userItemDao()
    }

    @Provides
    @AppScope
    fun getMovieListDao(db : AppDb): MovieListDao {
        return db.movieDao()
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