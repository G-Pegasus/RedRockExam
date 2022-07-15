package redrock.tongji.redrockexam.ui.fragment

import redrock.tongji.lib_base.base.BaseBindVMFragment
import redrock.tongji.redrockexam.R
import redrock.tongji.redrockexam.databinding.FragmentCommunityBinding
import redrock.tongji.redrockexam.ui.viewmodel.CommunityViewModel

/**
 * @Author Tongji
 * @Description
 * @Date create in 2022/7/14 21:23
 */
class CommunityFragment : BaseBindVMFragment<CommunityViewModel, FragmentCommunityBinding>() {
    override val getLayoutRes: Int
        get() = R.layout.fragment_community

    override fun initView() {

    }

    override fun lazyLoadData() {

    }

}