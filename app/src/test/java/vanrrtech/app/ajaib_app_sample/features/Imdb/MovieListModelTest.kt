package vanrrtech.app.ajaib_app_sample.features.Imdb


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.*
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

import org.junit.Rule
import org.junit.Test
import vanrrtech.app.ajaib_app_sample.base_components.entities.ResourceState
import vanrrtech.app.ajaib_app_sample.data.SQDb.imdb.MovieListDao
import vanrrtech.app.ajaib_app_sample.data.remote_repository.RemoteApiRetrofitClient
import vanrrtech.app.ajaib_app_sample.domain.UseCases.imdb.GetMovieListUseCase
import vanrrtech.app.ajaib_app_sample.domain.UseCases.imdb.GetOfflineMovieListUseCase
import vanrrtech.app.ajaib_app_sample.domain.UseCases.imdb.MovieOfflineUpdateUseCase
import vanrrtech.app.ajaib_app_sample.domain.data_model.imdb.MovieItem
import vanrrtech.app.ajaib_app_sample.domain.data_model.imdb.MovieItemResponse

@RunWith(MockitoJUnitRunner::class)

internal class MovieListModelTest {
    lateinit var SUT: MovieListModel

    var movieUseCase: GetMovieListUseCase? = null
    var getOfflineMovieListUseCase: GetOfflineMovieListUseCase? = null
    var movieOfflineUpdateUseCase: MovieOfflineUpdateUseCase? = null

    @MockK
    lateinit var mRepo: RemoteApiRetrofitClient
    @MockK
    lateinit var mDao: MovieListDao

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    val dispatcher = TestCoroutineDispatcher()

    fun setup() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(dispatcher)
        movieUseCase = GetMovieListUseCase(mRepo)
        getOfflineMovieListUseCase = GetOfflineMovieListUseCase(mDao)
        movieOfflineUpdateUseCase = MovieOfflineUpdateUseCase(mDao)
        SUT = MovieListModel(
            movieUseCase!!,
            getOfflineMovieListUseCase!!,
            movieOfflineUpdateUseCase!!,
        )
    }

    @Test
    fun callApi_fetchMovieData_Succ(){
        setup()
        val res = MovieItemResponse(
            listOf(
                MovieItem("","","","","","","","","")
            )
        )
        var isNotified = false
        var result  : ResourceState<List<MovieItem>>? = null
        SUT.movieLiveData.observeForever{
            it.getContentForTest()?.let {
                isNotified = true; result = it
            }
        }

        runTest {
            coEvery { mRepo.getMovieList()}.returns(res)
            SUT?.fetchMovieData()
            while (!isNotified){
                delay(250)
            }
            advanceUntilIdle()
        }
        if(!(result is ResourceState.Success)){
            throw Throwable("response wrong")
        }
    }
    @Test
    fun callApi_fetchMovieData_Fail(){
        setup()
        var isNotified = false
        var result  : ResourceState<List<MovieItem>>? = null
        SUT.movieLiveData.observeForever{
            it.getContentForTest()?.let {
                isNotified = true; result = it
            }
        }

        runTest {
            coEvery { mRepo.getMovieList()}.returns(null)
            SUT?.fetchMovieData()
            while (!isNotified){
                delay(250)
            }
            advanceUntilIdle()
        }
        if(!(result is ResourceState.Failure)){
            throw Throwable("response wrong")
        }
    }

    @Test
    fun callApi_fetchMovieOfflineData_Succ(){
        setup()
        val res =
            listOf(
                MovieItem("","","","","","","","","")
            )
        var isNotified = false
        var result  : ResourceState<List<MovieItem>>? = null
        SUT.offlineMovieLiveData.observeForever{
            it.getContentForTest()?.let {
                isNotified = true; result = it
            }
        }

        runTest {
            coEvery { mDao.loadAll()}.returns(res)
            SUT?.fetchOfflineMovieList()
            while (!isNotified){
                delay(250)
            }
            advanceUntilIdle()
        }
        coVerify(exactly = 1){ mDao.loadAll() }
        if(!(result is ResourceState.Success)){
            throw Throwable("response wrong")
        }
    }
    @Test
    fun callApi_fetchOfflineMovieData_Fail(){
        setup()
        var isNotified = false
        var result  : ResourceState<List<MovieItem>>? = null
        SUT.offlineMovieLiveData.observeForever{
            it.getContentForTest()?.let {
                isNotified = true; result = it
            }
        }

        runTest {
            coEvery { mDao.loadAll()}.returns(null)
            SUT?.fetchOfflineMovieList()
            while (!isNotified){
                delay(250)
            }
            advanceUntilIdle()
        }
        coVerify(exactly = 1){ mDao.loadAll() }
        if(!(result is ResourceState.Failure)){
            throw Throwable("response wrong")
        }
    }

    @Test
    fun callApi_updateMovieOfflineData_Succ(){
        setup()
        val listSlot = mutableListOf<MovieItem>()
        val req =
            listOf(
                MovieItem("","","","","","","","",""),
                MovieItem("","","","","","","","",""),
                MovieItem("","","","","","","","","")
            )
        var isNotified = false
        var result  : ResourceState<Boolean>? = null
        SUT.updateMovieOfflineLiveData.observeForever{
            it.getContentForTest()?.let {
                isNotified = true; result = it
            }
        }

        runTest {
            coEvery { mDao.nukeTable()}.returns(Unit)
            coEvery { mDao.insert(capture(listSlot))}.returns(Unit)
            SUT.updateMovieOfflineLiveData(req)
            while (!isNotified){
                delay(250)
            }
            advanceUntilIdle()
        }

        req.forEachIndexed() { index, it ->
            MatcherAssert.assertThat(listSlot.get(index), CoreMatchers.`is`(it))
        }
        coVerify(exactly = 1){ mDao.nukeTable() }
        coVerify(exactly = 3){ mDao.insert(any()) }
        if(!(result is ResourceState.Success)){
            throw Throwable("response wrong")
        }
    }
    @Test
    fun callApi_updateMovieOfflineData_Fail(){
        setup()
        val listSlot = mutableListOf<MovieItem>()
        val req =
            listOf(
                MovieItem("","","","","","","","",""),
                MovieItem("","","","","","","","",""),
                MovieItem("","","","","","","","","")
            )
        var isNotified = false
        var result  : ResourceState<Boolean>? = null
        SUT.updateMovieOfflineLiveData.observeForever{
            it.getContentForTest()?.let {
                isNotified = true; result = it
            }
        }

        runTest {
            coEvery { mDao.nukeTable()}.returns(Unit)
            coEvery { mDao.insert(capture(listSlot))}.throws(Throwable("Device Error error"))
            SUT.updateMovieOfflineLiveData(req)
            while (!isNotified){
                delay(250)
            }
            advanceUntilIdle()
        }

        if(!(result is ResourceState.Failure)){
            throw Throwable("response wrong")
        }
    }
}