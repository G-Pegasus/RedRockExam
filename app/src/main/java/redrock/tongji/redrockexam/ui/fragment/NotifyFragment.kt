package redrock.tongji.redrockexam.ui.fragment

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import redrock.tongji.lib_base.base.BaseBindVMFragment
import redrock.tongji.redrockexam.App
import redrock.tongji.redrockexam.R
import redrock.tongji.redrockexam.appViewModel
import redrock.tongji.redrockexam.databinding.FragmentNotifyBinding
import redrock.tongji.redrockexam.ext.init
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
        viewModel.loadNotify()
        viewModel.notifyPathData.observerKt { result ->
            val list = result.getOrNull()
            if (list != null) {
                viewModel.listData.clear()
                viewModel.listData.addAll(list)
                notifyAdapter = NotifyAdapter(App.context, list)
                rvNotify.adapter = notifyAdapter
                // 下拉刷新
                mDatabind.includeList.includeRecyclerview.swipeRefresh.init {
                    viewModel.loadNotify()
                    notifyAdapter.notifyDataSetChanged()
                    mDatabind.includeList.includeRecyclerview.swipeRefresh.isRefreshing = false
                }
            } else {
                this.showToast("网络好像不太好？555~")
            }
        }

//        viewModel.morePathData.observerKt { result ->
//            val list = result.getOrNull()
//            if (list != null) {
//                viewModel.listData.addAll(list)
//                notifyAdapter.addMore(list)
//                notifyAdapter.notifyDataSetChanged()
//            } else {
//                this.showToast("已经没有更多了哦~")
//            }
//        }
//
//        rvNotify.addOnScrollListener(object : RecyclerView.OnScrollListener() {
//            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
//                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
//                    notifyAdapter.isScrolling = false
//                    viewModel.loadMore(viewModel.listData[viewModel.listData.size - 1].nextUrl)
//                } else {
//                    notifyAdapter.isScrolling = true
//                }
//            }
//        })
    }

    override fun lazyLoadData() {

    }

}