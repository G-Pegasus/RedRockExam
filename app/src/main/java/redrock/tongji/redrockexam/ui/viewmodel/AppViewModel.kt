package redrock.tongji.redrockexam.ui.viewmodel

import androidx.lifecycle.ViewModel
import redrock.tongji.redrockexam.App
import redrock.tongji.redrockexam.util.ColorUtil
import redrock.tongji.redrockexam.util.EventLiveData

/**
 * @Author Tongji
 * @Description
 * @Date create in 2022/7/17 14:50
 */
class AppViewModel : ViewModel() {

    //App主题颜色
    var appColor = EventLiveData<Int>()

    init {
        //默认值颜色
        appColor.value = ColorUtil.getColor(App.context)
    }
}