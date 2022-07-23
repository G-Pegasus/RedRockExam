package redrock.tongji.redrockexam.model.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import redrock.tongji.redrockexam.bean.UserBean

/**
 * @Author Tongji
 * @Description
 * @Date create in 2022/7/23 20:38
 */

@Dao
interface UserDao {
    @Insert
    fun insertUser(user: UserBean)

    @Update
    fun updateUser(user: UserBean)

    @Query("select * from UserBean where id = :id")
    fun loadUserInfo(id: Long): UserBean

    @Query("select * from UserBean")
    fun loadAllUserInfo(): List<UserBean>
}