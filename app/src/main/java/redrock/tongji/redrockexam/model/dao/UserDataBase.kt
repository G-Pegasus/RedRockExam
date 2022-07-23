package redrock.tongji.redrockexam.model.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import redrock.tongji.redrockexam.App
import redrock.tongji.redrockexam.bean.UserBean

/**
 * @Author Tongji
 * @Description
 * @Date create in 2022/7/23 20:45
 */

@Database(version = 1, entities = [UserBean::class])
abstract class UserDataBase : RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object {
        private var instance: UserDataBase? = null

        @Synchronized
        fun getDatabase(context: Context): UserDataBase {
            instance?.let {
                return it
            }
            return Room.databaseBuilder(context.applicationContext,
                UserDataBase::class.java, "user.db")
                .allowMainThreadQueries()
                .build().apply {
                    instance = this
                }
        }
    }
}