package redrock.tongji.redrockexam.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView
import redrock.tongji.redrockexam.R
import redrock.tongji.redrockexam.bean.NotifyData
import java.text.SimpleDateFormat
import java.util.*

/**
 * @Author Tongji
 * @Description
 * @Date create in 2022/7/18 18:52
 */
class NotifyAdapter(private val context: Context, private val mList: MutableList<NotifyData>?) :
    RecyclerView.Adapter<NotifyAdapter.NotifyViewHolder>() {

    var isScrolling = false

    inner class NotifyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle: TextView = itemView.findViewById(R.id.tv_notify_title)
        val tvDescription: TextView = itemView.findViewById(R.id.tv_notify_description)
        val tvTime: TextView = itemView.findViewById(R.id.tv_notify_time)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotifyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_notify, parent, false)
        return NotifyViewHolder(view)
    }

    @SuppressLint("SimpleDateFormat")
    override fun onBindViewHolder(holder: NotifyViewHolder, position: Int) {
        if (mList != null && !isScrolling) {
            holder.tvTitle.text = mList[position].title
            holder.tvDescription.text = mList[position].content
            val timeFormat = SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
            timeFormat.timeZone = TimeZone.getTimeZone("GMT+08:00")
            val ms = timeFormat.format(mList[position].date)
            holder.tvTime.text = ms
        } else {
            holder.tvTitle.text = "我还没加载好哦"
        }
    }

    fun addMore(moreList: MutableList<NotifyData>) {
        mList?.addAll(moreList)
    }

    override fun getItemCount(): Int {
        if (mList != null) return mList.size
        return 0
    }
}