package redrock.tongji.redrockexam.model.repository

import androidx.lifecycle.liveData
import kotlinx.coroutines.Dispatchers
import redrock.tongji.redrockexam.bean.DiscoveryData
import redrock.tongji.redrockexam.model.network.ApiLoad
import kotlin.coroutines.CoroutineContext

/**
 * @Author Tongji
 * @Description
 * @Date create in 2022/7/19 13:48
 */
object DiscoveryRepo {

    // 获取轮播图数据
    fun loadBanner() = fire(Dispatchers.IO) {
        val response = ApiLoad.loadDiscovery()
        val length = response.itemList[0].data.count
        val list = mutableListOf<String>()
        for (i in 0 until length) {
            list.add(response.itemList[0].data.itemList[i].data.image)
        }

        Result.success(list)
    }

    fun loadSpecial() = fire(Dispatchers.IO) {
        val response = ApiLoad.loadDiscovery()
        val len = response.count
        val list = mutableListOf<DiscoveryData>()
        for (j in 0 until len) {
            if (response.itemList[j].type == "specialSquareCardCollection") {
                val length = response.itemList[j].data.count
                for (i in 0 until length) {
                    val specialData = DiscoveryData(
                        response.itemList[j].data.itemList[i].data.image,
                        response.itemList[j].data.itemList[i].data.title,
                        response.itemList[j].data.itemList[i].data.id.toString(),
                        "", "", 0, "", "", "",
                        "specialSquareCardCollection"
                    )
                    list.add(specialData)
                }
                break
            }
        }
        Result.success(list)
    }

    fun loadDiscovery() = fire(Dispatchers.IO) {
        val response = ApiLoad.loadDiscovery()
        val length = response.count
        val list = mutableListOf<DiscoveryData>()
        try {
            for (j in 0 until length) {
                if (response.itemList[j].type == "columnCardList") {
                    val count = response.itemList[2].data.count
                    for (i in 0 until count) {
                        try {
                            val columnData = DiscoveryData(
                                response.itemList[j].data.itemList[i].data.image,
                                response.itemList[j].data.itemList[i].data.title,
                                "", "", "", 0, "", "", "",
                                "columnCardList"
                            )
                            list.add(columnData)
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                }
                if (response.itemList[j].type == "textCard") {
                    val textData = DiscoveryData(
                        "", "", "",
                        response.itemList[j].data.text,
                        "", 0, "", "", "",
                        "textCard"
                    )
                    list.add(textData)
                }
                if (response.itemList[j].type == "videoSmallCard") {
                    val videoData = DiscoveryData(
                        response.itemList[j].data.cover.feed,
                        response.itemList[j].data.title,
                        response.itemList[j].data.id.toString(),
                        "",
                        response.itemList[j].data.playUrl,
                        response.itemList[j].data.duration,
                        response.itemList[j].data.category,
                        response.itemList[j].data.description,
                        response.itemList[j].data.cover.blurred,
                        "videoSmallCard"
                    )
                    list.add(videoData)
                }
                if (response.itemList[j].type == "briefCard") {
                    val briefData = DiscoveryData(
                        response.itemList[j].data.icon,
                        response.itemList[j].data.title,
                        "", "", "", 0, "",
                        response.itemList[j].data.description, "",
                        response.itemList[j].type
                    )
                    list.add(briefData)
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