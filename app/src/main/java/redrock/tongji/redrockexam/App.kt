package redrock.tongji.redrockexam

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import redrock.tongji.redrockexam.ui.viewmodel.AppViewModel

/**
 * @Author Tongji
 * @Description
 * @Date create in 2022/7/14 15:39
 */

val appViewModel: AppViewModel by lazy { App.appViewModelInstance }

class App : Application(), ViewModelStoreOwner {

    private var mFactory: ViewModelProvider.Factory? = null
    private lateinit var mAppViewModelStore: ViewModelStore

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
        lateinit var appViewModelInstance: AppViewModel
    }

    // 获取全局的 ViewModel
    fun getAppViewModelProvider(): ViewModelProvider {
        return ViewModelProvider(this, this.getAppFactory())
    }

    private fun getAppFactory(): ViewModelProvider.Factory {
        if (mFactory == null) {
            mFactory = ViewModelProvider.AndroidViewModelFactory.getInstance(this)
        }
        return mFactory as ViewModelProvider.Factory
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        mAppViewModelStore = ViewModelStore()
        appViewModelInstance = getAppViewModelProvider()[AppViewModel::class.java]
        //IjkPlayerManager.setLogLevel(if (BuildConfig.DEBUG) IjkMediaPlayer.IJK_LOG_WARN else IjkMediaPlayer.IJK_LOG_SILENT)
    }

    override fun getViewModelStore(): ViewModelStore {
        return mAppViewModelStore
    }
}
