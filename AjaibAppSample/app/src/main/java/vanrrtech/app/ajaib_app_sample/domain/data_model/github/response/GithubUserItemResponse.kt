package vanrrtech.app.ajaib_app_sample.domain.data_model.github.response

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import vanrrtech.app.ajaib_app_sample.base_components.base_interface.BaseModel
import java.io.Serializable

@Entity(tableName =  "user_list_table")
data class GithubUserItemResponse(
    @PrimaryKey
    @SerializedName("id")
    @ColumnInfo(name = "id")
    var id : Int,

    @SerializedName("login")
    @ColumnInfo(name = "login")
    var login : String,


    @SerializedName("avatar_url")
    @ColumnInfo(name = "avatar_url")
    var avatarUrl : String,

    @SerializedName("url")
    @ColumnInfo(name = "url")
    var url : String,

    @SerializedName("repos_url")
    @ColumnInfo(name = "repos_url")
    var repos_url : String,
) : Serializable, BaseModel

data class SearchResult(
    @SerializedName("items")
    var items : List<GithubUserItemResponse?>,

    @SerializedName("total_count")
    var total : Int
) : Serializable, BaseModel

data class UserDetails(
    @SerializedName("name")
    var name : String,
    @SerializedName("login")
    var login : String,
    @SerializedName("avatar_url")
    var avatarUrl : String,
    @SerializedName("bio")
    var bio : String,
    @SerializedName("followers")
    var followers : String,
    @SerializedName("following")
    var following : String,
    @SerializedName("location")
    var location : String,
    @SerializedName("email")
    var email : String,
) : Serializable, BaseModel

data class UserRepoDetails(
    @SerializedName("name")
    var name : String,
    @SerializedName("owner")
    var owner : GithubUserItemResponse,
    @SerializedName("description")
    var description : String,
    @SerializedName("watcher_count")
    var watcher_count : Int,
    @SerializedName("updated_at")
    var update_at : String
) : Serializable, BaseModel