package redrock.tongji.redrockexam.ui.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import androidx.core.view.ViewCompat
import androidx.core.view.ViewCompat.canScrollVertically
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import redrock.tongji.lib_base.base.BaseBindVMFragment
import redrock.tongji.redrockexam.App
import redrock.tongji.redrockexam.R
import redrock.tongji.redrockexam.appViewModel
import redrock.tongji.redrockexam.bean.CommonData
import redrock.tongji.redrockexam.databinding.FragmentDailyBinding
import redrock.tongji.redrockexam.ext.init
import redrock.tongji.redrockexam.ext.initFloatBtn
import redrock.tongji.redrockexam.ext.setUiTheme
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

                // 设置列表加载动画
                rvDaily.layoutAnimation = LayoutAnimationController(AnimationUtils.loadAnimation(App.context, R.anim.animation))

                // 初始化快速返回顶部按钮
                rvDaily.initFloatBtn(mDatabind.includeList.floatbtn)

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

        viewModel.morePathData.observerKt { result ->
            val list = result.getOrNull()
            if (list != null) {
                viewModel.listData.addAll(list)
                dailyAdapter.addMore(list)
                dailyAdapter.notifyDataSetChanged()
            } else {
                this.showToast("已经没有更多了哦~")
            }
        }

        // 滑动到最后一个时加载下一页
        rvDaily.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!canScrollVertically(recyclerView, 1)) {
                    viewModel.loadMore(viewModel.listData[viewModel.listData.size - 1].nextUrl)
                    this@DailyFragment.showToast("加载完成！")
                }
            }
        })

        appViewModel.run {
            appColor.observe(this@DailyFragment) {
                setUiTheme(it, mDatabind.includeList.floatbtn, mDatabind.includeList.includeRecyclerview.swipeRefresh)
            }
        }
    }

}