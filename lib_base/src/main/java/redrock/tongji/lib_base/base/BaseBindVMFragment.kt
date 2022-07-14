package redrock.tongji.lib_base.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import redrock.tongji.lib_base.extentions.inflateBindingWithGeneric

/**
 * @Author Tongji
 * @Description
 * @Date create in 2022/7/14 22:22
 */
abstract class BaseBindVMFragment<VM : BaseViewModel, DB : ViewDataBinding> : BaseVMFragment<VM>() {

    //该类绑定的ViewDataBinding
    private var _binding: DB? = null
    val mDatabind: DB get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding  = inflateBindingWithGeneric(inflater,container,false)
        return mDatabind.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}