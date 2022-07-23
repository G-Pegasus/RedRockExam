package redrock.tongji.redrockexam.util

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.preference.PreferenceManager
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import redrock.tongji.redrockexam.App
import redrock.tongji.redrockexam.R

/**
 * @Author Tongji
 * @Description 该单例用于设置主题颜色
 * @Date create in 2022/7/17 14:48
 */
object ColorUtil {

    // 获取当前颜色，没有设置就用默认颜色
    fun getColor(context: Context): Int {
        val setting = PreferenceManager.getDefaultSharedPreferences(context)
        val defaultColor = ContextCompat.getColor(context, R.color.colorPrimary)
        val color = setting.getInt("color", defaultColor)
        return if (color != 0 && Color.alpha(color) != 255) {
            defaultColor
        } else {
            color
        }
    }

    // 设置颜色
    fun setColor(context: Context, color: Int) {
        val setting = PreferenceManager.getDefaultSharedPreferences(context)
        setting.edit().putInt("color", color).apply()
    }

    fun getColorStateList(context: Context): ColorStateList {
        val colors = intArrayOf(getColor(context), ContextCompat.getColor(context, R.color.colorGray))
        val states = arrayOfNulls<IntArray>(2)
        states[0] = intArrayOf(android.R.attr.state_checked, android.R.attr.state_checked)
        states[1] = intArrayOf()
        return ColorStateList(states, colors)
    }

    fun getColorStateList(color: Int): ColorStateList {
        val colors = intArrayOf(color, ContextCompat.getColor(App.context, R.color.colorGray))
        val states = arrayOfNulls<IntArray>(2)
        states[0] = intArrayOf(android.R.attr.state_checked, android.R.attr.state_checked)
        states[1] = intArrayOf()
        return ColorStateList(states, colors)
    }

    fun getOneColorStateList(context: Context): ColorStateList {
        val colors = intArrayOf(getColor(context))
        val states = arrayOfNulls<IntArray>(1)
        states[0] = intArrayOf()
        return ColorStateList(states, colors)
    }

    fun getOneColorStateList(color: Int): ColorStateList {
        val colors = intArrayOf(color)
        val states = arrayOfNulls<IntArray>(1)
        states[0] = intArrayOf()
        return ColorStateList(states, colors)
    }
}