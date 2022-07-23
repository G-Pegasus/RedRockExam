package redrock.tongji.redrockexam.bean

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @Author Tongji
 * @Description 用来存储视频信息
 * @Date create in 2022/7/23 20:27
 */

@Entity
data class VideoInfoBean(
    var imgUrl: String,
    var avatar: String,
    var playUrl: String,
    var title: String,
    var description: String,
    var time: Int,
    @PrimaryKey(autoGenerate = false) var id: String,
    var blurred: String,
    var author: String
)
