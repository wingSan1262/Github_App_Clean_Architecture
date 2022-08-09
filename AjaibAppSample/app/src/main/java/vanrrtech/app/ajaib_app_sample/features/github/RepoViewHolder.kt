package vanrrtech.app.ajaib_app_sample.features.github

import vanrrtech.app.ajaib_app_sample.base_components.base_classes.BaseViewHolder
import vanrrtech.app.ajaib_app_sample.base_components.base_interface.BaseModel
import vanrrtech.app.ajaib_app_sample.base_components.extensions.loadImage
import vanrrtech.app.ajaib_app_sample.databinding.UserRepoItemBinding
import vanrrtech.app.ajaib_app_sample.domain.data_model.github.response.UserRepoDetails

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