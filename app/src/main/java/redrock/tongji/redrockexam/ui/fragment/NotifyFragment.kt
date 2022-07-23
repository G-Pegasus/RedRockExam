package redrock.tongji.redrockexam.ui.fragment

import android.annotation.SuppressLint
import android.view.View
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import androidx.core.view.ViewCompat.canScrollVertically
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import redrock.tongji.lib_base.base.BaseBindVMFragment
import redrock.tongji.redrockexam.App
import redrock.tongji.redrockexam.R
import redrock.tongji.redrockexam.appViewModel
import redrock.tongji.redrockexam.databinding.FragmentNotifyBinding
import redrock.tongji.redrockexam.ext.init
import redrock.tongji.redrockexam.ext.initFloatBtn
import redrock.tongji.redrockexam.ext.setUiTheme
import redrock.tongji.redrockexam.ext.showToast
import redrock.tongji.redrockexam.ui.adapter.NotifyAdapter
import redrock.tongji.redrockexam.ui.viewmodel.NotifyViewModel
import redrock.tongji.redrockexam.util.ColorUtil

/**
 * @Author Tongji
 * @Description
 * @Date create in 2022/7/14 21:23
 */
class NotifyFragment : BaseBindVMFragment<NotifyViewModel, FragmentNotifyBinding>() {

    private val viewModel by lazy { ViewModelProvider(this).get(NotifyViewModel::class.java) }
    private lateinit var notifyAdapter: NotifyAdapter

    override val getLayoutRes: Int
        get() = R.layout.fragment_notify

    @SuppressLint("NotifyDataSetChanged")
    override fun initView() {
        val rvNotify = mDatabind.includeList.includeRecyclerview.recyclerView
        rvNotify.layoutManager = LinearLayoutManager(App.context)

        // 初始化快速返回顶部按钮
        rvNotify.initFloatBtn(mDatabind.includeList.floatbtn)

        viewModel.loadNotify()
        viewModel.notifyPathData.observerKt { result ->
            val list = result.getOrNull()
            if (list != null) {
                viewModel.listData.clear()
                viewModel.listData.addAll(list)
                notifyAdapter = NotifyAdapter(App.context, list)
                rvNotify.adapter = notifyAdapter

                // 设置列表加载动画
                rvNotify.layoutAnimation = LayoutAnimationController(AnimationUtils.loadAnimation(App.context, R.anim.animation))

                // 下拉刷新，并加载更多
                mDatabind.includeList.includeRecyclerview.swipeRefresh.init {
                    viewModel.loadNotify()
                    rvNotify.adapter?.notifyDataSetChanged()
                    mDatabind.includeList.includeRecyclerview.swipeRefresh.isRefreshing = false
                }
            } else {
                this.showToast("网络好像不太好？555~")
            }
        }

        viewModel.morePathData.observerKt { result ->
            val list = result.getOrNull()
            if (list != null) {
                viewModel.listData.addAll(list)
                notifyAdapter.addMore(list)
                notifyAdapter.notifyDataSetChanged()
            } else {
                this.showToast("已经没有更多了哦~")
            }
        }

        rvNotify.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!canScrollVertically(recyclerView, 1)) {
                    viewModel.loadMore(viewModel.listData[viewModel.listData.size - 1].nextUrl)
                    this@NotifyFragment.showToast("加载完成！")
                }
            }
        })

        appViewModel.run {
            appColor.observe(this@NotifyFragment) {
                setUiTheme(
                    it,
                    mDatabind.includeList.floatbtn,
                    mDatabind.includeList.includeRecyclerview.swipeRefresh
                )
            }
        }
    }

    override fun lazyLoadData() {

    }

}