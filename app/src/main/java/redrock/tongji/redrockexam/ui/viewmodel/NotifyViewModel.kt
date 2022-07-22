package redrock.tongji.redrockexam.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import redrock.tongji.lib_base.base.BaseViewModel
import redrock.tongji.redrockexam.bean.NotifyData
import redrock.tongji.redrockexam.logic.repository.NotifyRepo

/**
 * @Author Tongji
 * @Description
 * @Date create in 2022/7/15 9:41
 */
class NotifyViewModel : BaseViewModel() {

    val listData = mutableListOf<NotifyData>()
    private val notifyLiveData = MutableLiveData<Int>()
    val notifyPathData =
        Transformations.switchMap(notifyLiveData) { NotifyRepo.loadNotify() }

    fun loadNotify() {
        notifyLiveData.value = 0
    }

    private val moreLiveData = MutableLiveData<String>()
    val morePathData =
        Transformations.switchMap(moreLiveData) { url -> NotifyRepo.loadMoreNotify(url) }

    fun loadMore(url: String) {
        moreLiveData.value = url
    }
}