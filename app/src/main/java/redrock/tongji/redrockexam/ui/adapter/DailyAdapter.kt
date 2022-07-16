package redrock.tongji.redrockexam.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import redrock.tongji.redrockexam.R
import redrock.tongji.redrockexam.bean.RecData

/**
 * @Author Tongji
 * @Description
 * @Date create in 2022/7/15 22:25
 */
class DailyAdapter(context: Context, private val mList: MutableList<RecData>)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var onItemClickListener: OnItemClickListener

    inner class TextViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val text: TextView = itemView.findViewById(R.id.tv_daily)
    }

    inner class FollowViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    inner class SquareViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.onItemClickListener = listener
    }

    override fun getItemViewType(position: Int): Int {
        return when (mList[position].type) {
            "textCard" -> 1
            "squareCardCollection" -> 2
            "followCard" -> 3
            "videoSmallCard" -> 4
            else -> 0
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            1 -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_text, parent, false)
                TextViewHolder(view)
            }

            2 -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_video, parent, false)
                SquareViewHolder(view)
            }

            3 -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_small_video, parent, false)
                FollowViewHolder(view)
            }

            4 -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_small_video, parent, false)
                FollowViewHolder(view)
            }

            else -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_small_video, parent, false)
                FollowViewHolder(view)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

    }

    override fun getItemCount(): Int = mList.size

    interface OnItemClickListener {
        fun onItemClick(view: View, position: Int)
        fun onItemLongClick(view: View, position: Int)
    }

}