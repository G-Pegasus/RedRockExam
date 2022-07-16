package redrock.tongji.lib_base.base

import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * @Author Tongji
 * @Description
 * @Date create in 2022/7/16 15:19
 */
abstract class BaseAdapter(val mList: MutableList<*>)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var onItemClickListener: OnItemClickListener

    open fun setOnItemClickListener(listener: OnItemClickListener) {
        this.onItemClickListener = listener
    }

    interface OnItemClickListener {
        fun onItemClick(view: View, position: Int)
        fun onItemLongClick(view: View, position: Int)
    }

    override fun getItemCount(): Int {
        return mList.size
    }
}