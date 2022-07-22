package redrock.tongji.redrockexam.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import redrock.tongji.lib_base.base.BaseBindVMActivity
import redrock.tongji.redrockexam.App
import redrock.tongji.redrockexam.R
import redrock.tongji.redrockexam.bean.CommonData
import redrock.tongji.redrockexam.databinding.ActivitySpecialInfoBinding
import redrock.tongji.redrockexam.ext.showToast
import redrock.tongji.redrockexam.ui.adapter.TagInfoAdapter
import redrock.tongji.redrockexam.ui.viewmodel.SpecialViewModel

class SpecialInfoActivity : BaseBindVMActivity<SpecialViewModel, ActivitySpecialInfoBinding>() {

    private val viewModel by lazy { ViewModelProvider(this)[SpecialViewModel::class.java] }
    private lateinit var specialAdapter: TagInfoAdapter

    override val getLayoutRes: Int
        get() = R.layout.activity_special_info

    override fun initView() {
        mBind.rvSpecialInfo.layoutManager = LinearLayoutManager(App.context)
        val id = intent.getStringExtra("id")
        if (id != null) {
            viewModel.loadTagInfo(id)
        }

        viewModel.specialPathData.observe(this) { result ->
            val list = result.getOrNull()
            if (list != null) {
                Glide.with(this).load(list.cover).into(mBind.ivLogo)
            } else {
                this.showToast("图片加载失败了~")
            }
        }

        viewModel.specialRecPathData.observe(this) { result ->
            val list = result.getOrNull()
            if (list != null) {
                viewModel.listData.clear()
                viewModel.listData.addAll(list)
                specialAdapter = TagInfoAdapter(App.context, list)
                mBind.rvSpecialInfo.adapter = specialAdapter
                mBind.rvSpecialInfo.layoutAnimation = LayoutAnimationController(AnimationUtils.loadAnimation(App.context, R.anim.animation))
                specialAdapter.setOnItemClickListener(object : TagInfoAdapter.OnItemClickListener {
                    override fun onItemClick(view: View, position: Int) {
                        val intent = Intent(this@SpecialInfoActivity, PlayVideoActivity::class.java)
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
                this.showToast("加载失败惹~")
            }
        }
    }

    override fun initData() {

    }

}