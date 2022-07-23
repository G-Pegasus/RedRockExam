package redrock.tongji.redrockexam.model.repository

import androidx.lifecycle.liveData
import kotlinx.coroutines.Dispatchers
import redrock.tongji.redrockexam.bean.CommentData
import redrock.tongji.redrockexam.bean.CommonData
import redrock.tongji.redrockexam.model.network.ApiLoad
import kotlin.coroutines.CoroutineContext

/**
 * @Author Tongji
 * @Description
 * @Date create in 2022/7/21 12:04
 */
object PlayerRepo {

    fun loadRelated(query: String) = fire(Dispatchers.IO) {
        val response = ApiLoad.loadRelated(query)
        val length = response.count
        val list = mutableListOf<CommonData>()
        for (i in 0 until length) {
            if (response.itemList[i].type == "videoSmallCard") {

                val relatedData = CommonData(
                    response.itemList[i].data.cover.feed,
                    response.itemList[i].data.playUrl,
                    response.itemList[i].data.duration,
                    response.itemList[i].data.title,
                    response.itemList[i].data.category,
                    response.itemList[i].data.description,
                    response.itemList[i].data.id.toString(),
                    response.itemList[i].data.cover.blurred
                )
                list.add(relatedData)
            }
        }

        Result.success(list)
    }

    fun loadReply(query: String) = fire(Dispatchers.IO) {
        val response = ApiLoad.loadComments(query)
        val length = response.count
        val list = mutableListOf<CommentData>()
        for (i in 0 until length) {
            if (response.itemList[i].type == "reply") {
                val replyData =
                    CommentData(
                        response.itemList[i].data.user.avatar,
                        response.itemList[i].data.user.nickname,
                        response.itemList[i].data.message,
                        response.itemList[i].data.createTime,
                        response.itemList[i].data.likeCount,
                        "",
                        response.itemList[i].type
                    )
                list.add(replyData)
            }
        }

        Result.success(list)
    }

    @Suppress("SameParameterValue")
    private fun <T> fire(context: CoroutineContext, block: suspend () -> Result<T>) =
        liveData(context) {
            val result = try {
                block()
            } catch (e: Exception) {
                Result.failure(e)
            }
            emit(result)
        }
}