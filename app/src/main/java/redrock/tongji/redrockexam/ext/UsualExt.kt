package redrock.tongji.redrockexam.ext

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.*
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import redrock.tongji.redrockexam.App
import redrock.tongji.redrockexam.ui.fragment.CommunityFragment
import redrock.tongji.redrockexam.ui.fragment.HomeFragment
import redrock.tongji.redrockexam.ui.fragment.MineFragment
import redrock.tongji.redrockexam.ui.fragment.NotifyFragment
import redrock.tongji.redrockexam.util.ColorUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.logging.Handler
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

/**
 * @Author Tongji
 * @Description
 * @Date create in 2022/7/14 10:37
 */

// 跳转 Activity 扩展
inline fun <reified T : Activity> Activity.startActivity() {
    startActivity(Intent(this, T::class.java))
}

// RecyclerView 初始化
fun RecyclerView.init(
    layoutManger: RecyclerView.LayoutManager,
    bindAdapter: RecyclerView.Adapter<*>,
    isScroll: Boolean = true
): RecyclerView {
    layoutManager = layoutManger
    setHasFixedSize(true)
    adapter = bindAdapter
    isNestedScrollingEnabled = isScroll
    return this
}

//初始化 SwipeRefreshLayout
fun SwipeRefreshLayout.init(onRefreshListener: () -> Unit) {
    this.run {
        setOnRefreshListener {
            onRefreshListener.invoke()
        }
        //设置主题颜色
        setColorSchemeColors(ColorUtil.getColor(App.context))
    }
}

// 普通 ViewPager
fun ViewPager2.init(
    fragment: Fragment,
    fragments: ArrayList<Fragment>,
    isUserInputEnabled: Boolean = true
): ViewPager2 {
    //是否可滑动
    this.isUserInputEnabled = isUserInputEnabled
    //设置适配器
    adapter = object : FragmentStateAdapter(fragment) {
        override fun createFragment(position: Int) = fragments[position]
        override fun getItemCount() = fragments.size
    }
    return this
}

// 初始化主页面 ViewPager，设置 adapter
fun ViewPager2.initMain(activity: AppCompatActivity): ViewPager2 {
    //是否可滑动
    this.isUserInputEnabled = false
    this.offscreenPageLimit = 4
    //设置适配器
    adapter = object : FragmentStateAdapter(activity) {

        override fun getItemCount(): Int = 4

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> HomeFragment()
                1 -> CommunityFragment()
                2 -> NotifyFragment()
                3 -> MineFragment()
                else -> HomeFragment()
            }
        }
    }
    return this
}

// 初始化底部导航
fun BottomNavigationView.init(navigationItemSelectedAction: (Int) -> Unit) : BottomNavigationView {
    itemIconTintList = ColorUtil.getColorStateList(ColorUtil.getColor(App.context))
    itemTextColor = ColorUtil.getColorStateList(App.context)
    setOnNavigationItemSelectedListener {
        navigationItemSelectedAction.invoke(it.itemId)
        true
    }
    return this
}


// 协程 await
suspend fun <T> Call<T>.await(): T {
    return suspendCoroutine { continuation ->
        enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                val body = response.body()
                if (body != null) continuation.resume(body)
                else continuation.resumeWithException(
                    RuntimeException("response is null")
                )
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                Log.e("Internet error:", t.toString())
                continuation.resumeWithException(t)
            }
        })
    }
}

// Toast 扩展
fun Fragment.showToast(content: String): Toast {
    val toast = Toast.makeText(this.activity?.applicationContext, content, Toast.LENGTH_SHORT)
    toast.show()
    return toast
}

fun Context.showToast(content: String): Toast {
    val toast = Toast.makeText(this.applicationContext, content, Toast.LENGTH_SHORT)
    toast.show()
    return toast
}

fun RecyclerView.initFloatBtn(floatBtn: FloatingActionButton) {
    // 监听recyclerview滑动到顶部的时候，把向上返回顶部的按钮隐藏
    addOnScrollListener(object : RecyclerView.OnScrollListener() {
        @SuppressLint("RestrictedApi")
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            if (!canScrollVertically(-1)) {
                floatBtn.visibility = View.INVISIBLE
            }
        }
    })
    floatBtn.backgroundTintList = ColorUtil.getOneColorStateList(App.context)
    floatBtn.setOnClickListener {
        val layoutManager = layoutManager as LinearLayoutManager
        // 如果当前recyclerview 最后一个视图位置的索引大于等于20，则迅速返回顶部，否则带有滚动动画效果返回到顶部
        if (layoutManager.findLastVisibleItemPosition() >= 20) {
            scrollToPosition(0)//没有动画迅速返回到顶部
        } else {
            smoothScrollToPosition(0)//有滚动动画返回到顶部
        }
    }
}

fun RecyclerView.initFloatBtnGrid(floatBtn: FloatingActionButton) {
    // 监听recyclerview滑动到顶部的时候，把向上返回顶部的按钮隐藏
    addOnScrollListener(object : RecyclerView.OnScrollListener() {
        @SuppressLint("RestrictedApi")
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            if (!canScrollVertically(-1)) {
                floatBtn.visibility = View.INVISIBLE
            }
        }
    })
    floatBtn.backgroundTintList = ColorUtil.getOneColorStateList(App.context)
    floatBtn.setOnClickListener {
        smoothScrollToPosition(0)//有滚动动画返回到顶部
    }
}

fun View.gone() {
    visibility = View.GONE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.visible() {
    visibility = View.VISIBLE
}
