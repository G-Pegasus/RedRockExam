package redrock.tongji.redrockexam.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import redrock.tongji.lib_base.base.BaseViewModel
import redrock.tongji.redrockexam.bean.DiscoveryData
import redrock.tongji.redrockexam.model.repository.DiscoveryRepo

/**
 * @Author Tongji
 * @Description 发现页面
 * @Date create in 2022/7/19 13:33
 */
class DiscoveryViewModel : BaseViewModel() {

    val listData = mutableListOf<DiscoveryData>()

    private val discoveryLiveData = MutableLiveData<Int>()

    val bannerPathData = Transformations.switchMap(discoveryLiveData) { DiscoveryRepo.loadBanner() }

    val specialPathData = Transformations.switchMap(discoveryLiveData) { DiscoveryRepo.loadSpecial() }

    val otherPathData = Transformations.switchMap(discoveryLiveData) { DiscoveryRepo.loadDiscovery() }

    fun loadBanner() {
        discoveryLiveData.value = 0
    }

}