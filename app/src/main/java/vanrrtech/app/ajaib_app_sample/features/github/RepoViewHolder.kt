package vanrrtech.app.ajaib_app_sample.features.github

import android.annotation.SuppressLint
import android.view.View
import vanrrtech.app.ajaib_app_sample.base_components.UtilServices.UnixDateConverter
import vanrrtech.app.ajaib_app_sample.base_components.base_classes.BaseViewHolder
import vanrrtech.app.ajaib_app_sample.base_components.base_interface.BaseModel
import vanrrtech.app.ajaib_app_sample.base_components.extensions.loadImage
import vanrrtech.app.ajaib_app_sample.databinding.UserRepoItemBinding
import vanrrtech.app.ajaib_app_sample.domain.data_model.github.response.UserRepoDetails
import java.util.logging.Handler

class RepoViewHolder(
    val vhBinding : UserRepoItemBinding,
    val timeHandler: UnixDateConverter,
    val root : View = vhBinding.root
    ) : BaseViewHolder<BaseModel>(root) {
    @SuppressLint("SetTextI18n")
    override fun bindData(model: BaseModel) {
        model as UserRepoDetails
        with(vhBinding){
            vhBinding.root // test purpose
            userImage.loadImage(model.owner.avatarUrl)
            this.repoName.text = model.name
            this.repoDesc.text = model.description
            this.repoStar.text = model.watcher_count.toString()
            repoLastUpdate.text = "Last updated ${when{
                timeHandler.getDaysBetweenWithCurrent(model.update_at) != "0" ->
                    timeHandler.getDaysBetweenWithCurrent(model.update_at) + " day ago"

                timeHandler.getHourBetweenWithCurrent(model.update_at) != "0" ->
                    timeHandler.getHourBetweenWithCurrent(model.update_at) + " hour ago"

                timeHandler.getMinutesBetweenWithCurrent(model.update_at) != "0" ->
                    timeHandler.getMinutesBetweenWithCurrent(model.update_at) + "minute ago"
                else -> {""}
            }}"
        }
    }
}