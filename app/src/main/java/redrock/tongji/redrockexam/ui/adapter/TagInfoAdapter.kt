package redrock.tongji.redrockexam.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.imageview.ShapeableImageView
import redrock.tongji.redrockexam.App
import redrock.tongji.redrockexam.R
import redrock.tongji.redrockexam.bean.TagRecData
import redrock.tongji.redrockexam.ext.gone
import redrock.tongji.redrockexam.ext.invisible
import redrock.tongji.redrockexam.ext.visible
import java.text.SimpleDateFormat
import java.util.*

/**
 * @Author Tongji
 * @Description
 * @Date create in 2022/7/21 22:14
 */
class TagInfoAdapter(private val context: Context, private val mList: MutableList<TagRecData>?) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var onItemClickListener: OnItemClickListener

    inner class TextViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val text: TextView = itemView.findViewById(R.id.tv_daily)
    }

    inner class SmallVideoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivSmallBg: ShapeableImageView = itemView.findViewById(R.id.iv_small_video_bg)
        val tvSmallTitle: TextView = itemView.findViewById(R.id.tv_small_video_title)
        val tvSmallCategory: TextView = itemView.findViewById(R.id.tv_small_video_category)
        val tvSmallTime: TextView = itemView.findViewById(R.id.tv_small_video_time)
        val smallRootLayout: RelativeLayout = itemView.findViewById(R.id.item_small_video_root)
    }

    inner class AutoVideoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivAutoVideoIcon: ShapeableImageView = itemView.findViewById(R.id.iv_special_portrait)
        val tvAutoVideoAuthor: TextView = itemView.findViewById(R.id.tv_special_author)
        val tvAutoVideoDes: TextView = itemView.findViewById(R.id.tv_special_des)
        val tvAutoVideoContent: TextView = itemView.findViewById(R.id.tv_special_content)
        val ivAutoVideoBg: ShapeableImageView = itemView.findViewById(R.id.iv_special_bg)
        val tvLikeCount: TextView = itemView.findViewById(R.id.tv_like_count)
        val tvCommentCount: TextView = itemView.findViewById(R.id.tv_comments_count)
        val tvShareCount: TextView = itemView.findViewById(R.id.tv_share_count)
        val btnMore: Button = itemView.findViewById(R.id.btn_more)
        //val btnLess: Button = itemView.findViewById(R.id.btn_less)
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
                    .inflate(R.layout.item_special_video, parent, false)
                AutoVideoViewHolder(view)
            }

            3 -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_small_video, parent, false)
                SmallVideoViewHolder(view)
            }

            else -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_special_video, parent, false)
                AutoVideoViewHolder(view)
            }
        }
    }

    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (mList != null) {
            when (getItemViewType(position)) {
                1 -> {
                    val textHolder = holder as TextViewHolder
                    textHolder.text.text = mList[position].text
                }

                2 -> {
                    val autoVideoHolder = holder as AutoVideoViewHolder
                    Glide.with(context).load(mList[position].url).into(autoVideoHolder.ivAutoVideoBg)
                    Glide.with(context).load(mList[position].avatar).into(autoVideoHolder.ivAutoVideoIcon)
                    autoVideoHolder.tvAutoVideoDes.text = mList[position].title
                    autoVideoHolder.tvAutoVideoContent.text = mList[position].description
                    autoVideoHolder.tvAutoVideoAuthor.text = mList[position].author
                    autoVideoHolder.tvLikeCount.text = mList[position].good.toString()
                    autoVideoHolder.tvCommentCount.text = mList[position].message.toString()
                    autoVideoHolder.tvShareCount.text = mList[position].shareCount.toString()
                    autoVideoHolder.btnMore.setOnClickListener {
                        autoVideoHolder.tvAutoVideoContent.layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
                        it.gone()
                        //autoVideoHolder.btnLess.visible()
                    }
//                    autoVideoHolder.btnLess.setOnClickListener {
//                        autoVideoHolder.tvAutoVideoContent.layoutParams.height = R.dimen.myTextViewSize
//                        it.invisible()
//                        autoVideoHolder.btnMore.visible()
//                    }
                    autoVideoHolder.ivAutoVideoBg.setOnClickListener {
                        onItemClickListener.onItemClick(
                            autoVideoHolder.itemView,
                            position
                        )
                    }
                }

                3 -> {
                    val smallVideoViewHolder = holder as SmallVideoViewHolder
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
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (mList?.get(position)?.type) {
            "textCard" -> 1
            "followCard" -> 2
            "videoSmallCard" -> 3
            else -> 0
        }
    }

    override fun getItemCount(): Int {
        if (mList != null) return mList.size
        return 0
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.onItemClickListener = listener
    }

    interface OnItemClickListener {
        fun onItemClick(view: View, position: Int)
        fun onItemLongClick(view: View, position: Int)
    }

}