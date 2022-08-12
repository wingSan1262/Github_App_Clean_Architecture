package vanrrtech.app.ajaib_app_sample.retrofit_payloads_check


import android.R
import android.content.res.Resources
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.CoreMatchers.`is`
import vanrrtech.app.ajaib_app_sample.data.remote_repository.ImdbApiInterface


@RunWith(MockitoJUnitRunner::class)

class ApiPayloadCheckImdb {

    lateinit var SUT: ImdbApiInterface
    private val mockWebServer = MockWebServer()
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    val dispatcher = TestCoroutineDispatcher()

    @Before
    @Throws(Exception::class)
    fun setup() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(dispatcher)

        val client = OkHttpClient.Builder()
            .connectTimeout(1, TimeUnit.SECONDS)
            .readTimeout(1, TimeUnit.SECONDS)
            .writeTimeout(1, TimeUnit.SECONDS)
            .build()

        SUT = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ImdbApiInterface::class.java)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun testUserList_success(){
        mockWebServer.enqueue(
            MockResponse().also {
                it.setBody(PayloadImdb.MOVIE_MOCK_RESPONSE)
                it.setResponseCode(200)
            }
        )
        runTest {
            SUT.getAllMovieList().items.run {
                assertThat(get(0).id, `is`("tt0111161"))
                assertThat(get(0).rank, `is`("1"))
                assertThat(get(0).title, `is`("TheShawshankRedemption"))
                assertThat(get(0).fullTitle, `is`("TheShawshankRedemption(1994)"))
                assertThat(get(0).year, `is`("1994"))
                assertThat(get(0).image, `is`("https://m.media-amazon.com/images/M/MV5BMDFkYTc0MGEtZmNhMC00ZDIzLWFmNTEtODM1ZmRlYWMwMWFmXkEyXkFqcGdeQXVyMTMxODk2OTU@._V1_UX128_CR0,12,128,176_AL_.jpg"))
                assertThat(get(0).crew, `is`("FrankDarabont(dir.),TimRobbins,MorganFreeman"))
                assertThat(get(0).imDbRating, `is`("9.2"))
                assertThat(get(0).imDbRatingCount, `is`("2623078"))
            }
            advanceUntilIdle()
        }
    }

}

class PayloadImdb{
    companion object{
        val MOVIE_MOCK_RESPONSE = "{\"items\":[{\"id\":\"tt0111161\",\"rank\":\"1\",\"title\":\"TheShawshankRedemption\",\"fullTitle\":\"TheShawshankRedemption(1994)\",\"year\":\"1994\",\"image\":\"https://m.media-amazon.com/images/M/MV5BMDFkYTc0MGEtZmNhMC00ZDIzLWFmNTEtODM1ZmRlYWMwMWFmXkEyXkFqcGdeQXVyMTMxODk2OTU@._V1_UX128_CR0,12,128,176_AL_.jpg\",\"crew\":\"FrankDarabont(dir.),TimRobbins,MorganFreeman\",\"imDbRating\":\"9.2\",\"imDbRatingCount\":\"2623078\"},{\"id\":\"tt0068646\",\"rank\":\"2\",\"title\":\"TheGodfather\",\"fullTitle\":\"TheGodfather(1972)\",\"year\":\"1972\",\"image\":\"https://m.media-amazon.com/images/M/MV5BM2MyNjYxNmUtYTAwNi00MTYxLWJmNWYtYzZlODY3ZTk3OTFlXkEyXkFqcGdeQXVyNzkwMjQ5NzM@._V1_UX128_CR0,12,128,176_AL_.jpg\",\"crew\":\"FrancisFordCoppola(dir.),MarlonBrando,AlPacino\",\"imDbRating\":\"9.2\",\"imDbRatingCount\":\"1816744\"}],\"errorMessage\":\"\"}"
    }
}

