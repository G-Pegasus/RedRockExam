package redrock.tongji.redrockexam.ui.fragment

import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import redrock.tongji.lib_base.base.BaseBindVMFragment
import redrock.tongji.lib_base.base.BaseViewModel
import redrock.tongji.redrockexam.R
import redrock.tongji.redrockexam.appViewModel
import redrock.tongji.redrockexam.databinding.FragmentHomeBinding
import redrock.tongji.redrockexam.ext.init
import redrock.tongji.redrockexam.ext.initMain
import redrock.tongji.redrockexam.ext.setUiTheme

/**
 * @Author Tongji
 * @Description
 * @Date create in 2022/7/14 21:23
 */
class HomeFragment : BaseBindVMFragment<BaseViewModel, FragmentHomeBinding>() {

    override val getLayoutRes: Int
        get() = R.layout.fragment_home

    override fun initView() {
        val fragments = ArrayList<Fragment>()
        val tabTitles = arrayListOf("推荐", "日报")
        fragments.add(RecommendFragment())
        fragments.add(DailyFragment())
        mDatabind.vpHome.init(this, fragments)
        mDatabind.vpHome.offscreenPageLimit = 2
        TabLayoutMediator(mDatabind.tbHome, mDatabind.vpHome, true, true) {
                tab, position ->
            tab.text = tabTitles[position]
        }.attach()

        appViewModel.run {
            appColor.observe(this@HomeFragment) {
                setUiTheme(it, mDatabind.tbHome)
            }
        }
    }

}