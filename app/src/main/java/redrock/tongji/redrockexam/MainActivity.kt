package redrock.tongji.redrockexam

import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import redrock.tongji.lib_base.base.BaseBindVMActivity
import redrock.tongji.redrockexam.databinding.ActivityMainBinding
import redrock.tongji.redrockexam.ext.findNavController
import redrock.tongji.redrockexam.logic.network.NetState
import redrock.tongji.redrockexam.ui.fragment.CommunityFragment
import redrock.tongji.redrockexam.ui.fragment.HomeFragment
import redrock.tongji.redrockexam.ui.fragment.MineFragment
import redrock.tongji.redrockexam.ui.fragment.NotifyFragment
import redrock.tongji.redrockexam.ui.viewmodel.MainViewModel

class MainActivity : BaseBindVMActivity<MainViewModel, ActivityMainBinding>() {

    private var mHomeFragment: HomeFragment? = null
    private var mCommunityFragment: CommunityFragment? = null
    private var mNotifyFragment: NotifyFragment? = null
    private var mMineFragment: MineFragment? = null

    var exitTime = 0L
    override val getLayoutRes: Int
        get() = R.layout.activity_main

    override fun initView() {
        val navController = findNavController(R.id.host_fragment)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.action_home, R.id.action_community, R.id.action_notification, R.id.action_mine
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        mBind.navView.setupWithNavController(navController)
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val nav = Navigation.findNavController(this@MainActivity, R.id.host_fragment)
                if (nav.currentDestination != null && nav.currentDestination!!.id != R.id.mainfragment) {
                    //如果当前界面不是主页，那么直接调用返回即可
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
        onNetworkStateChanged(NetState())
    }

    override fun initData() {

    }

    override fun initEvent() {

    }

    fun onNetworkStateChanged(netState: NetState) {
        if (netState.isSuccess) {
            Toast.makeText(applicationContext, "我特么终于有网了啊!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(applicationContext, "我特么怎么断网了!", Toast.LENGTH_SHORT).show()
        }
    }

}