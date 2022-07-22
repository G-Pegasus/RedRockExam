package redrock.tongji.redrockexam.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import redrock.tongji.lib_base.base.BaseViewModel
import redrock.tongji.redrockexam.bean.RecData
import redrock.tongji.redrockexam.logic.repository.RecRepo

/**
 * @Author Tongji
 * @Description
 * @Date create in 2022/7/17 19:11
 */
class RecommendViewModel : BaseViewModel() {

    var listData = mutableListOf<RecData>()
    private val recLiveData = MutableLiveData<Int>()
    val recPathData = Transformations.switchMap(recLiveData) { RecRepo.loadRec() }
    fun loadRec() {
        recLiveData.value = 0
    }

    private val moreLiveData = MutableLiveData<String>()
    val morePathData = Transformations.switchMap(moreLiveData) {url-> RecRepo.loadMore(url) }
    fun loadMore(url:String) {
        moreLiveData.value = url
    }

}