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
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import redrock.tongji.lib_base.base.BaseBindVMFragment
import redrock.tongji.redrockexam.App
import redrock.tongji.redrockexam.R
import redrock.tongji.redrockexam.appViewModel
import redrock.tongji.redrockexam.databinding.FragmentCommunitySquareBinding
import redrock.tongji.redrockexam.ext.*
import redrock.tongji.redrockexam.ui.activity.PhotoShowActivity
import redrock.tongji.redrockexam.ui.adapter.SquareAdapter
import redrock.tongji.redrockexam.ui.viewmodel.SquareViewModel

/**
 * @Author Tongji
 * @Description
 * @Date create in 2022/7/22 19:11
 */
class SquareFragment : BaseBindVMFragment<SquareViewModel, FragmentCommunitySquareBinding>() {

    private val viewModel by lazy { ViewModelProvider(this)[SquareViewModel::class.java] }
    private lateinit var squareAdapter: SquareAdapter

    override val getLayoutRes: Int
        get() = R.layout.fragment_community_square

    @SuppressLint("NotifyDataSetChanged")
    override fun initView() {
        val rvSquare: RecyclerView = mDatabind.includeList.includeRecyclerview.recyclerView
        rvSquare.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        viewModel.loadSquare()
        viewModel.squarePathData.observerKt { result ->
            val list = result.getOrNull()
            if (list != null) {
                viewModel.listData.clear()
                viewModel.listData.addAll(list)
                squareAdapter = SquareAdapter(App.context, list)
                rvSquare.adapter = squareAdapter

                // 初始化快速返回顶部按钮
                rvSquare.initFloatBtnGrid(mDatabind.includeList.floatbtn)

                // 设置列表加载动画
                rvSquare.layoutAnimation = LayoutAnimationController(AnimationUtils.loadAnimation(App.context, R.anim.animation))

                // 下拉刷新
                mDatabind.includeList.includeRecyclerview.swipeRefresh.init {
                    viewModel.loadSquare()
                    squareAdapter.notifyDataSetChanged()
                    mDatabind.includeList.includeRecyclerview.swipeRefresh.isRefreshing = false
                }

                squareAdapter.setOnItemClickListener(object : SquareAdapter.OnItemClickListener {
                    override fun onItemClick(view: View, position: Int) {
                        val intent = Intent(context, PhotoShowActivity::class.java)
                        val bundle = Bundle()
                        val photoList = list[position].urlList
                        bundle.putStringArrayList("data", photoList)
                        intent.putExtras(bundle)
                        startActivity(intent)
                    }

                    override fun onItemLongClick(view: View, position: Int) {

                    }
                })
            } else {
                this.showToast("图片加载失败了")
            }
        }

        viewModel.morePathData.observerKt { result ->
            val list = result.getOrNull()
            if (list != null) {
                viewModel.listData.addAll(list)
                squareAdapter.addMore(list)
                squareAdapter.notifyDataSetChanged()
            } else {
                this.showToast("已经没有更多了哦~")
            }
        }

        // 滑动到最后一个时加载下一页
        rvSquare.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!canScrollVertically(recyclerView, 1)) {
                    viewModel.loadMore(viewModel.listData[viewModel.listData.size - 1].nextUrl)
                    this@SquareFragment.showToast("加载完成！")
                }
            }
        })

        appViewModel.run {
            appColor.observe(this@SquareFragment) {
                setUiTheme(
                    it,
                    mDatabind.includeList.floatbtn,
                    mDatabind.includeList.includeRecyclerview.swipeRefresh
                )
            }
        }
    }

}