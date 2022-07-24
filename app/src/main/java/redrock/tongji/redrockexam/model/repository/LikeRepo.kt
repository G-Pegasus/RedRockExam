package redrock.tongji.redrockexam.model.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import redrock.tongji.redrockexam.App
import redrock.tongji.redrockexam.bean.VideoInfoBean
import redrock.tongji.redrockexam.model.dao.VideoInfoDatabase

/**
 * @Author Tongji
 * @Description
 * @Date create in 2022/7/24 13:02
 */
object LikeRepo {

    private val videoDao = VideoInfoDatabase.getDatabase(App.context).videoInfoDao()

    fun loadMyLike() : LiveData<List<VideoInfoBean>> {
        return videoDao.loadAllVideos()
    }
}