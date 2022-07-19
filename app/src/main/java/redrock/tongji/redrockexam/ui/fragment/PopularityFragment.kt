package redrock.tongji.redrockexam.ui.fragment

import redrock.tongji.lib_base.base.BaseBindVMFragment
import redrock.tongji.redrockexam.R
import redrock.tongji.redrockexam.databinding.FragmentPopularityBinding
import redrock.tongji.redrockexam.ui.viewmodel.PopularityViewModel

/**
 * @Author Tongji
 * @Description
 * @Date create in 2022/7/19 13:27
 */
class PopularityFragment : BaseBindVMFragment<PopularityViewModel, FragmentPopularityBinding>() {

    override val getLayoutRes: Int
        get() = R.layout.fragment_popularity

    override fun initView() {

    }

}