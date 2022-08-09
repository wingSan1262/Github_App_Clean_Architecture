package vanrrtech.app.prodiaappsample.data.SQDb.github

import androidx.room.*
import vanrrtech.app.prodiaappsample.domain.data_model.github.response.GithubUserItemResponse

@Dao
interface UserListDao {
    @Query("Select * from user_list_table")
    fun loadAllUser() : List<GithubUserItemResponse>?
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUsers (movie : GithubUserItemResponse)
    @Update
    fun updateUser (movie : GithubUserItemResponse)
    @Delete
    fun deleteUser (movie : GithubUserItemResponse)
    @Query("DELETE FROM user_list_table")
    fun nukeTable()
}