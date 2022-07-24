package redrock.tongji.redrockexam.ui.viewmodel

import androidx.lifecycle.LiveData
import redrock.tongji.lib_base.base.BaseViewModel
import redrock.tongji.redrockexam.bean.VideoInfoBean
import redrock.tongji.redrockexam.model.repository.LikeRepo

/**
 * @Author Tongji
 * @Description
 * @Date create in 2022/7/24 13:05
 */
class LikeViewModel : BaseViewModel() {

    var likeLiveData : LiveData<List<VideoInfoBean>> = LikeRepo.loadMyLike()
}