package redrock.tongji.redrockexam.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.imageview.ShapeableImageView
import redrock.tongji.redrockexam.R
import redrock.tongji.redrockexam.bean.SquareData

/**
 * @Author Tongji
 * @Description
 * @Date create in 2022/7/22 19:51
 */
class SquareAdapter(private val context: Context, private val mList: MutableList<SquareData>?) :
    RecyclerView.Adapter<SquareAdapter.SquareViewHolder>() {

    private lateinit var onItemClickListener: OnItemClickListener

    inner class SquareViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivImg: ShapeableImageView = itemView.findViewById(R.id.iv_square_img)
        val tvDes: TextView = itemView.findViewById(R.id.tv_square_des)
        val tvName: TextView = itemView.findViewById(R.id.tv_square_name)
        val ivPortrait: ShapeableImageView = itemView.findViewById(R.id.iv_square_portrait)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SquareViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_square, parent, false)
        return SquareViewHolder(view)
    }

    override fun onBindViewHolder(holder: SquareViewHolder, position: Int) {
        if (mList != null) {
            Glide.with(context).load(mList[position].cover).into(holder.ivImg)
            Glide.with(context).load(mList[position].avatar).into(holder.ivPortrait)
            holder.tvDes.text = mList[position].description
            holder.tvName.text = mList[position].name
            holder.ivImg.setOnClickListener {
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

    fun addMore(moreList: MutableList<SquareData>) {
        mList?.addAll(moreList)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.onItemClickListener = listener
    }

    interface OnItemClickListener {
        fun onItemClick(view: View, position: Int)
        fun onItemLongClick(view: View, position: Int)
    }
}