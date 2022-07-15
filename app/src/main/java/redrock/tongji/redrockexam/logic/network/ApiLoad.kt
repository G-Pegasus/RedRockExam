package redrock.tongji.redrockexam.logic.network

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

/**
 * @Author Tongji
 * @Description
 * @Date create in 2022/7/15 16:20
 */
class ApiLoad {

    suspend fun loadRec() = NetCreate.apiService.getRec().await()

    suspend fun loadMore(url: String) = NetCreate.apiService.getMore(url).await()

    suspend fun loadDaily() = NetCreate.apiService.getDaily().await()

    suspend fun loadTagRec(id: String) = NetCreate.apiService.getTagRec(id).await()

    suspend fun loadMoreTagRec(url: String) = NetCreate.apiService.getMoreTagRec(url).await()

    suspend fun loadTagInfo(id: String) = NetCreate.apiService.getTagInfo(id).await()

    private suspend fun <T> Call<T>.await(): T {
        return suspendCoroutine { continuation ->
            enqueue(object : Callback<T> {
                override fun onResponse(call: Call<T>, response: Response<T>) {
                    val body = response.body()
                    if (body != null) continuation.resume(body)
                    else continuation.resumeWithException(
                        RuntimeException("response is null")
                    )
                }

                override fun onFailure(call: Call<T>, t: Throwable) {
                    Log.e("Internet error:", t.toString())
                    continuation.resumeWithException(t)
                }
            })
        }
    }
}