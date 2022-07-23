package redrock.tongji.redrockexam.model.repository

import androidx.lifecycle.liveData
import kotlinx.coroutines.Dispatchers
import redrock.tongji.redrockexam.bean.RecData
import redrock.tongji.redrockexam.model.network.ApiLoad
import kotlin.coroutines.CoroutineContext

/**
 * @Author Tongji
 * @Description
 * @Date create in 2022/7/20 10:35
 */
object PopularRepo {

    fun loadPopularRank() = fire(Dispatchers.IO) {
        val response1 = ApiLoad.loadWeeklyRank()
        val length1 = response1.count
        val list = mutableListOf<RecData>()

        val textWeekly = RecData(
            "本周榜单",
            "", "", 0, "", "",
            "", "", "", "",
            "textCard", ""
        )
        list.add(textWeekly)

        for (i in 0 until length1) {
            val popData1 = RecData(
                "",
                response1.itemList[i].data.cover.feed,
                response1.itemList[i].data.playUrl,
                response1.itemList[i].data.duration,
                response1.itemList[i].data.title,
                response1.itemList[i].data.author.name,
                response1.itemList[i].data.description,
                response1.itemList[i].data.id.toString(),
                "",
                response1.itemList[i].data.author.icon,
                "followCard", ""
            )
            list.add(popData1)
        }

        val textHistory = RecData(
            "历史榜单",
            "", "", 0, "", "",
            "", "", "", "",
            "textCard", ""
        )
        list.add(textHistory)

        val response2 = ApiLoad.loadHistoricalRank()
        val length2 = response2.count
        for (i in 0 until length2) {
            val popData2 = RecData(
                "",
                response2.itemList[i].data.cover.feed,
                response2.itemList[i].data.playUrl,
                response2.itemList[i].data.duration,
                response2.itemList[i].data.title,
                response2.itemList[i].data.author.name,
                response2.itemList[i].data.description,
                response2.itemList[i].data.id.toString(),
                "",
                response2.itemList[i].data.author.icon,
                "followCard", ""
            )
            list.add(popData2)
        }

        val textEnd = RecData(
            "滑到底啦~",
            "", "", 0, "", "",
            "", "", "", "",
            "textCard", ""
        )
        list.add(textEnd)

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