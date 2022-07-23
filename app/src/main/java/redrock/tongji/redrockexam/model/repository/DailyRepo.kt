package redrock.tongji.redrockexam.model.repository

import androidx.lifecycle.liveData
import kotlinx.coroutines.Dispatchers
import redrock.tongji.redrockexam.bean.RecData
import redrock.tongji.redrockexam.model.network.ApiLoad
import kotlin.coroutines.CoroutineContext

/**
 * @Author Tongji
 * @Description
 * @Date create in 2022/7/19 10:02
 */
object DailyRepo {

    fun loadDaily() = fire(Dispatchers.IO) {
        val response = ApiLoad.loadDaily()
        val length = response.count
        val list = mutableListOf<RecData>()
        try {
            for (i in 0 until length) {
                if (response.itemList[i].type == "textCard" && length > 1) {
                    val recData = RecData(
                        response.itemList[i].data.text,
                        "", "", 0, "", "", "", "", "", "", response.itemList[i].type,
                        response.nextPageUrl
                    )
                    list.add(recData)
                }

                if (response.itemList[i].type == "followCard" || response.itemList[i].type == "autoPlayFollowCard") {
                    val recData = RecData(
                        "",
                        response.itemList[i].data.content.data.cover.feed,
                        response.itemList[i].data.content.data.playUrl,
                        response.itemList[i].data.content.data.duration,
                        response.itemList[i].data.content.data.title,
                        "${response.itemList[i].data.content.data.author.name ?: ""} / #${response.itemList[i].data.content.data.category}",
                        response.itemList[i].data.content.data.description,
                        response.itemList[i].data.content.data.id.toString(),
                        response.itemList[i].data.content.data.cover.blurred,
                        response.itemList[i].data.content.data.author.icon,
                        // "squareCardCollection"
                        response.itemList[i].type,
                        response.nextPageUrl
                    )
                    list.add(recData)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        Result.success(list)
    }

    fun loadMoreDaily(url: String) = fire(Dispatchers.IO) {
        val response = ApiLoad.loadMore(url)
        val length = response.count
        val list = mutableListOf<RecData>()
        try {
            for (i in 0 until length) {
                if (response.itemList[i].type == "textCard" && length > 1) {
                    val recData = RecData(
                        response.itemList[i].data.text,
                        "", "", 0, "", "", "", "", "", "", response.itemList[i].type,
                        response.nextPageUrl
                    )
                    list.add(recData)
                }

                if (response.itemList[i].type == "followCard") {
                    val recData = RecData(
                        "",
                        response.itemList[i].data.content.data.cover.feed,
                        response.itemList[i].data.content.data.playUrl,
                        response.itemList[i].data.content.data.duration,
                        response.itemList[i].data.content.data.title,
                        "${response.itemList[i].data.content.data.author.name ?: ""} / #${response.itemList[i].data.content.data.category}",
                        response.itemList[i].data.content.data.description,
                        response.itemList[i].data.content.data.id.toString(),
                        response.itemList[i].data.content.data.cover.blurred,
                        response.itemList[i].data.content.data.author.icon,
                        response.itemList[i].type,
                        response.nextPageUrl
                    )
                    list.add(recData)
                }
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