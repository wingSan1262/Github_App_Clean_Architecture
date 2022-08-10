package vanrrtech.app.ajaib_app_sample.di.Activity.ViewModelProducer

import android.app.Application
import androidx.lifecycle.ViewModel

import androidx.lifecycle.ViewModelProvider
import vanrrtech.app.ajaib_app_sample.domain.UseCases.github.*
import vanrrtech.app.ajaib_app_sample.features.github.SearchFragmentVm
import vanrrtech.app.ajaib_app_sample.features.github.UserDetailFragmentVm


class VmFactory(
    val mApplication: Application,
    val githubUserListUseCase: GetGithubUserListUseCase,
    val getOfflineGithubUserListUseCase: GetOfflineGithubUserListUseCase,
    val updateOfflineGithubUserListUseCase: UpdateOfflineGithubUserListUseCase,
    val searchUserGithubUseCase: SearchUserGithubUseCase,
    val getGithubUserDetails: GetGithubUserDetails,
    val getGithubUserRepoContent: GetGithubUserRepoContentUseCase
) :
    ViewModelProvider.Factory {

    fun <T : ViewModel?> getClassInstance(modelClass: Class<T>): T {
        when(modelClass){

            SearchFragmentVm::class.java -> {
                return modelClass.getConstructor(
                    GetGithubUserListUseCase::class.java,
                    GetOfflineGithubUserListUseCase::class.java,
                    UpdateOfflineGithubUserListUseCase::class.java,
                    SearchUserGithubUseCase::class.java
                ).newInstance(
                    githubUserListUseCase,
                    getOfflineGithubUserListUseCase,
                    updateOfflineGithubUserListUseCase,
                    searchUserGithubUseCase
                ) as T
            }
            UserDetailFragmentVm::class.java -> {
                return modelClass.getConstructor(
                    GetGithubUserDetails::class.java,
                    GetGithubUserRepoContentUseCase::class.java
                ).newInstance(
                    getGithubUserDetails,
                    getGithubUserRepoContent
                ) as T
            }
            else -> {
                throw Exception("no vm found")
            }
        }
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return getClassInstance(modelClass)
    }

}