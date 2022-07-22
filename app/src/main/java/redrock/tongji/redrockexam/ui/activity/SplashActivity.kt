package redrock.tongji.redrockexam.ui.activity

import android.annotation.SuppressLint
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay
import redrock.tongji.lib_base.base.BaseBindActivity
import redrock.tongji.redrockexam.R
import redrock.tongji.redrockexam.databinding.ActivitySplashBinding
import redrock.tongji.redrockexam.ext.startActivity

@SuppressLint("CustomSplashScreen")
class SplashActivity : BaseBindActivity<ActivitySplashBinding>() {

    override val getLayoutRes: Int
        get() = R.layout.activity_splash

    override fun initData() {
        actionBar?.hide()
        mBind.context = this
        lifecycleScope.launchWhenCreated {
            delay(2000)
            startActivity<MainActivity>()
            finish()
        }
    }
}