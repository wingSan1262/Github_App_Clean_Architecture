package vanrrtech.app.ajaib_app_sample.data.SQDb.imdb

import androidx.room.*
import vanrrtech.app.ajaib_app_sample.domain.data_model.imdb.MovieItem

@Dao
interface MovieListDao {
    @Query("Select * from movie_table")
    fun loadAll() : List<MovieItem>?
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert (movie : MovieItem)
    @Update
    fun update (movie : MovieItem)
    @Delete
    fun delete (movie : MovieItem)
    @Query("DELETE FROM movie_table")
    fun nukeTable()
}