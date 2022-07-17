package redrock.tongji.redrockexam

import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import redrock.tongji.lib_base.base.BaseBindVMActivity
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

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val nav = findNavController(R.id.host_fragment)
                if (nav.currentDestination != null && nav.currentDestination!!.id != R.id.navigation_home) {
                    //如果当前界面不是主页，那么直接调用返回
                    nav.navigateUp()
                } else {
                    //是主页
                    if (System.currentTimeMillis() - exitTime > 2000) {
                        Toast.makeText(this@MainActivity, "再按一次退出应用", Toast.LENGTH_SHORT).show()
                        exitTime = System.currentTimeMillis()
                    } else {
                        finish()
                    }
                }
            }
        })
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