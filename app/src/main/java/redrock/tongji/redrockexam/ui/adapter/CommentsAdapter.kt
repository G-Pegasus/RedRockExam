package redrock.tongji.redrockexam.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.imageview.ShapeableImageView
import redrock.tongji.redrockexam.R
import redrock.tongji.redrockexam.bean.CommentData
import redrock.tongji.redrockexam.bean.CommonData
import java.text.SimpleDateFormat
import java.util.*

/**
 * @Author Tongji
 * @Description
 * @Date create in 2022/7/21 12:22
 */
class CommentsAdapter(private val context: Context, private val mList: MutableList<CommentData>?)
    : RecyclerView.Adapter<CommentsAdapter.ReplyViewHolder>() {

    inner class ReplyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivIcon: ShapeableImageView = itemView.findViewById(R.id.iv_comments)
        val tvName: TextView = itemView.findViewById(R.id.tv_comments_name)
        val tvContent: TextView = itemView.findViewById(R.id.tv_comments_content)
        val tvTime: TextView = itemView.findViewById(R.id.tv_comments_time)
    }

    @SuppressLint("SimpleDateFormat")
    override fun onBindViewHolder(holder: ReplyViewHolder, position: Int) {
        if (mList != null) {
            Glide.with(context).load(mList[position].avatar).into(holder.ivIcon)
            holder.tvName.text = mList[position].nickName
            holder.tvContent.text = mList[position].message
            val timeFormat = SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
            timeFormat.timeZone = TimeZone.getTimeZone("GMT+00:00")
            val hms = timeFormat.format(mList[position].time);
            holder.tvTime.text = hms
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReplyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_comments, parent, false)
        return ReplyViewHolder(view)
    }

    override fun getItemCount(): Int {
        if (mList != null) return mList.size
        return 0
    }

}