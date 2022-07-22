package redrock.tongji.redrockexam.logic.model

import redrock.tongji.redrockexam.ext.await
import redrock.tongji.redrockexam.logic.network.NetCreate
import retrofit2.http.Query

/**
 * @Author Tongji
 * @Description
 * @Date create in 2022/7/15 16:20
 */
object ApiLoad {

    suspend fun loadRec() = NetCreate.apiService.getRec().await()

    suspend fun loadMore(url: String) = NetCreate.apiService.getMore(url).await()

    suspend fun loadDaily() = NetCreate.apiService.getDaily().await()

    suspend fun loadTagRec(id: String) = NetCreate.apiService.getTagRec(id).await()

    suspend fun loadTagInfo(id: String) = NetCreate.apiService.getTagInfo(id).await()

    suspend fun loadNotify() = NetCreate.apiService.getNotify().await()

    suspend fun loadMoreNotify(url: String) = NetCreate.apiService.getMoreNotify(url).await()

    suspend fun loadDiscovery() = NetCreate.apiService.getDiscovery().await()

    suspend fun loadWeeklyRank() = NetCreate.apiService.getWeeklyRankList().await()

    suspend fun loadHistoricalRank() = NetCreate.apiService.getHistoricalRankList().await()

    suspend fun loadRelated(query: String) = NetCreate.apiService.getRelated(query).await()

    suspend fun loadComments(query: String) = NetCreate.apiService.getReply(query).await()
}