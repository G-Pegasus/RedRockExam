package redrock.tongji.redrockexam.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import redrock.tongji.lib_base.base.BaseViewModel
import redrock.tongji.redrockexam.bean.TagRecData
import redrock.tongji.redrockexam.model.repository.TagRepo

/**
 * @Author Tongji
 * @Description
 * @Date create in 2022/7/21 22:03
 */
class SpecialViewModel : BaseViewModel() {

    var listData = mutableListOf<TagRecData>()

    private val specialLiveData = MutableLiveData<String>()

    val specialPathData = Transformations.switchMap(specialLiveData) { id ->
        TagRepo.loadTagInfo(id)
    }

    val specialRecPathData = Transformations.switchMap(specialLiveData) { id ->
        TagRepo.loadTagRec(id)
    }

    fun loadTagInfo(id: String) {
        specialLiveData.value = id
    }
}