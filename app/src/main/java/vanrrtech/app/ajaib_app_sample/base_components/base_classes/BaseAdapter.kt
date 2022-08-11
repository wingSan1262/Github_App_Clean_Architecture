package vanrrtech.app.ajaib_app_sample.base_components.base_classes

import androidx.recyclerview.widget.RecyclerView
import vanrrtech.app.ajaib_app_sample.base_components.base_interface.BaseModel

/** Standard BaseAdapter Handling BaseModel List and  BaseViewHolder**/
abstract class BaseAdapter<T : BaseViewHolder<BaseModel>> : RecyclerView.Adapter<T>() {
    open var listItems: MutableList<BaseModel> = mutableListOf()

    override fun getItemCount(): Int {
        return listItems.size
    }

    fun removeItem(item : BaseModel){
        listItems.remove(item)
        notifyItemRangeRemoved(0, listItems.size)
    }

    fun clearList(){
        listItems.clear()
        notifyDataSetChanged()
    }

    fun insertAtTop(item: BaseModel){
        listItems.add(0, item)
        notifyItemInserted(0)
    }

    fun insertAll(item: List<BaseModel>){
        listItems.addAll(0, item)
        notifyItemInserted(0)
    }

    fun insertAllAtBottom(item: List<BaseModel>){
        listItems.size.run{
            listItems.addAll(this, item)
            notifyItemInserted(this)
        }
    }

    override fun onBindViewHolder(holder: T, position: Int) {
        bindVH(holder, position)
    }

    abstract fun bindVH(holder: T, position: Int)

}