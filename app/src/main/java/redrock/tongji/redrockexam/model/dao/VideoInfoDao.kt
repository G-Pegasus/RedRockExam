package redrock.tongji.redrockexam.model.dao

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import redrock.tongji.redrockexam.bean.VideoInfoBean

/**
 * @Author Tongji
 * @Description 收藏视频
 * @Date create in 2022/7/23 20:42
 */

@Dao
interface VideoInfoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertVideo(video: VideoInfoBean)

    @Query("select * from VideoInfoBean")
    fun loadAllVideos(): LiveData<List<VideoInfoBean>>

    @Query("select * from VideoInfoBean where id = :id")
    fun loadIsExist(id: String) : VideoInfoBean
}