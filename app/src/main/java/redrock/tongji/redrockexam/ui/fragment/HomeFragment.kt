package redrock.tongji.redrockexam.ui.fragment

import redrock.tongji.lib_base.base.BaseBindVMFragment
import redrock.tongji.redrockexam.R
import redrock.tongji.redrockexam.databinding.FragmentHomeBinding
import redrock.tongji.redrockexam.ui.viewmodel.HomeViewModel

/**
 * @Author Tongji
 * @Description
 * @Date create in 2022/7/14 21:23
 */
class HomeFragment : BaseBindVMFragment<HomeViewModel, FragmentHomeBinding>() {
    override val getLayoutRes: Int
        get() = R.layout.fragment_home

    override fun initView() {

    }

    override fun lazyLoadData() {

    }

}