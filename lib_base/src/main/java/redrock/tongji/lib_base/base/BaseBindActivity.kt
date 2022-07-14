package redrock.tongji.lib_base.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

/**
 * @Author Tongji
 * @Description
 * @Date create in 2022/7/14 11:43
 */
abstract class BaseBindActivity<DB : ViewDataBinding> : AppCompatActivity() {

    abstract val getLayoutRes: Int
    lateinit var mBind: DB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBind = DataBindingUtil.setContentView(
            this,
            getLayoutRes
        )

        initData()
    }

    open fun initData() {

    }

    override fun onDestroy() {
        super.onDestroy()
        if (::mBind.isInitialized) {
            mBind.unbind()
        }
    }
}