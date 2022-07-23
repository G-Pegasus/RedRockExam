package redrock.tongji.redrockexam.model.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import redrock.tongji.redrockexam.bean.VideoInfoBean

/**
 * @Author Tongji
 * @Description
 * @Date create in 2022/7/23 20:42
 */

@Dao
interface VideoInfoDao {
    @Insert
    fun insertVideo(video: VideoInfoBean)

    @Query("select * from VideoInfoBean")
    fun loadAllVideos(): List<VideoInfoBean>
}