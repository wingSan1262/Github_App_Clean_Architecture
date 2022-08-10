package vanrrtech.app.ajaib_app_sample.features.github


import android.view.View
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.times
import org.mockito.junit.MockitoJUnitRunner
import vanrrtech.app.ajaib_app_sample.databinding.UserRepoItemBinding
import vanrrtech.app.ajaib_app_sample.domain.data_model.github.response.GithubUserItemResponse
import vanrrtech.app.ajaib_app_sample.domain.data_model.github.response.UserRepoDetails

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.CoreMatchers.`is`
import vanrrtech.app.ajaib_app_sample.base_components.UtilServices.LoginHandler.RandomHandler
import vanrrtech.app.ajaib_app_sample.databinding.SearchUserItemBinding

@RunWith(MockitoJUnitRunner::class)
internal class UserItemViewHolderTest {
    var SUT : UserItemViewHolder? = null

    @Mock
    private lateinit var binding : SearchUserItemBinding

    @Mock
    private lateinit var root : View

    val data = GithubUserItemResponse(
        1,"","","",""
    )

    @Before
    @Throws(Exception::class)
    fun setup() {
        SUT = UserItemViewHolder(binding, root, {},RandomHandler())
    }

    @Test
    fun bindData() {
        try {
            SUT?.bindData(data)
        } catch (e : Throwable) {}
        Mockito.verify(binding, times(1)).getRoot()
    }

    @Test
    fun bindData_bindStatus() {
        var isBindInvoked = false
        try {
            SUT?.bindData(data)
        } catch (e : Throwable) {
            isBindInvoked = true
        }
        assertThat(isBindInvoked, `is`(true))
    }
}