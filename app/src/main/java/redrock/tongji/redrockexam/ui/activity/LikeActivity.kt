package redrock.tongji.redrockexam.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import redrock.tongji.lib_base.base.BaseBindVMActivity
import redrock.tongji.redrockexam.App
import redrock.tongji.redrockexam.R
import redrock.tongji.redrockexam.appViewModel
import redrock.tongji.redrockexam.bean.CommonData
import redrock.tongji.redrockexam.bean.VideoInfoBean
import redrock.tongji.redrockexam.databinding.ActivityLikeBinding
import redrock.tongji.redrockexam.ext.init
import redrock.tongji.redrockexam.ext.initFloatBtn
import redrock.tongji.redrockexam.ext.setUiTheme
import redrock.tongji.redrockexam.model.dao.VideoInfoDatabase
import redrock.tongji.redrockexam.ui.adapter.LikeAdapter
import redrock.tongji.redrockexam.ui.viewmodel.LikeViewModel
import redrock.tongji.redrockexam.util.RecyclerTouchHelpCallBack

class LikeActivity : BaseBindVMActivity<LikeViewModel, ActivityLikeBinding>() {

    private val viewModel by lazy { ViewModelProvider(this)[LikeViewModel::class.java] }
    private lateinit var likeAdapter: LikeAdapter
    private lateinit var callBack: RecyclerTouchHelpCallBack
    private val videoDao = VideoInfoDatabase.getDatabase(App.context).videoInfoDao()

    override val getLayoutRes: Int
        get() = R.layout.activity_like

    @SuppressLint("NotifyDataSetChanged")
    override fun initView() {
        val rvLike = mBind.includeList.includeRecyclerview.recyclerView
        viewModel.likeLiveData.observe(this) {
            rvLike.layoutManager = LinearLayoutManager(App.context)
            likeAdapter = LikeAdapter(App.context, it as MutableList<VideoInfoBean>)
            rvLike.adapter = likeAdapter
            // 设置列表加载动画
            rvLike.layoutAnimation = LayoutAnimationController(
                AnimationUtils.loadAnimation(
                    App.context,
                    R.anim.animation
                )
            )

            mBind.includeList.includeRecyclerview.swipeRefresh.init {
                likeAdapter.notifyDataSetChanged()
                mBind.includeList.includeRecyclerview.swipeRefresh.isRefreshing = false
            }

            callBack = RecyclerTouchHelpCallBack(object : RecyclerTouchHelpCallBack.OnHelperCallBack {
                override fun onMove(fromPosition: Int, targetPosition: Int) {
                    likeAdapter.mList?.let { it1 ->
                        callBack.itemMove(likeAdapter,
                            it1, fromPosition, targetPosition)
                    }
                }

                override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder, actionState: Int) {
                    viewHolder.itemView.alpha = 1f
                    viewHolder.itemView.scaleX = 1.2f
                    viewHolder.itemView.scaleY = 1.2f
                }

                override fun clearView(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder
                ) {
                    likeAdapter.mList
                    viewHolder.itemView.alpha = 1f
                    viewHolder.itemView.scaleX = 1f
                    viewHolder.itemView.scaleY = 1f
                }

                override fun remove(
                    viewHolder: RecyclerView.ViewHolder,
                    direction: Int,
                    position: Int
                ) {
                    videoDao.deleteVideo(it[position])
                    likeAdapter.removeData(position)
                }

            })

            callBack.edit = true
            ItemTouchHelper(callBack).attachToRecyclerView(rvLike)

            // 初始化快速返回顶部按钮
            rvLike.initFloatBtn(mBind.includeList.floatbtn)

            likeAdapter.setOnItemClickListener(object : LikeAdapter.OnItemClickListener {
                override fun onItemClick(view: View, position: Int) {
                    val intent = Intent(App.context, PlayVideoActivity::class.java)
                    val bundle = Bundle()
                    val videoData = CommonData(
                        it[position].imgUrl, it[position].playUrl,
                        it[position].time, it[position].title,
                        it[position].author, it[position].description,
                        it[position].id, it[position].blurred
                    )
                    bundle.putSerializable("data", videoData)
                    intent.putExtras(bundle)
                    startActivity(intent)
                }

                override fun onItemLongClick(view: View, position: Int) {

                }
            })
        }

        appViewModel.run {
            appColor.observe(this@LikeActivity) { it1 ->
                setUiTheme(it1,
                    mBind.includeList.floatbtn,
                    mBind.includeList.includeRecyclerview.swipeRefresh
                )
                mBind.tvMyLikeBack.setBackgroundColor(it1)
                window.statusBarColor = it1
            }
        }
    }

    override fun initData() {

    }
}