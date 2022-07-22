package redrock.tongji.redrockexam.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import android.widget.ImageView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import redrock.tongji.lib_base.base.BaseBindVMFragment
import redrock.tongji.redrockexam.App
import redrock.tongji.redrockexam.R
import redrock.tongji.redrockexam.bean.CommonData
import redrock.tongji.redrockexam.databinding.FragmentDiscoveryBinding
import redrock.tongji.redrockexam.ext.showToast
import redrock.tongji.redrockexam.ui.activity.PlayVideoActivity
import redrock.tongji.redrockexam.ui.activity.SpecialInfoActivity
import redrock.tongji.redrockexam.ui.adapter.BannerAdapter
import redrock.tongji.redrockexam.ui.adapter.DailyAdapter
import redrock.tongji.redrockexam.ui.adapter.DiscoveryAdapter
import redrock.tongji.redrockexam.ui.adapter.SpecialGridAdapter
import redrock.tongji.redrockexam.ui.viewmodel.DiscoveryViewModel

/**
 * @Author Tongji
 * @Description
 * @Date create in 2022/7/19 13:25
 */
class DiscoveryFragment : BaseBindVMFragment<DiscoveryViewModel, FragmentDiscoveryBinding>() {

    private lateinit var otherAdapter: DiscoveryAdapter
    private lateinit var specialAdapter: SpecialGridAdapter
    private lateinit var bannerAdapter: BannerAdapter
    private val viewModel by lazy { ViewModelProvider(this)[DiscoveryViewModel::class.java] }

    override val getLayoutRes: Int
        get() = R.layout.fragment_discovery

    override fun initView() {
        viewModel.loadBanner()
        viewModel.bannerPathData.observerKt { result ->
            val list = result.getOrNull()
            if (list != null) {
                bannerAdapter = BannerAdapter(list)
                mDatabind.bannerViewpager.adapter = bannerAdapter
                mDatabind.bannerViewpager.registerOnPageChangeCallback(object :
                    ViewPager2.OnPageChangeCallback() {
                    override fun onPageSelected(position: Int) {
                        mDatabind.bannerViewpager.currentItem = position
                    }

                    override fun onPageScrollStateChanged(state: Int) {
                        //只有在空闲状态，才让自动滚动
                        if (state == ViewPager2.SCROLL_STATE_IDLE) {
                            if (mDatabind.bannerViewpager.currentItem == 0) {
                                mDatabind.bannerViewpager.setCurrentItem(bannerAdapter.itemCount - 2, false)
                            } else if (mDatabind.bannerViewpager.currentItem == bannerAdapter.itemCount - 1) {
                                mDatabind.bannerViewpager.setCurrentItem(1, false)
                            }
                        }
                    }
                })
                val mLooper = object : Runnable {
                    override fun run() {
                        mDatabind.bannerViewpager.currentItem = ++mDatabind.bannerViewpager.currentItem
                        mDatabind.bannerViewpager.postDelayed(this, 3000)
                    }
                }
                mDatabind.bannerViewpager.postDelayed(mLooper, 3000)
            } else {
                this.showToast("加载失败了哦~")
            }
        }

        val rvSpecial = mDatabind.recyclerViewGrid
        val specialLayoutManager = GridLayoutManager(App.context, 2)
        specialLayoutManager.orientation = GridLayoutManager.HORIZONTAL
        rvSpecial.layoutManager = specialLayoutManager
        rvSpecial.layoutAnimation = LayoutAnimationController(AnimationUtils.loadAnimation(App.context, R.anim.animation))
        viewModel.specialPathData.observerKt { result ->
            val list = result.getOrNull()
            if (list != null) {
                viewModel.listData.clear()
                viewModel.listData.addAll(list)
                specialAdapter = SpecialGridAdapter(App.context, list)

                rvSpecial.adapter = specialAdapter
                specialAdapter.setOnItemClickListener(object : SpecialGridAdapter.OnItemClickListener {
                    override fun onItemClick(view: View, position: Int) {
                        val intent = Intent(context, SpecialInfoActivity::class.java)
                        intent.putExtra("id", viewModel.listData[position].id)
                        startActivity(intent)
                    }

                    override fun onItemLongClick(view: View, position: Int) {

                    }

                })
            } else {
                this.showToast("加载失败惹~")
            }
        }

        val rvOther = mDatabind.recyclerViewLinear
        rvOther.layoutManager = LinearLayoutManager(App.context)
        viewModel.otherPathData.observerKt { result ->
            val list = result.getOrNull()
            if (list != null) {
                otherAdapter = DiscoveryAdapter(App.context, list)
                rvOther.adapter = otherAdapter
                otherAdapter.setOnItemClickListener(object : DiscoveryAdapter.OnItemClickListener {
                    override fun onItemClick(view: View, position: Int) {
                        val intent = Intent(context, PlayVideoActivity::class.java)
                        val bundle = Bundle()
                        val videoData = CommonData(
                            list[position].url, list[position].playUrl,
                            list[position].time, list[position].title,
                            list[position].author, list[position].description,
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
                this.showToast("没加载出来")
            }
        }
    }

}