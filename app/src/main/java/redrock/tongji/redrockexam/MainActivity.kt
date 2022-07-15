package redrock.tongji.redrockexam

import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import redrock.tongji.lib_base.base.BaseBindVMActivity
import redrock.tongji.redrockexam.databinding.ActivityMainBinding
import redrock.tongji.redrockexam.logic.network.NetState
import redrock.tongji.redrockexam.ui.viewmodel.MainViewModel

class MainActivity : BaseBindVMActivity<MainViewModel, ActivityMainBinding>() {

    var exitTime = 0L
    override val getLayoutRes: Int
        get() = R.layout.activity_main

    override fun initView() {
        val navView: BottomNavigationView = mBind.navView
        val navController = findNavController(R.id.host_fragment)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_community,
                R.id.navigation_notify, R.id.navigation_mine
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val nav = findNavController(R.id.host_fragment)
                if (nav.currentDestination != null && nav.currentDestination!!.id != R.id.navigation_home) {
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

    private fun onNetworkStateChanged(netState: NetState) {
        if (netState.isSuccess) {
            Toast.makeText(applicationContext, "我现在有网哦!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(applicationContext, "我怎么断网了呀!", Toast.LENGTH_SHORT).show()
        }
    }

}