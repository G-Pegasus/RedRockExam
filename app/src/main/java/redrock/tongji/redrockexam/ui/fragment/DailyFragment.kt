package redrock.tongji.redrockexam.ui.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import redrock.tongji.lib_base.base.BaseBindVMFragment
import redrock.tongji.redrockexam.App
import redrock.tongji.redrockexam.R
import redrock.tongji.redrockexam.bean.CommonData
import redrock.tongji.redrockexam.bean.RecData
import redrock.tongji.redrockexam.databinding.FragmentDailyBinding
import redrock.tongji.redrockexam.ext.init
import redrock.tongji.redrockexam.ext.showToast
import redrock.tongji.redrockexam.ui.activity.PlayVideoActivity
import redrock.tongji.redrockexam.ui.adapter.DailyAdapter
import redrock.tongji.redrockexam.ui.viewmodel.DailyViewModel

/**
 * @Author Tongji
 * @Description
 * @Date create in 2022/7/16 10:51
 */
class DailyFragment : BaseBindVMFragment<DailyViewModel, FragmentDailyBinding>() {

    private lateinit var dailyAdapter: DailyAdapter
    private val viewModel by lazy { ViewModelProvider(this)[DailyViewModel::class.java] }

    override val getLayoutRes: Int
        get() = R.layout.fragment_daily

    @SuppressLint("NotifyDataSetChanged")
    override fun initView() {
        val rvDaily = mDatabind.includeList.includeRecyclerview.recyclerView
        rvDaily.layoutManager = LinearLayoutManager(App.context)
        rvDaily.setItemViewCacheSize(10)
        viewModel.loadDaily()
        viewModel.dailyPathData.observerKt { result ->
            val list = result.getOrNull()
            if (list != null) {
                viewModel.listData.clear()
                viewModel.listData.addAll(list)
                dailyAdapter = context?.let { DailyAdapter(it, list) }!!
                rvDaily.adapter = dailyAdapter
                // 下拉刷新
                mDatabind.includeList.includeRecyclerview.swipeRefresh.init {
                    viewModel.loadDaily()
                    dailyAdapter.notifyDataSetChanged()
                    mDatabind.includeList.includeRecyclerview.swipeRefresh.isRefreshing = false
                }
                dailyAdapter.setOnItemClickListener(object : DailyAdapter.OnItemClickListener {
                    override fun onItemClick(view: View, position: Int) {
                        val intent = Intent(context, PlayVideoActivity::class.java)
                        val bundle = Bundle()
                        val videoData = CommonData(
                            list[position].url, list[position].playUrl,
                            list[position].time, list[position].title,
                            list[position].author, list[position].description ?: "",
                            list[position].id, list[position].blurred
                        )
                        bundle.putSerializable("data", videoData)
                        intent.putExtras(bundle)
                        startActivity(intent)
                    }

                    override fun onItemLongClick(view: View, position: Int) {

                    }
                })
            } else {
                this.showToast("网络好像不太好？555~")
            }
        }

//        viewModel.morePathData.observerKt { result ->
//            val list = result.getOrNull()
//            if (list != null) {
//                viewModel.listData.addAll(list)
//                dailyAdapter.addMore(list)
//                dailyAdapter.notifyDataSetChanged()
//            } else {
//                this.showToast("已经没有更多了哦~")
//            }
//        }
//
//        // 停止滑动时刷新数据，滑动卡顿严重，留待以后解决
//        rvDaily.addOnScrollListener(object : RecyclerView.OnScrollListener() {
//            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
//                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
//                    dailyAdapter.isScrolling = false
//                    viewModel.loadMore(viewModel.listData[viewModel.listData.size - 1].nextUrl)
//                } else {
//                    dailyAdapter.isScrolling = true
//                }
//            }
//        })
    }

}