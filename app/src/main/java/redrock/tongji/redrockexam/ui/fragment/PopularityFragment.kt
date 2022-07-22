package redrock.tongji.redrockexam.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import redrock.tongji.lib_base.base.BaseBindVMFragment
import redrock.tongji.redrockexam.App
import redrock.tongji.redrockexam.R
import redrock.tongji.redrockexam.bean.CommonData
import redrock.tongji.redrockexam.databinding.FragmentPopularityBinding
import redrock.tongji.redrockexam.ext.showToast
import redrock.tongji.redrockexam.ui.activity.PlayVideoActivity
import redrock.tongji.redrockexam.ui.adapter.DailyAdapter
import redrock.tongji.redrockexam.ui.viewmodel.PopularityViewModel

/**
 * @Author Tongji
 * @Description
 * @Date create in 2022/7/19 13:27
 */
class PopularityFragment : BaseBindVMFragment<PopularityViewModel, FragmentPopularityBinding>() {

    private lateinit var popularWeeklyAdapter: DailyAdapter
    private val viewModel by lazy { ViewModelProvider(this)[PopularityViewModel::class.java] }

    override val getLayoutRes: Int
        get() = R.layout.fragment_popularity

    override fun initView() {
        val rvWeekly = mDatabind.recyclerViewWeekly
        rvWeekly.layoutManager = LinearLayoutManager(App.context)
        rvWeekly.layoutAnimation = LayoutAnimationController(AnimationUtils.loadAnimation(App.context, R.anim.animation))
        viewModel.loadPopularData()
        viewModel.popularPathData.observerKt { result ->
            val list = result.getOrNull()
            if (list != null) {
                viewModel.listData.clear()
                viewModel.listData.addAll(list)
                popularWeeklyAdapter = DailyAdapter(App.context, list)
                rvWeekly.adapter = popularWeeklyAdapter
                popularWeeklyAdapter.setOnItemClickListener(object : DailyAdapter.OnItemClickListener {
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
                this.showToast("加载失败惹~")
            }
        }
    }
}