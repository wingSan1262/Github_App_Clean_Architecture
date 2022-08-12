package vanrrtech.app.ajaib_app_sample.features.Imdb


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.CoreMatchers.`is`
import org.junit.Rule
import vanrrtech.app.ajaib_app_sample.data.SQDb.imdb.MovieListDao
import vanrrtech.app.ajaib_app_sample.data.remote_repository.RemoteApiRetrofitClient
import vanrrtech.app.ajaib_app_sample.domain.UseCases.imdb.GetMovieListUseCase
import vanrrtech.app.ajaib_app_sample.domain.UseCases.imdb.GetOfflineMovieListUseCase
import vanrrtech.app.ajaib_app_sample.domain.UseCases.imdb.MovieOfflineUpdateUseCase
import vanrrtech.app.ajaib_app_sample.domain.data_model.imdb.MovieItem
import vanrrtech.app.ajaib_app_sample.domain.data_model.imdb.MovieItemResponse

@RunWith(MockitoJUnitRunner::class)

internal class MovieListPresenterTest{
    lateinit var movieUseCase: GetMovieListUseCase
    lateinit var getOfflineMovieListUseCase: GetOfflineMovieListUseCase
    lateinit var movieOfflineUpdateUseCase: MovieOfflineUpdateUseCase
    lateinit var movieListModel: MovieListModel

    @MockK
    lateinit var mRepo: RemoteApiRetrofitClient
    @MockK
    lateinit var mDb: MovieListDao

    lateinit var SUT: MovieListPresenter

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()
    val dispatcher = TestCoroutineDispatcher()


    /** Sorry Write Each Big Unit Test . . . should smaller **/

    @Before
    @Throws(Exception::class)
    fun setup() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(dispatcher)
        movieUseCase = GetMovieListUseCase(mRepo)
        getOfflineMovieListUseCase = GetOfflineMovieListUseCase(mDb)
        movieOfflineUpdateUseCase = MovieOfflineUpdateUseCase(mDb)
        movieListModel = MovieListModel(
            movieUseCase,
            getOfflineMovieListUseCase,
            movieOfflineUpdateUseCase
        )
        SUT = MovieListPresenter(movieListModel)
    }

    @Test
    fun onScreenStart_FetchApi_SuccessAndNotifiedWithCorrectData(){

        /** Arrange **/
        val items = listOf(
            MovieItem("","","","","","","","",""),
            MovieItem("","","","","","","","",""),
            MovieItem("","","","","","","","",""),
            MovieItem("","","","","","","","",""),
            MovieItem("","","","","","","","",""),
            MovieItem("","","","","","","","",""),
            MovieItem("","","","","","","","",""),
            MovieItem("","","","","","","","",""),
            MovieItem("","","","","","","","",""),
            MovieItem("","","","","","","","",""),
            MovieItem("","","","","","","","",""),
            MovieItem("","","","","","","","",""),
        )
        val resMock = MovieItemResponse(
            items
        )
        var isNotified = false
        var result = ArrayList<MovieItem>()
        SUT.setListener(
            MovieListViewCallback(
                showListVideo = {
                    isNotified = true; result.addAll(it)
                }
            )
        )
        runBlocking {
            coEvery { mDb.insert(any())}.returns(Unit)
            coEvery { mDb.nukeTable()}.returns(Unit)
            coEvery { mRepo.getMovieList()}.returns(resMock)
            SUT.onScreenStart()
            for(i in 0..10){
                delay(250)
            }

            delay(3000)
        }
        /** Test **/
        coVerify() { mDb.insert(any()) } // must update
        coVerify() { mDb.nukeTable() }

        assertThat(isNotified, `is`(true))
        if(result.isEmpty()){ throw Throwable("fail, it is empty") }
        result.forEachIndexed{index, it ->
            assertThat( it
                , `is`(resMock.items[index]))
        }
        assertThat(result.size, `is`(5))
    }

    @Test
    fun onScreenStart_FetchApi_OfflineSQLFeedData(){

        /** Arrange **/
        val items = listOf(
            MovieItem("","","","","","","","",""),
            MovieItem("","","","","","","","",""),
            MovieItem("","","","","","","","",""),
            MovieItem("","","","","","","","",""),
            MovieItem("","","","","","","","",""),
            MovieItem("","","","","","","","",""),
            MovieItem("","","","","","","","",""),
            MovieItem("","","","","","","","",""),
            MovieItem("","","","","","","","",""),
            MovieItem("","","","","","","","",""),
            MovieItem("","","","","","","","",""),
            MovieItem("","","","","","","","",""),
        )
        val resMock = MovieItemResponse(
            items
        )
        var isNotified = false
        var result = ArrayList<MovieItem>()
        SUT.setListener(
            MovieListViewCallback(
                showListVideo = {
                    isNotified = true; result.addAll(it)
                }
            )
        )
        runBlocking {
            coEvery { mRepo.getMovieList()}.returns(null)
            coEvery { mDb.loadAll()}.returns(items) // here
            SUT.onScreenStart()
            for(i in 0..10){
                delay(250)
            }
        }
        /** Test **/
        coVerify { mDb.loadAll() }
        coVerify(exactly = 0) { mDb.insert(any()) } // no update
        coVerify(exactly = 0) { mDb.nukeTable() }

        assertThat(isNotified, `is`(true))
        if(result.isEmpty()){ throw Throwable("fail, it is empty") }
        result.forEachIndexed{index, it ->
            assertThat( it
                , `is`(resMock.items[index]))
        }
        assertThat(result.size, `is`(5))
    }

}