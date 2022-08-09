package vanrrtech.app.prodiaappsample.features.github

import vanrrtech.app.prodiaappsample.base_components.UtilServices.LoginHandler.RandomHandler
import vanrrtech.app.prodiaappsample.base_components.base_classes.BaseViewHolder
import vanrrtech.app.prodiaappsample.base_components.base_interface.BaseModel
import vanrrtech.app.prodiaappsample.base_components.extensions.loadImage
import vanrrtech.app.prodiaappsample.databinding.SearchUserItemBinding
import vanrrtech.app.prodiaappsample.domain.data_model.github.response.GithubUserItemResponse

class UserItemViewHolder(
    val vhBinding : SearchUserItemBinding,
    val itemClick : (BaseModel) -> Unit = {},
    val randomHandler : RandomHandler
    ) : BaseViewHolder<BaseModel>(vhBinding) {
    override fun bindData(model: BaseModel) {
        model as GithubUserItemResponse
        with(vhBinding){
            vhBinding.root.setOnClickListener{
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