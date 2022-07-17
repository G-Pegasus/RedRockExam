package redrock.tongji.redrockexam.ui.fragment

import redrock.tongji.lib_base.base.BaseBindVMFragment
import redrock.tongji.redrockexam.R
import redrock.tongji.redrockexam.databinding.FragmentRecBinding
import redrock.tongji.redrockexam.ui.viewmodel.RecommendViewModel

/**
 * @Author Tongji
 * @Description
 * @Date create in 2022/7/17 19:10
 */
class RecommendFragment : BaseBindVMFragment<RecommendViewModel, FragmentRecBinding>() {

    override val getLayoutRes: Int
        get() = R.layout.fragment_rec

    override fun initView() {

    }

}