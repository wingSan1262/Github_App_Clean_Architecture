package vanrrtech.app.prodiaappsample.di.App

import dagger.Module
import dagger.Provides
import vanrrtech.app.kompasgithubapp.app.DependancyInjenction.AppScope
import vanrrtech.app.prodiaappsample.data.SQDb.github.UserListDao
import vanrrtech.app.prodiaappsample.data.remote_repository.RemoteApiRetrofitClient
import vanrrtech.app.prodiaappsample.domain.UseCases.github.*

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
}