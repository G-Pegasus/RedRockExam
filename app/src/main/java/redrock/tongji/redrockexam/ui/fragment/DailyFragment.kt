package redrock.tongji.redrockexam.ui.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import redrock.tongji.lib_base.base.BaseBindVMFragment
import redrock.tongji.redrockexam.R
import redrock.tongji.redrockexam.bean.CommonData
import redrock.tongji.redrockexam.databinding.FragmentDailyBinding
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
        rvDaily.layoutManager = LinearLayoutManager(context)
        viewModel.loadDaily()
        viewModel.dailyPathData.observerKt { result ->
            val list = result.getOrNull()
            if (list != null) {
                viewModel.listData.clear()
                viewModel.listData.addAll(list)
                dailyAdapter = context?.let { DailyAdapter(it, list) }!!
                rvDaily.adapter = dailyAdapter
                rvDaily.adapter?.notifyDataSetChanged()
                dailyAdapter.setOnItemClickListener(object : DailyAdapter.OnItemClickListener {
                    override fun onItemClick(view: View, position: Int) {
                        val intent = Intent(context, PlayVideoActivity::class.java)
                        val bundle = Bundle()
                        val topData = CommonData(
                            list[position].url,
                            list[position].playUrl,
                            list[position].time,
                            list[position].title,
                            list[position].author,
                            list[position].description ?: "",
                            list[position].id,
                            list[position].blurred
                        )
                        bundle.putSerializable("data", topData)
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
    }

    override fun lazyLoadData() {

    }

}