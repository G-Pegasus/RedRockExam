package redrock.tongji.redrockexam.ui.adapter

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
import redrock.tongji.redrockexam.bean.DiscoveryData

/**
 * @Author Tongji
 * @Description
 * @Date create in 2022/7/19 17:14
 */
class SpecialGridAdapter(private val context: Context, private val mList: MutableList<DiscoveryData>?) :
    RecyclerView.Adapter<SpecialGridAdapter.SpecialViewHolder>() {

    private lateinit var onItemClickListener: OnItemClickListener

    inner class SpecialViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivSpecial: ShapeableImageView = itemView.findViewById(R.id.iv_special)
        val tvSpecial: TextView = itemView.findViewById(R.id.tv_special)
        val rootLayout: RelativeLayout = itemView.findViewById(R.id.special_root)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpecialViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_special, parent, false)
        return SpecialViewHolder(view)
    }

    override fun onBindViewHolder(holder: SpecialViewHolder, position: Int) {
        if (mList != null) {
            Glide.with(context).load(mList[position].url).into(holder.ivSpecial)
            holder.tvSpecial.text = mList[position].title
            holder.rootLayout.setOnClickListener {
                onItemClickListener.onItemClick(
                    holder.itemView,
                    position
                )
            }
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