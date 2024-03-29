package redrock.tongji.redrockexam.model.repository

import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext
import androidx.lifecycle.liveData
import redrock.tongji.redrockexam.bean.TagInfoData
import redrock.tongji.redrockexam.bean.TagRecData
import redrock.tongji.redrockexam.model.network.ApiLoad

/**
 * @Author Tongji
 * @Description
 * @Date create in 2022/7/15 19:54
 */
object TagRepo {

    fun loadTagInfo(id: String) = fire(Dispatchers.IO) {
        val response = ApiLoad.loadTagInfo(id)
        val tagInfoData = TagInfoData(
            response.tagInfo.name,
            response.tagInfo.description ?: "",
            response.tagInfo.headerImage
        )
        Result.success(tagInfoData)
    }

    fun loadTagRec(id: String) = fire(Dispatchers.IO) {
        val response = ApiLoad.loadTagRec(id)
        val length = response.count
        val list = mutableListOf<TagRecData>()
        for (i in 0 until length) {

            if (response.itemList[i].type == "textCard" && length > 1) {
                val tagRecData = TagRecData(
                    response.itemList[i].data.text,
                    "", "", 0, "", "", "", "", "", "",
                    "textCard",
                    response.nextPageUrl, 0, 0, 0, 0
                )
                list.add(tagRecData)
            }

            if (response.itemList[i].type == "videoSmallCard") {
                val tagRecData = TagRecData(
                    "",
                    response.itemList[i].data.cover.feed,
                    response.itemList[i].data.playUrl,
                    response.itemList[i].data.duration,
                    response.itemList[i].data.title,
                    response.itemList[i].data.category,
                    response.itemList[i].data.description,
                    response.itemList[i].data.id.toString(),
                    response.itemList[i].data.cover.blurred,
                    response.itemList[i].data.author.icon,
                    response.itemList[i].type,
                    response.nextPageUrl, 0, 0, 0, 0
                )
                list.add(tagRecData)
            }

            if (response.itemList[i].type == "followCard" || response.itemList[i].type == "autoPlayFollowCard") {
                val tagRecData = TagRecData(
                    "",
                    response.itemList[i].data.content.data.cover.feed,
                    response.itemList[i].data.content.data.playUrl,
                    response.itemList[i].data.content.data.duration,
                    response.itemList[i].data.content.data.title,
                    response.itemList[i].data.content.data.author.name,
                    response.itemList[i].data.content.data.description,
                    response.itemList[i].data.content.data.id.toString(),
                    response.itemList[i].data.content.data.cover.blurred,
                    response.itemList[i].data.content.data.author.icon,
                    "followCard",
                    response.nextPageUrl,
                    response.itemList[i].data.content.data.consumption.collectionCount,
                    response.itemList[i].data.content.data.consumption.replyCount,
                    response.itemList[i].data.content.data.consumption.shareCount,
                    response.itemList[i].data.content.data.date
                )
                list.add(tagRecData)
            }
        }

        list.add(TagRecData(
            "滑到底啦~",
            "", "", 0, "", "", "", "", "", "",
            "textCard",
            response.nextPageUrl, 0, 0, 0, 0
        ))
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