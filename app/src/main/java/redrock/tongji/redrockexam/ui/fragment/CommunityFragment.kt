package redrock.tongji.redrockexam.ui.fragment

import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import redrock.tongji.lib_base.base.BaseBindVMFragment
import redrock.tongji.redrockexam.R
import redrock.tongji.redrockexam.databinding.FragmentCommunityBinding
import redrock.tongji.redrockexam.ext.init
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
        val fragments = ArrayList<Fragment>()
        val tabTitles = arrayListOf("发现", "人气")
        fragments.add(DiscoveryFragment())
        fragments.add(PopularityFragment())
        mDatabind.vpCommunity.init(this, fragments)
        mDatabind.vpCommunity.offscreenPageLimit = 2
        TabLayoutMediator(mDatabind.tbCommunity, mDatabind.vpCommunity, true, true) {
                tab, position ->
            tab.text = tabTitles[position]
        }.attach()
    }

}