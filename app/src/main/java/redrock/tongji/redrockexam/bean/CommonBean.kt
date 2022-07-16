package redrock.tongji.redrockexam.bean

import java.io.Serializable

/**
 * @Author Tongji
 * @Description
 * @Date create in 2022/7/16 17:04
 */
data class CommonData(
    val url:String,
    val playUrl:String,
    val time:Int,
    val title:String,
    val author:String,
    val description:String,
    val id:String,
    val blurred:String) : Serializable