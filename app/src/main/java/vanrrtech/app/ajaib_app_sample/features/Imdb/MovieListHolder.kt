package vanrrtech.app.ajaib_app_sample.features.Imdb

import android.view.View
import vanrrtech.app.ajaib_app_sample.base_components.UtilServices.LoginHandler.RandomHandler
import vanrrtech.app.ajaib_app_sample.base_components.base_classes.BaseViewHolder
import vanrrtech.app.ajaib_app_sample.base_components.base_interface.BaseModel
import vanrrtech.app.ajaib_app_sample.base_components.extensions.loadImage
import vanrrtech.app.ajaib_app_sample.databinding.MovieItemHolderBinding
import vanrrtech.app.ajaib_app_sample.domain.data_model.imdb.MovieItem

class MovieListHolder(
    val vhBinding : MovieItemHolderBinding,
    val root : View = vhBinding.root,
    val itemClick : (BaseModel) -> Unit = {}
    ) : BaseViewHolder<BaseModel>(root) {
    override fun bindData(model: BaseModel) {
        model as MovieItem
        with(vhBinding){
            vhBinding.getRoot()?.setOnClickListener{
                itemClick(model)
            }
            imagePosterListItem.loadImage(model.image)
            tvTitle.text = model.fullTitle
        }
    }
}