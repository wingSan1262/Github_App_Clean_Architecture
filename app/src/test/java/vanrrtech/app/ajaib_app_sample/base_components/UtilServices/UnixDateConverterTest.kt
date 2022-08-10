package vanrrtech.app.ajaib_app_sample.base_components.UtilServices


import android.view.View
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import vanrrtech.app.ajaib_app_sample.domain.data_model.github.response.GithubUserItemResponse

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.CoreMatchers.`is`
import org.junit.After
import org.junit.Rule
import vanrrtech.app.ajaib_app_sample.base_components.entities.ResourceState
import vanrrtech.app.ajaib_app_sample.data.remote_repository.RemoteApiRetrofitClient
import vanrrtech.app.ajaib_app_sample.domain.UseCases.github.GetGithubUserDetails
import vanrrtech.app.ajaib_app_sample.domain.UseCases.github.GetGithubUserRepoContentUseCase
import vanrrtech.app.ajaib_app_sample.domain.data_model.github.request.UserDetailRequest
import vanrrtech.app.ajaib_app_sample.domain.data_model.github.response.UserDetails
import vanrrtech.app.ajaib_app_sample.domain.data_model.github.response.UserRepoDetails
import vanrrtech.app.ajaib_app_sample.features.github.UserDetailFragmentVm
import vanrrtech.app.ajaib_app_sample.features.github.UserItemViewHolder

@RunWith(MockitoJUnitRunner::class)

internal class UnixDateConverterTest{

    lateinit var SUT: UnixDateConverter

    val dispatcher = TestCoroutineDispatcher()

    @Before
    fun setup() {
        SUT = UnixDateConverter()
        SUT.setTestDate("2022-08-10 T10:13:03")
    }

    // TODO please change 'is' value based current time
    @Test
    fun test_getDaysDiff(){
        assertThat(SUT.getDaysBetweenWithCurrent("2022-08-09 T01:17:10"), `is`("1"))
        assertThat(SUT.getDaysBetweenWithCurrent("2022-08-08 T01:17:10"), `is`("2"))
        assertThat(SUT.getDaysBetweenWithCurrent("2022-08-07 T01:17:10"), `is`("3"))
        assertThat(SUT.getDaysBetweenWithCurrent("2022-08-10 T01:17:10"), `is`("0"))
    }

    // TODO please change 'is' value based current time
    @Test
    fun test_getHourDiff(){
        assertThat(SUT.getHourBetweenWithCurrent("2022-08-09 T02:15:00"), `is`("7"))
        assertThat(SUT.getHourBetweenWithCurrent("2022-08-08 T02:15:00"), `is`("7"))
        assertThat(SUT.getHourBetweenWithCurrent("2022-08-07 T02:15:00"), `is`("7"))
        assertThat(SUT.getHourBetweenWithCurrent("2022-08-02 T02:15:00"), `is`("7"))
        assertThat(SUT.getHourBetweenWithCurrent("2022-08-10 T02:15:00"), `is`("7"))
    }

    @Test
    fun test_getSecondDiff(){
        assertThat(SUT.getMinutesBetweenWithCurrent("2022-08-09 T01:15:00"), `is`("58"))
        assertThat(SUT.getMinutesBetweenWithCurrent("2022-08-08 T01:15:00"), `is`("58"))
        assertThat(SUT.getMinutesBetweenWithCurrent("2022-08-07 T01:15:00"), `is`("58"))
        assertThat(SUT.getMinutesBetweenWithCurrent("2022-08-02 T01:15:00"), `is`("58"))
        assertThat(SUT.getMinutesBetweenWithCurrent("2022-08-10 T01:15:00"), `is`("58"))
    }

    @Test
    fun test_failt(){
        if(SUT.getHourBetweenWithCurrent("error") != null) throw Throwable("error hour")
        if(SUT.getMinutesBetweenWithCurrent("error") != null) throw Throwable("error minutes")
        if(SUT.getDaysBetweenWithCurrent("error") != null) throw Throwable("error days")
    }
}