package redrock.tongji.redrockexam.util

import androidx.recyclerview.widget.DiffUtil
import redrock.tongji.redrockexam.bean.VideoInfoBean

/**
 * @Author Tongji
 * @Description 进行差分刷新数据
 * @Date create in 2022/7/26 9:58
 */
class DiffCallBack(private val oldList: MutableList<VideoInfoBean>?, private val newList: MutableList<VideoInfoBean>?) :
    DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList!![oldItemPosition] === newList!![newItemPosition]
    }

    override fun getOldListSize(): Int {
        return oldList!!.size
    }

    override fun getNewListSize(): Int {
        return newList!!.size
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList!![oldItemPosition] == newList!![newItemPosition]
    }

}