package vanrrtech.app.ajaib_app_sample.domain.data_model.imdb

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import vanrrtech.app.ajaib_app_sample.base_components.base_interface.BaseModel
import java.io.Serializable

data class MovieItemResponse(
    @SerializedName("items")
    var items : List<MovieItem> = emptyList()
) : Serializable, BaseModel

@Entity(tableName =  "movie_table")
data class MovieItem(
    @SerializedName("id")
    @ColumnInfo(name = "id")
    var id : String,
    @SerializedName("rank")
    @ColumnInfo(name = "rank")
    var rank : String,
    @SerializedName("title")
    @ColumnInfo(name = "title")
    var title : String,
    @SerializedName("fullTitle")
    @ColumnInfo(name = "fullTitle")
    var fullTitle : String,
    @SerializedName("year")
    @ColumnInfo(name = "year")
    var year : String,
    @SerializedName("image")
    @ColumnInfo(name = "image")
    var image : String,
    @SerializedName("crew")
    @ColumnInfo(name = "crew")
    var crew : String,
    @SerializedName("imDbRating")
    @ColumnInfo(name = "imDbRating")
    var imDbRating : String,
    @SerializedName("imDbRatingCount")
    @ColumnInfo(name = "imDbRatingCount")
    var imDbRatingCount : String,
)
