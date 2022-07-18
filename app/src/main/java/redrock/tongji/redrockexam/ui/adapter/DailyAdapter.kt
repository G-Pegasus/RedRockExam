package redrock.tongji.redrockexam.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.imageview.ShapeableImageView
import redrock.tongji.redrockexam.R
import redrock.tongji.redrockexam.bean.RecData
import java.text.SimpleDateFormat
import java.util.*

/**
 * @Author Tongji
 * @Description 日报界面 Adapter
 * @Date create in 2022/7/15 22:25
 */
class DailyAdapter(private val context: Context, private val mList: MutableList<RecData>?)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var onItemClickListener: OnItemClickListener
    var isScrolling = false

    // 文本类型ViewHolder
    inner class TextViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val text: TextView = itemView.findViewById(R.id.tv_daily)
    }

    // 视频类型ViewHolder
    inner class FollowViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivBg: ShapeableImageView = itemView.findViewById(R.id.iv_video_bg)
        val ivPortrait: ShapeableImageView = itemView.findViewById(R.id.iv_video_portrait)
        val tvTitle: TextView = itemView.findViewById(R.id.tv_video_title)
        val tvDescription: TextView = itemView.findViewById(R.id.tv_video_description)
        val tvTime: TextView = itemView.findViewById(R.id.tv_video_time_2)
        val rootLayout: RelativeLayout = itemView.findViewById(R.id.item_video_root)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.onItemClickListener = listener
    }

    override fun getItemViewType(position: Int): Int {
        return when (mList?.get(position)?.type) {
            "textCard" -> 1
            "followCard" -> 2
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
                // RV 优化
//                FollowViewHolder(view).rootLayout.setOnClickListener {
//                    onClickListener(FollowViewHolder(view).absoluteAdapterPosition)
//                }
                FollowViewHolder(view)
            }

            else -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_video, parent, false)
                FollowViewHolder(view)
            }
        }
    }

    @SuppressLint("SimpleDateFormat")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (mList != null && !isScrolling) {
            when (getItemViewType(position)) {
                // Text 类型
                1 -> {
                    val textHolder = holder as TextViewHolder
                    textHolder.text.text = mList[position].text
                }

                // Video 类型
                2 -> {
                    val followCard = holder as FollowViewHolder
                    followCard.tvTitle.text = mList[position].title
                    followCard.tvDescription.text = mList[position].author
                    Glide.with(context).load(mList[position].avatar)
                        .into(followCard.ivPortrait)
                    Glide.with(context).load(mList[position].url)
                        .into(followCard.ivBg)
                    val timeFormat = SimpleDateFormat("mm:ss")
                    timeFormat.timeZone = TimeZone.getTimeZone("GMT+00:00")
                    val hms = timeFormat.format(mList[position].time * 1000)
                    followCard.tvTime.text = hms
                    holder.rootLayout.setOnClickListener {
                        onItemClickListener.onItemClick(
                            holder.itemView,
                            position
                        )
                    }
                }
            }
        }
    }

    fun addMore(moreList: MutableList<RecData>) {
        mList?.addAll(moreList)
    }

    // 对 RV 进行优化
    private fun onClickListener(position: Int): View.OnClickListener = View.OnClickListener {
        onItemClickListener.onItemClick(it, position)
    }

    override fun getItemCount(): Int {
        return mList?.size ?: 0
    }

    interface OnItemClickListener {
        fun onItemClick(view: View, position: Int)
        fun onItemLongClick(view: View, position: Int)
    }

}