package redrock.tongji.redrockexam.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import redrock.tongji.lib_base.base.BaseViewModel
import redrock.tongji.redrockexam.logic.repository.PlayerRepo

/**
 * @Author Tongji
 * @Description
 * @Date create in 2022/7/16 16:36
 */
class PlayVideoViewModel : BaseViewModel() {

    private val relatedLiveData = MutableLiveData<String>()

    val relatedPathData = Transformations.switchMap(relatedLiveData) { query ->
        PlayerRepo.loadRelated(query) }

    val replyPathData = Transformations.switchMap(relatedLiveData) { query ->
        PlayerRepo.loadReply(query) }

    fun loadRelated(query: String) {
        relatedLiveData.value = query
    }
}