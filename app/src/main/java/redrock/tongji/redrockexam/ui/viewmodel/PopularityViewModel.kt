package redrock.tongji.redrockexam.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import redrock.tongji.lib_base.base.BaseViewModel
import redrock.tongji.redrockexam.bean.RecData
import redrock.tongji.redrockexam.model.repository.PopularRepo

/**
 * @Author Tongji
 * @Description
 * @Date create in 2022/7/19 13:34
 */
class PopularityViewModel : BaseViewModel() {

    var listData = mutableListOf<RecData>()
    private val popularLiveData = MutableLiveData<Int>()
    val popularPathData = Transformations.switchMap(popularLiveData) { PopularRepo.loadPopularRank() }
    fun loadPopularData() {
        popularLiveData.value = 0
    }

}