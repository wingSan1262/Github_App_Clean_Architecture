package vanrrtech.app.ajaib_app_sample.features.github

import android.view.View
import vanrrtech.app.ajaib_app_sample.base_components.UtilServices.LoginHandler.RandomHandler
import vanrrtech.app.ajaib_app_sample.base_components.base_classes.BaseViewHolder
import vanrrtech.app.ajaib_app_sample.base_components.base_interface.BaseModel
import vanrrtech.app.ajaib_app_sample.base_components.extensions.loadImage
import vanrrtech.app.ajaib_app_sample.databinding.SearchUserItemBinding
import vanrrtech.app.ajaib_app_sample.domain.data_model.github.response.GithubUserItemResponse

class UserItemViewHolder(
    val vhBinding : SearchUserItemBinding,
    val root : View = vhBinding.root,
    val itemClick : (BaseModel) -> Unit = {},
    val randomHandler : RandomHandler
    ) : BaseViewHolder<BaseModel>(root) {
    override fun bindData(model: BaseModel) {
        model as GithubUserItemResponse
        with(vhBinding){
            vhBinding.getRoot()?.setOnClickListener{
                itemClick(model)
            }
            profilePict.loadImage(model.avatarUrl)
            nameTv.text = model.login
            loginTv.text = "@ ${model.login}"
            tvDetailDesc.text = randomHandler.randomBioDescGenerator()
            locationTv.text = randomHandler.randomLocationDescGenerator()
        }
    }
}