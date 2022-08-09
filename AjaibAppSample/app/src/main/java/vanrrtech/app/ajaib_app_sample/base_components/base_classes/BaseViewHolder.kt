package vanrrtech.app.ajaib_app_sample.base_components.base_classes

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

/** RV ViewHolder Base **/
abstract class BaseViewHolder<in BaseModel>(viewBinding: ViewBinding) : RecyclerView.ViewHolder(viewBinding.root) {
    abstract fun bindData(model: BaseModel)
}