package vanrrtech.app.ajaib_app_sample.domain.data_model.github.request

import vanrrtech.app.ajaib_app_sample.base_components.base_interface.BaseModel
import java.io.Serializable

data class SearchUserRequest(
    val query : String,
    val type : String = "Users"
) : Serializable, BaseModel

data class UserDetailRequest(
    val userName : String,
) : Serializable, BaseModel