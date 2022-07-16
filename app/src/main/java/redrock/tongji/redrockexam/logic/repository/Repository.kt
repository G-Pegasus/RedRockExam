@file:Suppress("SameParameterValue")

package redrock.tongji.redrockexam.logic.repository

import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext
import androidx.lifecycle.liveData
import redrock.tongji.redrockexam.bean.RecData
import redrock.tongji.redrockexam.bean.TagInfoData
import redrock.tongji.redrockexam.bean.TagRecData
import redrock.tongji.redrockexam.logic.model.ApiLoad

/**
 * @Author Tongji
 * @Description
 * @Date create in 2022/7/15 19:54
 */
object Repository {

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
                            "${s.itemList[j].data.content.data.author.name} / #${s.itemList[j].data.content.data.category}",
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
                    "${response.itemList[i].data.content.data.author.name} / #${response.itemList[i].data.content.data.category}",
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
                    "${response.itemList[i].data.author.name} / #${response.itemList[i].data.category}",
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

    fun loadRec() = fire(Dispatchers.IO) {
        val response = ApiLoad.loadRec()
        val length = response.count
        val list = mutableListOf<RecData>()
        try {
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
                                "${s.itemList[j].data.content.data.author.name} / #${s.itemList[j].data.content.data.category}",
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
                if (response.itemList[i].type == "textCard" && response.itemList[i].data.type != "footer2" && length > 1) {
                    val recData = RecData(
                        response.itemList[i].data.text,
                        "", "", 0, "", "", "", "", "", "", response.itemList[i].type,
                        response.nextPageUrl
                    )
                    list.add(recData)
                }

                if (response.itemList[i].type == "banner2") {
                    val recData = RecData(
                        "",
                        response.itemList[i].data.image,
                        "",
                        0,
                        response.itemList[i].data.header.title,
                        "广告",
                        response.itemList[i].data.header.description,
                        "",
                        "",
                        response.itemList[i].data.header.icon,
                        "squareCardCollection",
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
                        "${response.itemList[i].data.content.data.author.name} / #${response.itemList[i].data.content.data.category}",
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
                        "${response.itemList[i].data.author.name} / #${response.itemList[i].data.category}",
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


        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }

        Result.success(list)
    }

    fun loadDaily() = fire(Dispatchers.IO) {
        val response = ApiLoad.loadDaily()
        val length = response.count
        val list = mutableListOf<RecData>()
        try {
            for (i in 0 until length) {
                if (response.itemList[i].type == "textCard" && response.itemList[i].data.type != "footer2" && response.itemList[i].data.type != "header7" && length > 1) {
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
                        "squareCardCollection",
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
                if (response.itemList[i].type == "textCard" && response.itemList[i].data.type != "footer2" && response.itemList[i].data.type != "header7" && length > 1) {
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
                        "squareCardCollection",
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

    fun loadMoreTagRec(url: String) = fire(Dispatchers.IO) {
        val response = ApiLoad.loadMoreTagRec(url)
        val length = response.count
        val list = mutableListOf<TagRecData>()
        for (i in 0 until length) {
            if (response.itemList[i].type == "textCard" && response.itemList[i].data.type != "footer2" && length > 1) {
                val tagRecData = TagRecData(
                    response.itemList[i].data.text,
                    "", "", 0, "", "", "", "", "", "", response.itemList[i].type,
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
                    "${response.itemList[i].data.author.name} / #${response.itemList[i].data.category}",
                    response.itemList[i].data.description,
                    response.itemList[i].data.id.toString(),
                    response.itemList[i].data.cover.blurred,
                    response.itemList[i].data.author.icon,
                    response.itemList[i].type,
                    response.nextPageUrl, 0, 0, 0, 0
                )
                list.add(tagRecData)
            }

            if (response.itemList[i].type == "followCard") {
                val tagRecData = TagRecData(
                    "",
                    response.itemList[i].data.content.data.cover.feed,
                    response.itemList[i].data.content.data.playUrl,
                    response.itemList[i].data.content.data.duration,
                    response.itemList[i].data.content.data.title,
                    "${response.itemList[i].data.content.data.author.name} / #${response.itemList[i].data.content.data.category}",
                    response.itemList[i].data.content.data.description,
                    response.itemList[i].data.content.data.id.toString(),
                    response.itemList[i].data.content.data.cover.blurred,
                    response.itemList[i].data.content.data.author.icon,
                    response.itemList[i].type,
                    response.nextPageUrl, 0, 0, 0, 0
                )
                list.add(tagRecData)
            }

            if (response.itemList[i].type == "autoPlayFollowCard") {
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
                    response.itemList[i].type, response.nextPageUrl,
                    response.itemList[i].data.content.data.consumption.collectionCount,
                    response.itemList[i].data.content.data.consumption.replyCount,
                    response.itemList[i].data.content.data.consumption.shareCount,
                    response.itemList[i].data.content.data.date
                )
                list.add(tagRecData)
            }
        }

        Result.success(list)
    }

    fun loadTagRec(id: String) = fire(Dispatchers.IO) {
        val response = ApiLoad.loadTagRec(id)
        val length = response.count
        val list = mutableListOf<TagRecData>()
        for (i in 0 until length) {
            if (response.itemList[i].type == "textCard" && response.itemList[i].data.type != "footer2" && length > 1) {
                val tagRecData = TagRecData(
                    response.itemList[i].data.text,
                    "", "", 0, "", "", "", "", "", "", response.itemList[i].type,
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
                    "${response.itemList[i].data.author.name} / #${response.itemList[i].data.category}",
                    response.itemList[i].data.description,
                    response.itemList[i].data.id.toString(),
                    response.itemList[i].data.cover.blurred,
                    response.itemList[i].data.author.icon,
                    response.itemList[i].type,
                    response.nextPageUrl, 0, 0, 0, 0
                )
                list.add(tagRecData)
            }

            if (response.itemList[i].type == "followCard") {
                val tagRecData = TagRecData(
                    "",
                    response.itemList[i].data.content.data.cover.feed,
                    response.itemList[i].data.content.data.playUrl,
                    response.itemList[i].data.content.data.duration,
                    response.itemList[i].data.content.data.title,
                    "${response.itemList[i].data.content.data.author.name} / #${response.itemList[i].data.content.data.category}",
                    response.itemList[i].data.content.data.description,
                    response.itemList[i].data.content.data.id.toString(),
                    response.itemList[i].data.content.data.cover.blurred,
                    response.itemList[i].data.content.data.author.icon,
                    response.itemList[i].type,
                    response.nextPageUrl, 0, 0, 0, 0
                )
                list.add(tagRecData)
            }

            if (response.itemList[i].type == "autoPlayFollowCard") {
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
                    response.itemList[i].type, response.nextPageUrl,
                    response.itemList[i].data.content.data.consumption.collectionCount,
                    response.itemList[i].data.content.data.consumption.replyCount,
                    response.itemList[i].data.content.data.consumption.shareCount,
                    response.itemList[i].data.content.data.date
                )
                list.add(tagRecData)
            }
        }

        Result.success(list)
    }

    fun loadTagInfo(id: String) = fire(Dispatchers.IO) {
        val response = ApiLoad.loadTagInfo(id)
        val tagInfoData = TagInfoData(
            response.tagInfo.name,
            response.tagInfo.description ?: "",
            response.tagInfo.headerImage
        )
        Result.success(tagInfoData)
    }

    private fun <T> fire(context: CoroutineContext, block: suspend () -> Result<T>) =
        liveData<Result<T>>(context) {
            val result = try {
                block()
            } catch (e: Exception) {
                Result.failure<T>(e)
            }
            emit(result)
        }
}