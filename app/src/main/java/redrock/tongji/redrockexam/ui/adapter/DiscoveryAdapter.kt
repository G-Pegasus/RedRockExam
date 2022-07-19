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
import redrock.tongji.redrockexam.App
import redrock.tongji.redrockexam.R
import redrock.tongji.redrockexam.bean.DiscoveryData
import java.text.SimpleDateFormat
import java.util.*

/**
 * @Author Tongji
 * @Description
 * @Date create in 2022/7/19 19:50
 */
class DiscoveryAdapter(private val context: Context, private val mList: MutableList<DiscoveryData>?) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var onItemClickListener: OnItemClickListener

    // 文本类型ViewHolder
    inner class TextViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val text: TextView = itemView.findViewById(R.id.tv_daily)
    }

    inner class ColumnViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvColumn: TextView = itemView.findViewById(R.id.tv_column)
        val ivColumn: ShapeableImageView = itemView.findViewById(R.id.iv_column)
    }

    inner class SmallVideoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivSmallBg: ShapeableImageView = itemView.findViewById(R.id.iv_small_video_bg)
        val tvSmallTitle: TextView = itemView.findViewById(R.id.tv_small_video_title)
        val tvSmallCategory: TextView = itemView.findViewById(R.id.tv_small_video_category)
        val tvSmallTime: TextView = itemView.findViewById(R.id.tv_small_video_time)
        val smallRootLayout: RelativeLayout = itemView.findViewById(R.id.item_small_video_root)
    }

    inner class BriefViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivBriefBg: ShapeableImageView = itemView.findViewById(R.id.iv_brief)
        val tvBriefTitle: TextView = itemView.findViewById(R.id.tv_brief_title)
        val tvBriefContent: TextView = itemView.findViewById(R.id.tv_brief_content)
    }

    override fun getItemViewType(position: Int): Int {
        return when (mList?.get(position)?.type) {
            "textCard" -> 1
            "columnCardList" -> 2
            "videoSmallCard" -> 3
            "briefCard" -> 4
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
                    .inflate(R.layout.item_column, parent, false)
                ColumnViewHolder(view)
            }

            3 -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_small_video, parent, false)
                SmallVideoViewHolder(view)
            }

            else -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_brief, parent, false)
                BriefViewHolder(view)
            }
        }
    }

    @SuppressLint("SimpleDateFormat", "SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (mList != null) {
            when (getItemViewType(position)) {
                1 -> {
                    val textHolder = holder as DiscoveryAdapter.TextViewHolder
                    textHolder.text.text = mList[position].text
                }

                2 -> {
                    val columnHolder = holder as ColumnViewHolder
                    Glide.with(App.context).load(mList[position].url).into(columnHolder.ivColumn)
                    columnHolder.tvColumn.text = mList[position].title
                }

                3 -> {
                    val smallVideoViewHolder = holder as DiscoveryAdapter.SmallVideoViewHolder
                    Glide.with(App.context).load(mList[position].url).into(smallVideoViewHolder.ivSmallBg)
                    smallVideoViewHolder.tvSmallTitle.text = mList[position].title
                    smallVideoViewHolder.tvSmallCategory.text = "# " + mList[position].author
                    val timeFormat = SimpleDateFormat("mm:ss")
                    timeFormat.timeZone = TimeZone.getTimeZone("GMT+00:00")
                    val hms = timeFormat.format(mList[position].time * 1000)
                    smallVideoViewHolder.tvSmallTime.text = hms
                    holder.smallRootLayout.setOnClickListener {
                        onItemClickListener.onItemClick(
                            holder.itemView,
                            position
                        )
                    }
                }

                4 -> {
                    val briefViewHolder = holder as BriefViewHolder
                    Glide.with(App.context).load(mList[position].url).into(briefViewHolder.ivBriefBg)
                    briefViewHolder.tvBriefTitle.text = mList[position].title
                    briefViewHolder.tvBriefContent.text = mList[position].description
                }
            }
        }
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.onItemClickListener = listener
    }

    override fun getItemCount(): Int {
        if (mList != null) return mList.size
        return 0
    }

    interface OnItemClickListener {
        fun onItemClick(view: View, position: Int)
        fun onItemLongClick(view: View, position: Int)
    }

}