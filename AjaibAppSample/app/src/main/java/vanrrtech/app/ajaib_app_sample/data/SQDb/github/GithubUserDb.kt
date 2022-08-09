package vanrrtech.app.ajaib_app_sample.data.SQDb.github

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import vanrrtech.app.ajaib_app_sample.domain.data_model.github.response.GithubUserItemResponse

@Database(entities = [GithubUserItemResponse::class], exportSchema = false, version = 1)
public abstract class GithubUserDb() : RoomDatabase() {
    companion object {
        val DB_NAME: String = "user_list_db"
        private var instance: GithubUserDb? = null

        fun getInstance(context: Context): GithubUserDb {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    GithubUserDb::class.java,
                    DB_NAME
                )
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return instance!!
        }
    }

    public abstract fun userItemDao(): UserListDao
}