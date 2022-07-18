package redrock.tongji.redrockexam.bean

/**
 * @Author Tongji
 * @Description
 * @Date create in 2022/7/18 18:59
 */
data class NotifyBean(
    val messageList: List<Notify>,
    val nextPageUrl: String,
    val updateTime: Long
) {
    data class Notify(
        val actionUrl: String,
        val content: String,
        val date: Long,
        val icon: String,
        val id: Int,
        val ifPush: Boolean,
        val pushStatus: Int,
        val title: String,
        val uid: Any,
        val viewed: Boolean
    )
}

data class NotifyData(
    val title: String,
    val content: String,
    val date: Long,
    val nextUrl:String)