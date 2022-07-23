package redrock.tongji.redrockexam.model.repository

import androidx.lifecycle.liveData
import kotlinx.coroutines.Dispatchers
import redrock.tongji.redrockexam.bean.RecData
import redrock.tongji.redrockexam.model.network.ApiLoad
import java.lang.Exception
import kotlin.coroutines.CoroutineContext

/**
 * @Author Tongji
 * @Description 推荐界面仓库
 * @Date create in 2022/7/19 10:14
 */
object RecRepo {

    fun loadRec() = fire(Dispatchers.IO) {
        val response = ApiLoad.loadRec()
        val length = response.count
        val list = mutableListOf<RecData>()
        try {
            for (i in 0 until length) {
                if (response.itemList[i].type == "squareCardCollection") {
                    val collection = response.itemList[i].data
                    val len = collection.count
                    for (j in 0 until len) {
                        try {
                            val recData = RecData(
                                response.itemList[i].data.header.title,
                                collection.itemList[j].data.content.data.cover.feed,
                                collection.itemList[j].data.content.data.playUrl,
                                collection.itemList[j].data.content.data.duration,
                                collection.itemList[j].data.content.data.title,
                                collection.itemList[j].data.content.data.category,
                                collection.itemList[j].data.content.data.description,
                                collection.itemList[j].data.content.data.id.toString(),
                                collection.itemList[j].data.content.data.cover.blurred,
                                collection.itemList[j].data.content.data.author.icon,
                                "squareCardCollection",
                                response.nextPageUrl
                            )
                            list.add(recData)
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }

                    }
                }
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
                        response.itemList[i].data.content.data.category,
                        response.itemList[i].data.content.data.description,
                        response.itemList[i].data.content.data.id.toString(),
                        response.itemList[i].data.content.data.cover.blurred,
                        response.itemList[i].data.content.data.author.icon,
                        response.itemList[i].type,
                        response.nextPageUrl
                    )
                    list.add(recData)
                }
                if (response.itemList[i].type == "videoSmallCard") {
                    val recData = RecData(
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

    fun loadMore(url: String) = fire(Dispatchers.IO) {
        val response = ApiLoad.loadMore(url)
        val length = response.count
        val list = mutableListOf<RecData>()
        for (i in 0 until length) {
            if (response.itemList[i].type == "squareCardCollection") {
                val s = response.itemList[i].data
                val len = s.count
                for (j in 0 until len) {
                    try {
                        val recData = RecData(
                            response.itemList[i].data.header.title,
                            s.itemList[j].data.content.data.cover.feed,
                            s.itemList[j].data.content.data.playUrl,
                            s.itemList[j].data.content.data.duration,
                            s.itemList[j].data.content.data.title,
                            s.itemList[j].data.content.data.category,
                            s.itemList[j].data.content.data.description,
                            s.itemList[j].data.content.data.id.toString(),
                            s.itemList[j].data.content.data.cover.blurred,
                            s.itemList[j].data.content.data.author.icon,
                            "squareCardCollection",
                            response.nextPageUrl
                        )
                        list.add(recData)
                    } catch (e: java.lang.Exception) {
                        e.printStackTrace()
                    }

                }
            }
            if (response.itemList[i].type == "textCard") {
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
                    response.itemList[i].data.content.data.category,
                    response.itemList[i].data.content.data.description,
                    response.itemList[i].data.content.data.id.toString(),
                    response.itemList[i].data.content.data.cover.blurred,
                    response.itemList[i].data.content.data.author.icon,
                    response.itemList[i].type,
                    response.nextPageUrl
                )
                list.add(recData)
            }
            if (response.itemList[i].type == "videoSmallCard") {
                val recData = RecData(
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
                    response.nextPageUrl
                )
                list.add(recData)
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