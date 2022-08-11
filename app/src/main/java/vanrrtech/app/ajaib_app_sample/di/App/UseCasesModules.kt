package vanrrtech.app.ajaib_app_sample.di.App

import dagger.Module
import dagger.Provides
import vanrrtech.app.ajaib_app_sample.data.SQDb.github.UserListDao
import vanrrtech.app.ajaib_app_sample.data.SQDb.imdb.MovieListDao
import vanrrtech.app.ajaib_app_sample.data.remote_repository.RemoteApiRetrofitClient
import vanrrtech.app.ajaib_app_sample.domain.UseCases.github.*
import vanrrtech.app.ajaib_app_sample.domain.UseCases.imdb.GetMovieListUseCase
import vanrrtech.app.ajaib_app_sample.domain.UseCases.imdb.GetOfflineMovieListUseCase
import vanrrtech.app.ajaib_app_sample.domain.UseCases.imdb.MovieOfflineUpdateUseCase

@Module
class UseCasesModules() {

    @Provides
    @AppScope
    fun getGithubUsers(myApi : RemoteApiRetrofitClient) : GetGithubUserListUseCase =
        GetGithubUserListUseCase(myApi)

    @Provides
    @AppScope
    fun updateOfflineGithubUsers(myApi : UserListDao) : UpdateOfflineGithubUserListUseCase =
        UpdateOfflineGithubUserListUseCase(myApi)

    @Provides
    @AppScope
    fun getOfflineGithubUsers(myApi : UserListDao) : GetOfflineGithubUserListUseCase =
        GetOfflineGithubUserListUseCase(myApi)

    @Provides
    @AppScope
    fun searchUser(myApi : RemoteApiRetrofitClient) : SearchUserGithubUseCase =
        SearchUserGithubUseCase(myApi)

    @Provides
    @AppScope
    fun userDetailUseCase(myApi : RemoteApiRetrofitClient) : GetGithubUserDetails =
        GetGithubUserDetails(myApi)

    @Provides
    @AppScope
    fun userRepoUseCase(myApi : RemoteApiRetrofitClient) : GetGithubUserRepoContentUseCase =
        GetGithubUserRepoContentUseCase(myApi)

    @Provides
    @AppScope
    fun movieListUseCase(myApi : RemoteApiRetrofitClient) : GetMovieListUseCase =
        GetMovieListUseCase(myApi)

    @Provides
    @AppScope
    fun getOfflineMovieListUseCase(myApi : MovieListDao) : GetOfflineMovieListUseCase =
        GetOfflineMovieListUseCase(myApi)

    @Provides
    @AppScope
    fun movieOfflineUpdateUseCase(myApi : MovieListDao) : MovieOfflineUpdateUseCase =
        MovieOfflineUpdateUseCase(myApi)

}