package vanrrtech.app.prodiaappsample.domain.data_model.github.request

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import vanrrtech.app.prodiaappsample.base_components.base_interface.BaseModel
import java.io.Serializable

data class SearchUserRequest(
    val query : String,
    val type : String = "Users"
) : Serializable, BaseModel

data class UserDetailRequest(
    val userName : String,
) : Serializable, BaseModel