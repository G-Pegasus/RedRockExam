package redrock.tongji.redrockexam.ui.fragment

import redrock.tongji.lib_base.base.BaseBindVMFragment
import redrock.tongji.redrockexam.R
import redrock.tongji.redrockexam.appViewModel
import redrock.tongji.redrockexam.databinding.FragmentCommunityBinding
import redrock.tongji.redrockexam.ui.viewmodel.CommunityViewModel
import redrock.tongji.redrockexam.util.ColorUtil

/**
 * @Author Tongji
 * @Description
 * @Date create in 2022/7/14 21:23
 */
class CommunityFragment : BaseBindVMFragment<CommunityViewModel, FragmentCommunityBinding>() {
    override val getLayoutRes: Int
        get() = R.layout.fragment_community

    override fun initView() {
        appViewModel.run {
            //监听全局的主题颜色改变
            appColor.observeInFragment(this@CommunityFragment) {
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