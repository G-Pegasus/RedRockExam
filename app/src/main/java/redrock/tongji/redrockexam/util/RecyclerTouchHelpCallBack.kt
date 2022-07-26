package redrock.tongji.redrockexam.util

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import redrock.tongji.redrockexam.ui.adapter.LikeAdapter
import java.util.*

class RecyclerTouchHelpCallBack(var onCallBack: OnHelperCallBack) : ItemTouchHelper.Callback() {

    var edit = false

    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        if (!edit) {
            return 0
        }

        // 拖拽方向
        val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN or ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT

        // 侧滑删除
        val swipeFlags = ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT

        return makeMovementFlags(dragFlags, swipeFlags)
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        if (viewHolder.itemViewType != target.itemViewType) return false

        val fromPosition = viewHolder.adapterPosition
        val targetPosition = target.adapterPosition

        onCallBack.onMove(fromPosition, targetPosition)
        return true
    }

    // 侧滑
    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        onCallBack.remove(viewHolder, direction, viewHolder.layoutPosition)
    }

    // 长按时调用
    override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
        super.onSelectedChanged(viewHolder, actionState)
        viewHolder?.let {
            onCallBack.onSelectedChanged(viewHolder, actionState)
        }
    }

    // 松手时会最后调用
    override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        super.clearView(recyclerView, viewHolder)
        onCallBack.clearView(recyclerView, viewHolder)
    }

    fun itemMove(adapter: RecyclerView.Adapter<LikeAdapter.LikeViewHolder>, data: List<*>, fromPosition: Int, targetPosition: Int) {
        if (data.isEmpty()) {
            return
        }

        if (fromPosition < targetPosition) {
            for (i in fromPosition until targetPosition) {
                Collections.swap(data, i, i + 1)
            }
        } else {
            for (i in targetPosition until fromPosition) {
                Collections.swap(data, i, i + 1)
            }
        }

        adapter.notifyItemMoved(fromPosition, targetPosition)
    }

    interface OnHelperCallBack {
        fun onMove(fromPosition: Int, targetPosition: Int)

        fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder, actionState: Int)

        fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder)

        fun remove(viewHolder: RecyclerView.ViewHolder, direction: Int, position: Int)
    }
}