package redrock.tongji.redrockexam.logic.network

import redrock.tongji.redrockexam.bean.*
import retrofit2.Call
import retrofit2.http.*

/**
 * @Author Tongji
 * @Description
 * @Date create in 2022/7/14 19:07
 */
interface ApiService {
    // 日报 推荐
    @GET("v5/index/tab/allRec")
    fun getRec(): Call<RecBean>

    @GET
    fun getMore(@Url url: String): Call<RecBean>

    @GET("v5/index/tab/feed")
    fun getDaily(
        @Query("udid") udid: String = "20d2c76ac00b4b2ea4fe0249eafb6dc470d782a5",
        @Query("vc") vc: String = "6030022",
        @Query("vn") vn: String = "6.3.2",
        @Query("size") size: String = "1080X1920",
        @Query("deviceModel") deviceModel: String = "MI%205s%20Plus",
        @Query("first_channel") first_channel: String = "xiaomi",
        @Query("last_channel") last_channel: String = "xiaomi",
        @Query("system_version_code") system_version_code: String = "26"
    ): Call<RecBean>

    // 详情推荐
    @GET("v6/tag/index")
    fun getTagInfo(@Query("id") id: String): Call<TagInfoBean>

    @GET("v1/tag/videos")
    fun getTagRec(
        @Query("id") id: String,
        @Query("udid") udid: String = "20d2c76ac00b4b2ea4fe0249eafb6dc470d782a5",
        @Query("vc") vc: String = "6030022",
        @Query("vn") vn: String = "6.3.2",
        @Query("size") size: String = "1080X1920",
        @Query("deviceModel") deviceModel: String = "MI%205s%20Plus",
        @Query("first_channel") first_channel: String = "xiaomi",
        @Query("last_channel") last_channel: String = "xiaomi",
        @Query("system_version_code") system_version_code: String = "26"
    ): Call<TagBean>

    // 获取通知
    @GET("v3/messages")
    fun getNotify(
        @Query("udid") udid: String = "20d2c76ac00b4b2ea4fe0249eafb6dc470d782a5",
        @Query("vc") vc: String = "6030022",
        @Query("vn") vn: String = "6.3.2",
        @Query("size") size: String = "1080X1920",
        @Query("deviceModel") deviceModel: String = "MI%205s%20Plus",
        @Query("first_channel") first_channel: String = "xiaomi",
        @Query("last_channel") last_channel: String = "xiaomi",
        @Query("system_version_code") system_version_code: String = "26"
    ): Call<NotifyBean>

    @GET
    fun getMoreNotify(@Url url: String): Call<NotifyBean>

    // 获取发现页数据
    @GET("v7/index/tab/discovery")
    fun getDiscovery(
        @Query("udid") udid: String = "20d2c76ac00b4b2ea4fe0249eafb6dc470d782a5",
        @Query("vc") vc: String = "6030022",
        @Query("vn") vn: String = "6.3.2",
        @Query("size") size: String = "1080X1920",
        @Query("deviceModel") deviceModel: String = "MI%205s%20Plus",
        @Query("first_channel") first_channel: String = "xiaomi",
        @Query("last_channel") last_channel: String = "xiaomi",
        @Query("system_version_code") system_version_code: String = "26"
    ): Call<DiscoveryBean>

    // 获取历史排行
    @GET("v4/rankList/videos?strategy=historical")
    fun getHistoricalRankList(): Call<RecBean>

    // 获取周排行
    @GET("v4/rankList/videos?strategy=weekly")
    fun getWeeklyRankList(): Call<RecBean>

    // 获取相关视频
    @GET("v4/video/related")
    fun getRelated(@Query("id") id: String): Call<RelatedBean>

    // 获取评论
    @GET("v2/replies/video")
    fun getReply(@Query("videoId") videoId: String): Call<CommentBean>

    // 获取广场界面数据
    @GET("v7/community/tab/rec")
    fun getSquare(): Call<SquareBean>

    // 获取更多数据
    @GET
    fun getMoreSquare(@Url url: String): Call<SquareBean>
}