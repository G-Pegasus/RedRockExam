package redrock.tongji.lib_base.util

import android.annotation.SuppressLint
import android.app.Application

/**
 * @Author Tongji
 * @Description
 * @Date create in 2022/7/14 20:08
 */
object AppGlobal {

    private var mApplication: Application? = null
        @SuppressLint("PrivateApi")
        get() {
            if (field == null) {
                return Class.forName("android.app.ActivityThread")
                    .getMethod("currentApplication")
                    .invoke(null) as Application?
            }
            return field
        }

    fun get(): Application? = mApplication
}