package vanrrtech.app.ajaib_app_sample.data.SQDb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import vanrrtech.app.ajaib_app_sample.data.SQDb.imdb.MovieListDao
import vanrrtech.app.ajaib_app_sample.domain.data_model.imdb.MovieItem

@Database(entities = [MovieItem::class], exportSchema = false, version = 1)
public abstract class AppDb() : RoomDatabase() {
    companion object {
        val DB_NAME: String = "user_list_db"
        private var instance: AppDb? = null

        fun getInstance(context: Context): AppDb {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDb::class.java,
                    DB_NAME
                )
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return instance!!
        }
    }

    public abstract fun movieDao(): MovieListDao
}