package vanrrtech.app.ajaib_app_sample.features.github

import android.view.LayoutInflater
import android.view.ViewGroup
import vanrrtech.app.ajaib_app_sample.base_components.UtilServices.LoginHandler.RandomHandler
import vanrrtech.app.ajaib_app_sample.base_components.base_classes.BaseAdapter
import vanrrtech.app.ajaib_app_sample.base_components.base_classes.BaseViewHolder
import vanrrtech.app.ajaib_app_sample.base_components.base_interface.BaseModel
import vanrrtech.app.ajaib_app_sample.databinding.SearchUserItemBinding

class UserListAdapter(
    val randomHandler : RandomHandler,
    val itemClick : (BaseModel) -> Unit = {}
) : BaseAdapter<BaseViewHolder<BaseModel>>() {

    override fun bindVH(holder: BaseViewHolder<BaseModel>, position: Int) {
        holder.bindData(listItems[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<BaseModel> {
        return UserItemViewHolder(
            SearchUserItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            itemClick,
            randomHandler
        )
    }

}