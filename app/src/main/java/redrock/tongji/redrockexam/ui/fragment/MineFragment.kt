package redrock.tongji.redrockexam.ui.fragment

import redrock.tongji.lib_base.base.BaseBindVMFragment
import redrock.tongji.redrockexam.R
import redrock.tongji.redrockexam.databinding.FragmentMineBinding
import redrock.tongji.redrockexam.ui.viewmodel.MineViewModel

/**
 * @Author Tongji
 * @Description
 * @Date create in 2022/7/14 21:24
 */
class MineFragment : BaseBindVMFragment<MineViewModel, FragmentMineBinding>() {
    override val getLayoutRes: Int
        get() = R.layout.fragment_mine

    override fun initView() {

    }

    override fun lazyLoadData() {

    }

}