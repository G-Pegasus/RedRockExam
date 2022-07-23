package redrock.tongji.redrockexam.ui.fragment.dialog

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import redrock.tongji.redrockexam.App
import redrock.tongji.redrockexam.R
import redrock.tongji.redrockexam.appViewModel
import redrock.tongji.redrockexam.ext.setUiTheme
import redrock.tongji.redrockexam.util.ColorUtil

/**
 * @Author Tongji
 * @Description
 * @Date create in 2022/7/23 15:17
 */
class SetColor : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialot_color, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val radioGroup: RadioGroup = view.findViewById(R.id.radio_group)
        val tvCancel: TextView = view.findViewById(R.id.tv_cancel)
        val tvSure: TextView = view.findViewById(R.id.tv_sure)
        var setColor = 0

        radioGroup.setOnCheckedChangeListener { _, checkId ->
            when (checkId) {
                R.id.btn_blue -> {
                    setColor = Color.parseColor("#2196F3")
                }

                R.id.btn_orange -> {
                    setColor = Color.parseColor("#EF5350")
                }

                R.id.btn_green -> {
                    setColor = Color.parseColor("#26A69A")
                }
            }
        }

        tvCancel.setOnClickListener { dismiss() }
        tvSure.setOnClickListener {
            appViewModel.appColor.value = setColor
            Log.i("TongJi", "${appViewModel.appColor.value}")
            ColorUtil.setColor(App.context, setColor)
            dismiss()
        }

        appViewModel.run {
            appColor.observe(this@SetColor) {
                setUiTheme(it, tvSure)
            }
        }
    }
}