package vanrrtech.app.prodiaappsample.features.github

import android.view.LayoutInflater
import android.view.ViewGroup
import vanrrtech.app.prodiaappsample.base_components.base_classes.BaseAdapter
import vanrrtech.app.prodiaappsample.base_components.base_classes.BaseViewHolder
import vanrrtech.app.prodiaappsample.base_components.base_interface.BaseModel
import vanrrtech.app.prodiaappsample.databinding.UserRepoItemBinding

class RepoAdapter(
    val itemClick : (BaseModel) -> Unit = {}
) : BaseAdapter<BaseViewHolder<BaseModel>>() {

    override fun bindVH(holder: BaseViewHolder<BaseModel>, position: Int) {
        holder.bindData(listItems[position])
        holder.itemView.setOnClickListener {
            itemClick
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<BaseModel> {
        return RepoViewHolder(
            UserRepoItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

}