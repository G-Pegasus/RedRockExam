package redrock.tongji.redrockexam.logic.repository

import androidx.lifecycle.liveData
import kotlinx.coroutines.Dispatchers
import redrock.tongji.redrockexam.bean.SquareData
import redrock.tongji.redrockexam.logic.model.ApiLoad
import kotlin.coroutines.CoroutineContext

/**
 * @Author Tongji
 * @Description
 * @Date create in 2022/7/22 19:12
 */
object SquareRepo {

    fun loadSquare() = fire(Dispatchers.IO) {
        val response = ApiLoad.loadSquare()
        val length = response.count
        val list = mutableListOf<SquareData>()

        try {
            for (i in 0 until length) {
                if (response.itemList[i].type == "communityColumnsCard") {
                    val urlsList = arrayListOf<String>()
                    val urlsSize = response.itemList[i].data.content.data.urls.size
                    for (j in 0 until urlsSize) {
                        urlsList.add(response.itemList[i].data.content.data.urls[j])
                    }
                    val communityData = SquareData(
                        response.itemList[i].data.content.data.cover.feed,
                        response.itemList[i].data.content.data.url,
                        response.itemList[i].data.content.data.description,
                        response.itemList[i].data.content.data.owner.avatar,
                        response.itemList[i].data.content.data.owner.nickname,
                        response.itemList[i].data.content.data.consumption.collectionCount,
                        response.nextPageUrl,
                        urlsList
                    )
                    list.add(communityData)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        Result.success(list)
    }

    fun loadMoreSquare(url: String) = fire(Dispatchers.IO) {
        val response = ApiLoad.loadMoreSquare(url)
        val length = response.count
        val list = mutableListOf<SquareData>()
        for (i in 0 until length) {
            if (response.itemList[i].type == "communityColumnsCard") {
                val communityData = SquareData(
                    response.itemList[i].data.content.data.cover.feed,
                    response.itemList[i].data.content.data.url,
                    response.itemList[i].data.content.data.description,
                    response.itemList[i].data.content.data.owner.avatar,
                    response.itemList[i].data.content.data.owner.nickname,
                    response.itemList[i].data.content.data.consumption.collectionCount,
                    response.nextPageUrl,
                    arrayListOf(response.itemList[i].data.content.data.url)
                )
                list.add(communityData)
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