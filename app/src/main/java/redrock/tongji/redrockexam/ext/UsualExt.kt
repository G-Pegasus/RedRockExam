package redrock.tongji.redrockexam.ext

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.*
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomnavigation.BottomNavigationView
import redrock.tongji.redrockexam.App
import redrock.tongji.redrockexam.ui.fragment.CommunityFragment
import redrock.tongji.redrockexam.ui.fragment.HomeFragment
import redrock.tongji.redrockexam.ui.fragment.MineFragment
import redrock.tongji.redrockexam.ui.fragment.NotifyFragment
import redrock.tongji.redrockexam.util.ColorUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
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

// 初始化 ViewPager，设置 adapter
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

fun View.gone() {
    visibility = View.GONE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.visible() {
    visibility = View.VISIBLE
}
