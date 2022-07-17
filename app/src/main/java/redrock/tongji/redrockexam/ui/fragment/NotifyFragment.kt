package redrock.tongji.redrockexam.ui.fragment

import redrock.tongji.lib_base.base.BaseBindVMFragment
import redrock.tongji.redrockexam.R
import redrock.tongji.redrockexam.appViewModel
import redrock.tongji.redrockexam.databinding.FragmentNotifyBinding
import redrock.tongji.redrockexam.ui.viewmodel.NotifyViewModel
import redrock.tongji.redrockexam.util.ColorUtil

/**
 * @Author Tongji
 * @Description
 * @Date create in 2022/7/14 21:23
 */
class NotifyFragment : BaseBindVMFragment<NotifyViewModel, FragmentNotifyBinding>() {
    override val getLayoutRes: Int
        get() = R.layout.fragment_notify

    override fun initView() {
        appViewModel.run {
            //监听全局的主题颜色改变
            appColor.observeInFragment(this@NotifyFragment) {
                ColorUtil.setUiTheme(
                    it,
                    mDatabind.includeToolbar,
                    mDatabind.includeList.floatbtn,
                    mDatabind.includeList.includeRecyclerview.swipeRefresh
                )
            }
        }
    }

    override fun lazyLoadData() {

    }

}