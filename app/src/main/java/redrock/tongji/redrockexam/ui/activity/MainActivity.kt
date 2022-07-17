package redrock.tongji.redrockexam.ui.activity

import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import redrock.tongji.lib_base.base.BaseBindVMActivity
import redrock.tongji.redrockexam.R
import redrock.tongji.redrockexam.databinding.ActivityMainBinding
import redrock.tongji.redrockexam.ext.init
import redrock.tongji.redrockexam.ext.initMain
import redrock.tongji.redrockexam.logic.network.NetState
import redrock.tongji.redrockexam.logic.network.NetStateManager
import redrock.tongji.redrockexam.ui.viewmodel.MainViewModel

class MainActivity : BaseBindVMActivity<MainViewModel, ActivityMainBinding>() {

    var exitTime = 0L
    override val getLayoutRes: Int
        get() = R.layout.activity_main

    override fun initView() {
        // 初始化 ViewPager
        mBind.mainViewpager.initMain(this)

        // 初始化 BottomNavigationView
        mBind.navView.init {
            when (it) {
                R.id.navigation_home -> mBind.mainViewpager.setCurrentItem(0, false)
                R.id.navigation_community -> mBind.mainViewpager.setCurrentItem(1, false)
                R.id.navigation_notify -> mBind.mainViewpager.setCurrentItem(2, false)
                R.id.navigation_mine -> mBind.mainViewpager.setCurrentItem(3, false)
            }
        }
    }

    override fun initData() {

    }

    override fun initEvent() {
        NetStateManager.instance.mNetworkStateCallback
            .observeInActivity(this, Observer(this::onNetworkStateChanged))
    }

    // 监听网络状态
    private fun onNetworkStateChanged(netState: NetState) {
        if (netState.isSuccess) {
            Toast.makeText(applicationContext, "我现在有网哦!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(applicationContext, "我怎么断网了呀!", Toast.LENGTH_SHORT).show()
        }
    }

}