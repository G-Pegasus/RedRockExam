package redrock.tongji.redrockexam.ui.fragment

import android.util.Log
import redrock.tongji.lib_base.base.BaseBindVMFragment
import redrock.tongji.redrockexam.App
import redrock.tongji.redrockexam.R
import redrock.tongji.redrockexam.appViewModel
import redrock.tongji.redrockexam.databinding.FragmentMineBinding
import redrock.tongji.redrockexam.ext.setUiTheme
import redrock.tongji.redrockexam.ui.fragment.dialog.AboutAuthor
import redrock.tongji.redrockexam.ui.fragment.dialog.SetColor
import redrock.tongji.redrockexam.ui.viewmodel.MineViewModel
import redrock.tongji.redrockexam.util.ColorUtil

/**
 * @Author Tongji
 * @Description
 * @Date create in 2022/7/14 21:24
 */
class MineFragment : BaseBindVMFragment<MineViewModel, FragmentMineBinding>() {

    override val getLayoutRes: Int
        get() = R.layout.fragment_mine

    override fun initView() {

        mDatabind.rootMine.setBackgroundColor(ColorUtil.getColor(App.context))

        mDatabind.rootAuthorInfo.setOnClickListener {
            AboutAuthor().show(childFragmentManager, "AuthorAbout")
        }

        mDatabind.rootAppColor.setOnClickListener {
            SetColor().show(childFragmentManager, "SetAppColor")
        }

        appViewModel.run {
            appColor.observe(this@MineFragment) {
                setUiTheme(it, mDatabind.rootMine, )
            }
        }
    }

}