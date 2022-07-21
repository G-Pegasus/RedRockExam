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
import redrock.tongji.redrockexam.bean.CommonData
import java.text.SimpleDateFormat
import java.util.*

/**
 * @Author Tongji
 * @Description
 * @Date create in 2022/7/21 12:22
 */
class RelatedAdapter(private val context: Context, private val mList: MutableList<CommonData>?)
    : RecyclerView.Adapter<RelatedAdapter.RelatedViewHolder>() {

    private lateinit var onItemClickListener: OnItemClickListener

    inner class RelatedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivSmallBg: ShapeableImageView = itemView.findViewById(R.id.iv_related_bg)
        val tvSmallTitle: TextView = itemView.findViewById(R.id.tv_related_title)
        val tvSmallCategory: TextView = itemView.findViewById(R.id.tv_related_category)
        val tvSmallTime: TextView = itemView.findViewById(R.id.tv_related_time)
        val smallRootLayout: RelativeLayout = itemView.findViewById(R.id.item_related_root)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RelatedViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_related, parent, false)
        return RelatedViewHolder(view)
    }

    @SuppressLint("SimpleDateFormat", "SetTextI18n")
    override fun onBindViewHolder(holder: RelatedViewHolder, position: Int) {
        if (mList != null) {
            Glide.with(App.context).load(mList[position].url).into(holder.ivSmallBg)
            holder.tvSmallTitle.text = mList[position].title
            holder.tvSmallCategory.text = "# " + mList[position].author
            val timeFormat = SimpleDateFormat("mm:ss")
            timeFormat.timeZone = TimeZone.getTimeZone("GMT+00:00")
            val hms = timeFormat.format(mList[position].time * 1000)
            holder.tvSmallTime.text = hms
            holder.smallRootLayout.setOnClickListener {
                onItemClickListener.onItemClick(
                    holder.itemView,
                    position
                )
            }
        }
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.onItemClickListener = listener
    }

    interface OnItemClickListener {
        fun onItemClick(view: View, position: Int)
        fun onItemLongClick(view: View, position: Int)
    }

    override fun getItemCount(): Int {
        if (mList != null) return mList.size
        return 0
    }
}