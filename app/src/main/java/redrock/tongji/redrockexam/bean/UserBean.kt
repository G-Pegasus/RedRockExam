package redrock.tongji.redrockexam.bean

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @Author Tongji
 * @Description
 * @Date create in 2022/7/23 20:24
 */

@Entity
data class UserBean(
    var name: String,
    var description: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}