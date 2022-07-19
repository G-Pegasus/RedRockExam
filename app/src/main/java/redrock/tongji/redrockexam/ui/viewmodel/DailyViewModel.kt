package redrock.tongji.redrockexam.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import redrock.tongji.lib_base.base.BaseViewModel
import redrock.tongji.redrockexam.bean.RecData
import redrock.tongji.redrockexam.logic.repository.DailyRepo
import redrock.tongji.redrockexam.logic.repository.Repository

/**
 * @Author Tongji
 * @Description
 * @Date create in 2022/7/16 10:52
 */
class DailyViewModel : BaseViewModel() {

    var listData = mutableListOf<RecData>()
    private val dailyLiveData = MutableLiveData<Int>()
    val dailyPathData = Transformations.switchMap(dailyLiveData) { DailyRepo.loadDaily() }
    fun loadDaily() {
        dailyLiveData.value = 0
    }

    private val moreLiveData = MutableLiveData<String>()
    val morePathData = Transformations.switchMap(moreLiveData) { url -> DailyRepo.loadMoreDaily(url) }
    fun loadMore(url: String) {
        moreLiveData.value = url
    }

}