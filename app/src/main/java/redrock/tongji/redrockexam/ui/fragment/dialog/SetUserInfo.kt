package redrock.tongji.redrockexam.ui.fragment.dialog

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import redrock.tongji.redrockexam.App
import redrock.tongji.redrockexam.R
import redrock.tongji.redrockexam.appViewModel
import redrock.tongji.redrockexam.bean.UserBean
import redrock.tongji.redrockexam.ext.setUiTheme
import redrock.tongji.redrockexam.model.dao.UserDataBase
import kotlin.concurrent.thread

/**
 * @Author Tongji
 * @Description
 * @Date create in 2022/7/23 21:03
 */
class SetUserInfo : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_setting, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val userDao = UserDataBase.getDatabase(App.context).userDao()
        val getName: EditText = view.findViewById(R.id.input_user_name)
        val getDes: EditText = view.findViewById(R.id.input_user_des)
        val tvCancel: TextView = view.findViewById(R.id.tv_cancel_set)
        val tvSure: TextView = view.findViewById(R.id.tv_sure_set)

        val newUser = userDao.loadAllUserInfo()[0]

        tvSure.setOnClickListener {
            newUser.name = getName.text.toString()
            newUser.description = getDes.text.toString()
            userDao.updateUser(newUser)
            dismiss()
        }
        tvCancel.setOnClickListener {
            dismiss()
        }

        appViewModel.run {
            appColor.observe(this@SetUserInfo) {
                setUiTheme(it, tvSure)
            }
        }
    }
}