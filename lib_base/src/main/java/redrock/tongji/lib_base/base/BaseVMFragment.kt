package redrock.tongji.lib_base.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import redrock.tongji.lib_base.extentions.errorToast
import java.lang.reflect.ParameterizedType

/**
 * @Author Tongji
 * @Description
 * @Date create in 2022/7/14 20:02
 */
abstract class BaseVMFragment<VM : BaseViewModel> : BaseFragment() {

    lateinit var mViewModel: VM

    override fun initData() {
        initViewModel()
        lazyLoadData()
    }

    private fun initViewModel() {
        val parameterizedType = javaClass.genericSuperclass as ParameterizedType
        mViewModel = ViewModelProvider(this)[parameterizedType.actualTypeArguments[0] as Class<VM>]
        mViewModel.mStateLiveData.observe(viewLifecycleOwner) { state ->
            when (state) {
                LoadState -> {
                    showLoading()
                }
                SuccessState -> {
                    hideLoading()
                }
                is ErrorState -> {
                    hideLoading()
                    state.errorMsg?.let { errorToast(it) }
                    handlerError()
                }
            }
        }
    }

    protected fun <T : Any> LiveData<T>.observerKt(block: (T) -> Unit) {
        this.observe(viewLifecycleOwner) {
            block(it)
        }
    }

    //由于每个页面的加载框可能不一致，所以此处暴露给子类实现
    open fun showLoading() {

    }

    open fun hideLoading() {

    }

    open fun handlerError() {

    }

    open fun lazyLoadData() {}
}