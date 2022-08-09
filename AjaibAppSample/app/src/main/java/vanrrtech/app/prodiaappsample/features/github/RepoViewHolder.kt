package vanrrtech.app.prodiaappsample.features.github

import vanrrtech.app.prodiaappsample.base_components.base_classes.BaseViewHolder
import vanrrtech.app.prodiaappsample.base_components.base_interface.BaseModel
import vanrrtech.app.prodiaappsample.base_components.extensions.loadImage
import vanrrtech.app.prodiaappsample.databinding.UserRepoItemBinding
import vanrrtech.app.prodiaappsample.domain.data_model.github.response.UserRepoDetails

class RepoViewHolder(
    val vhBinding : UserRepoItemBinding
    ) : BaseViewHolder<BaseModel>(vhBinding) {
    override fun bindData(model: BaseModel) {
        model as UserRepoDetails
        with(vhBinding){
            userImage.loadImage(model.owner.avatarUrl)
            this.repoName.text = model.name
            this.repoDesc.text = model.description
            this.repoStar.text = model.watcher_count.toString()
            repoLastUpdate.text = model.update_at
        }
    }
}