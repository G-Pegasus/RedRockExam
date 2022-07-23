package redrock.tongji.redrockexam.model.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import redrock.tongji.redrockexam.App
import redrock.tongji.redrockexam.bean.VideoInfoBean

/**
 * @Author Tongji
 * @Description
 * @Date create in 2022/7/23 20:50
 */

@Database(version = 1, entities = [VideoInfoBean::class])
abstract class VideoInfoDatabase : RoomDatabase() {

    abstract fun videoInfoDao() : VideoInfoDao

    companion object {
        private var instance: VideoInfoDatabase? = null

        @Synchronized
        fun getDatabase(context: Context): VideoInfoDatabase {
            instance?.let {
                return it
            }
            return Room.databaseBuilder(context.applicationContext,
                VideoInfoDatabase::class.java, "video.db")
                .allowMainThreadQueries()
                .build().apply {
                    instance = this
                }
        }
    }
}