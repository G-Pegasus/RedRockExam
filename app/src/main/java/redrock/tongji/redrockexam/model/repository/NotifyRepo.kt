package redrock.tongji.redrockexam.model.repository

import androidx.lifecycle.liveData
import kotlinx.coroutines.Dispatchers
import redrock.tongji.redrockexam.bean.NotifyData
import redrock.tongji.redrockexam.model.network.ApiLoad
import kotlin.coroutines.CoroutineContext

/**
 * @Author Tongji
 * @Description
 * @Date create in 2022/7/19 10:10
 */
object NotifyRepo {

    fun loadNotify() = fire(Dispatchers.IO) {
        val response = ApiLoad.loadNotify()
        val length = response.messageList.size
        val list = mutableListOf<NotifyData>()
        for (i in 0 until length) {
            val messageData = NotifyData(
                response.messageList[i].title,
                response.messageList[i].content,
                response.messageList[i].date,
                response.nextPageUrl
            )
            list.add(messageData)
        }

        Result.success(list)
    }

    fun loadMoreNotify(url: String) = fire(Dispatchers.IO) {
        val response = ApiLoad.loadMoreNotify(url)
        val length = response.messageList.size
        val list = mutableListOf<NotifyData>()
        try {
            for (i in 0 until length) {
                val messageData = NotifyData(
                    response.messageList[i].title,
                    response.messageList[i].content,
                    response.messageList[i].date,
                    response.nextPageUrl
                )
                list.add(messageData)
            }

        } catch (e: Exception) {
            e.printStackTrace()
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