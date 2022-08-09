package vanrrtech.app.prodiaappsample.base_components.base_classes

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

/** RV ViewHolder Base **/
abstract class BaseViewHolder<in BaseModel>(viewBinding: ViewBinding) : RecyclerView.ViewHolder(viewBinding.root) {
    abstract fun bindData(model: BaseModel)
}