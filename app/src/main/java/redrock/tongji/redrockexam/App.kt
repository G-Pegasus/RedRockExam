package redrock.tongji.redrockexam

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.support.multidex.MultiDex
import androidx.work.WorkManager
import com.shuyu.gsyvideoplayer.player.IjkPlayerManager
import redrock.tongji.redrockexam.ui.SplashActivity
import tv.danmaku.ijk.media.player.IjkMediaPlayer

/**
 * @Author Tongji
 * @Description
 * @Date create in 2022/7/14 15:39
 */
class App : Application() {

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun onCreate() {
        super.onCreate()
        context = this
        IjkPlayerManager.setLogLevel(if (BuildConfig.DEBUG) IjkMediaPlayer.IJK_LOG_WARN else IjkMediaPlayer.IJK_LOG_SILENT)
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
    }
}