package redrock.tongji.lib_base.base

import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

/**
 * @Author Tongji
 * @Description
 * @Date create in 2022/7/14 19:58
 */
abstract class BaseBindVMActivity<VM : BaseViewModel, DB : ViewDataBinding> : BaseVMActivity<VM>() {

    lateinit var mBind: DB

    override fun setLayout() {
        mBind = DataBindingUtil.setContentView(
            this,
            getLayoutRes
        )
        mBind.lifecycleOwner = this
    }

    override fun initEvent() {
    }

    override fun onDestroy() {
        super.onDestroy()
        if (::mBind.isInitialized) {
            mBind.unbind()
        }
    }
}