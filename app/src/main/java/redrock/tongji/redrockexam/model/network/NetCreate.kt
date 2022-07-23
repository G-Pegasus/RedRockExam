package redrock.tongji.redrockexam.model.network

import redrock.tongji.redrockexam.util.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * @Author Tongji
 * @Description
 * @Date create in 2022/7/14 19:11
 */
object NetCreate {

    private fun initRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val apiService: ApiService = initRetrofit().create(ApiService::class.java)
}