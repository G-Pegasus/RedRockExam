package redrock.tongji.redrockexam.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import redrock.tongji.lib_base.base.BaseBindVMFragment
import redrock.tongji.redrockexam.App
import redrock.tongji.redrockexam.R
import redrock.tongji.redrockexam.appViewModel
import redrock.tongji.redrockexam.bean.UserBean
import redrock.tongji.redrockexam.databinding.FragmentMineBinding
import redrock.tongji.redrockexam.ext.setUiTheme
import redrock.tongji.redrockexam.model.dao.UserDataBase
import redrock.tongji.redrockexam.ui.activity.LikeActivity
import redrock.tongji.redrockexam.ui.activity.PlayVideoActivity
import redrock.tongji.redrockexam.ui.fragment.dialog.AboutAuthor
import redrock.tongji.redrockexam.ui.fragment.dialog.SetColor
import redrock.tongji.redrockexam.ui.fragment.dialog.SetUserInfo
import redrock.tongji.redrockexam.ui.viewmodel.MineViewModel
import redrock.tongji.redrockexam.util.ColorUtil
import kotlin.concurrent.thread

/**
 * @Author Tongji
 * @Description
 * @Date create in 2022/7/14 21:24
 */
class MineFragment : BaseBindVMFragment<MineViewModel, FragmentMineBinding>() {

    private val userDao = UserDataBase.getDatabase(App.context).userDao()

    override val getLayoutRes: Int
        get() = R.layout.fragment_mine

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val user = UserBean("可莉", "想陪可莉去炸鱼~")
        userDao.insertUser(user)
    }

    override fun initView() {

        mDatabind.rootMine.setBackgroundColor(ColorUtil.getColor(App.context))

        mDatabind.tvUserName.text = userDao.loadAllUserInfo()[0].name
        mDatabind.tvUserDes.text = userDao.loadAllUserInfo()[0].description
        mDatabind.tvRefresh.setOnClickListener {
            mDatabind.tvUserName.text = userDao.loadAllUserInfo()[0].name
            mDatabind.tvUserDes.text = userDao.loadAllUserInfo()[0].description
        }

        mDatabind.rootAuthorInfo.setOnClickListener {
            AboutAuthor().show(childFragmentManager, "AuthorAbout")
        }

        mDatabind.rootAppColor.setOnClickListener {
            SetColor().show(childFragmentManager, "SetAppColor")
        }

        mDatabind.rootSetInfo.setOnClickListener {
            SetUserInfo().show(childFragmentManager, "SetUserInfo")
        }

        mDatabind.rootUserLike.setOnClickListener {
            startActivity(Intent(App.context, LikeActivity::class.java))
        }

        appViewModel.run {
            appColor.observe(this@MineFragment) {
                setUiTheme(it, mDatabind.rootMine)
            }
        }
    }

}