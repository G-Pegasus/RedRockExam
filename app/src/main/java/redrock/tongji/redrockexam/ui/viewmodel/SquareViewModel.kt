package redrock.tongji.redrockexam.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import redrock.tongji.lib_base.base.BaseViewModel
import redrock.tongji.redrockexam.bean.SquareData
import redrock.tongji.redrockexam.logic.repository.SquareRepo

/**
 * @Author Tongji
 * @Description
 * @Date create in 2022/7/22 19:12
 */
class SquareViewModel : BaseViewModel() {

    val listData = mutableListOf<SquareData>()
    private val squareLiveData = MutableLiveData<Int>()

    val squarePathData =
        Transformations.switchMap(squareLiveData) { SquareRepo.loadSquare() }

    fun loadSquare() {
        squareLiveData.value = 0
    }

    private val moreSquareLiveData = MutableLiveData<String>()

    val morePathData = Transformations.switchMap(moreSquareLiveData) { url -> SquareRepo.loadMoreSquare(url) }

    fun loadMore(url: String) {
        moreSquareLiveData.value = url
    }
}