package redrock.tongji.redrockexam.logic.network

import redrock.tongji.redrockexam.util.EventLiveData

/**
 * @Author Tongji
 * @Description
 * @Date create in 2022/7/17 12:08
 */
class NetStateManager private constructor() {

    val mNetworkStateCallback =
        EventLiveData<NetState>()

    companion object {
        val instance: NetStateManager by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            NetStateManager()
        }
    }

}