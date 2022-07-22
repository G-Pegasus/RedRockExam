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
 * @Description 推荐界面 Adapter
 * @Date create in 2022/7/19 10:51
 */
class RecommendAdapter(private val context: Context, private val mList: MutableList<RecData>?)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var onItemClickListener: RecommendAdapter.OnItemClickListener
    var isScrolling = false

    inner class TextViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val text: TextView = itemView.findViewById(R.id.tv_daily)
    }

    inner class SquareViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivBg: ShapeableImageView = itemView.findViewById(R.id.iv_video_bg)
        val ivPortrait: ShapeableImageView = itemView.findViewById(R.id.iv_video_portrait)
        val tvTitle: TextView = itemView.findViewById(R.id.tv_video_title)
        val tvDescription: TextView = itemView.findViewById(R.id.tv_video_description)
        val tvTime: TextView = itemView.findViewById(R.id.tv_video_time_2)
        val rootLayout: RelativeLayout = itemView.findViewById(R.id.item_video_root)
    }

    inner class SmallVideoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivSmallBg: ShapeableImageView = itemView.findViewById(R.id.iv_small_video_bg)
        val tvSmallTitle: TextView = itemView.findViewById(R.id.tv_small_video_title)
        val tvSmallCategory: TextView = itemView.findViewById(R.id.tv_small_video_category)
        val tvSmallTime: TextView = itemView.findViewById(R.id.tv_small_video_time)
        val smallRootLayout: RelativeLayout = itemView.findViewById(R.id.item_small_video_root)
    }

    override fun getItemViewType(position: Int): Int {
        return when (mList?.get(position)?.type) {
            "textCard" -> 1
            "squareCardCollection" -> 2
            "followCard" -> 3
            "videoSmallCard" -> 3
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
                SmallVideoViewHolder(view)
            }

            else -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_small_video, parent, false)
                SmallVideoViewHolder(view)
            }
        }
    }

    @SuppressLint("SimpleDateFormat", "SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (mList != null && !isScrolling) {
            when (getItemViewType(position)) {
                1 -> {
                    val textHolder = holder as RecommendAdapter.TextViewHolder
                    textHolder.text.text = mList[position].text
                }

                2 -> {
                    val squareCollection = holder as RecommendAdapter.SquareViewHolder
                    squareCollection.tvTitle.text = mList[position].title
                    squareCollection.tvDescription.text = "# " + mList[position].author
                    Glide.with(context).load(mList[position].avatar)
                        .into(squareCollection.ivPortrait)
                    Glide.with(context).load(mList[position].url)
                        .into(squareCollection.ivBg)
                    val timeFormat = SimpleDateFormat("mm:ss")
                    timeFormat.timeZone = TimeZone.getTimeZone("GMT+00:00")
                    val hms = timeFormat.format(mList[position].time * 1000)
                    squareCollection.tvTime.text = hms
                    holder.rootLayout.setOnClickListener {
                        onItemClickListener.onItemClick(
                            holder.itemView,
                            position
                        )
                    }
                }

                3 -> {
                    val smallVideoHolder = holder as RecommendAdapter.SmallVideoViewHolder
                    smallVideoHolder.tvSmallTitle.text = mList[position].title
                    smallVideoHolder.tvSmallCategory.text = "# " + mList[position].author
                    Glide.with(context).load(mList[position].url)
                        .into(smallVideoHolder.ivSmallBg)
                    val timeFormat = SimpleDateFormat("mm:ss")
                    timeFormat.timeZone = TimeZone.getTimeZone("GMT+00:00")
                    val hms = timeFormat.format(mList[position].time * 1000)
                    smallVideoHolder.tvSmallTime.text = hms
                    holder.smallRootLayout.setOnClickListener {
                        onItemClickListener.onItemClick(
                            holder.itemView,
                            position
                        )
                    }
                }
            }
        }
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.onItemClickListener = listener
    }

    fun addMore(moreList: MutableList<RecData>) {
        mList?.addAll(moreList)
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