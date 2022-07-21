package redrock.tongji.redrockexam.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import redrock.tongji.redrockexam.R

/**
 * @Author Tongji
 * @Description
 * @Date create in 2022/7/20 15:53
 */
class BannerAdapter(private val data: MutableList<String>) :
    RecyclerView.Adapter<BannerAdapter.InnerHolder>() {

    inner class InnerHolder(view: View) : RecyclerView.ViewHolder(view) {
        val mIvCover: ImageView = view.findViewById(R.id.feed_iv_banner_picture)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InnerHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_banner, parent, false)
        return InnerHolder(view)
    }

    override fun onBindViewHolder(holder: InnerHolder, position: Int) {
        holder.apply {
            Glide.with(mIvCover).load(data[position]).centerCrop().into(mIvCover)
        }
    }

    override fun getItemCount(): Int = data.size
}