package vanrrtech.app.ajaib_app_sample.base_components.base_classes

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

/** RV ViewHolder Base **/
abstract class BaseViewHolder<in BaseModel>(rootView: View) : RecyclerView.ViewHolder(rootView) {
    abstract fun bindData(model: BaseModel)
}