package redrock.tongji.redrockexam.logic.model

import redrock.tongji.redrockexam.ext.await
import redrock.tongji.redrockexam.logic.network.NetCreate

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

    suspend fun loadMoreTagRec(url: String) = NetCreate.apiService.getMoreTagRec(url).await()

    suspend fun loadTagInfo(id: String) = NetCreate.apiService.getTagInfo(id).await()

    suspend fun loadNotify() = NetCreate.apiService.getNotify().await()

    suspend fun loadMoreNotify(url: String) = NetCreate.apiService.getMoreNotify(url).await()
}