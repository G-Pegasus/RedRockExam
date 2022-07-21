package redrock.tongji.redrockexam.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.shuyu.gsyvideoplayer.GSYBaseActivityDetail
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer
import redrock.tongji.redrockexam.App
import redrock.tongji.redrockexam.R
import redrock.tongji.redrockexam.bean.CommonData
import redrock.tongji.redrockexam.ext.gone
import redrock.tongji.redrockexam.ext.showToast
import redrock.tongji.redrockexam.ui.adapter.CommentsAdapter
import redrock.tongji.redrockexam.ui.adapter.RelatedAdapter
import redrock.tongji.redrockexam.ui.viewmodel.PlayVideoViewModel

class PlayVideoActivity : GSYBaseActivityDetail<StandardGSYVideoPlayer>() {

    private val viewModel by lazy { ViewModelProvider(this)[PlayVideoViewModel::class.java] }
    private lateinit var tvTitle: TextView
    private lateinit var tvAuthor: TextView
    private lateinit var tvContent: TextView
    private lateinit var rvRelated: RecyclerView
    private lateinit var rvComment: RecyclerView
    private lateinit var rootView: LinearLayout
    private var playUrl: String = ""
    private var blurred: String = ""
    private var cover: String = ""
    private var id: String = ""
    private lateinit var viewPlayer: StandardGSYVideoPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play_video)
        initData()
        initRV()
        initVideoBuilderMode()
    }

    override fun getGSYVideoPlayer(): StandardGSYVideoPlayer {
        return viewPlayer
    }

    override fun getGSYVideoOptionBuilder(): GSYVideoOptionBuilder {
        val imageView = ImageView(App.context)
        imageView.scaleType = ImageView.ScaleType.CENTER_CROP
        Glide.with(App.context).load(cover).into(imageView)

        return GSYVideoOptionBuilder()
            .setThumbImageView(imageView)
            .setUrl(playUrl)
            .setCacheWithPlay(true)
            .setVideoTitle(tvTitle.text.toString())
            .setIsTouchWiget(true)
            .setRotateViewAuto(false)
            .setLockLand(false)
            .setShowFullAnimation(false) //打开动画
            .setNeedLockFull(true)
            .setSeekRatio(1f)
    }

    override fun clickForFullScreen() {

    }

    override fun getDetailOrientationRotateAuto(): Boolean {
        return true
    }

    private fun initRV() {
        rvRelated.layoutManager = LinearLayoutManager(App.context)
        viewModel.loadRelated(id)
        viewModel.relatedPathData.observe(this, Observer { result ->
            val list = result.getOrNull()
            if (list != null) {
                val relatedAdapter = RelatedAdapter(App.context, list)
                rvRelated.adapter = relatedAdapter
                relatedAdapter.setOnItemClickListener(object : RelatedAdapter.OnItemClickListener {
                    override fun onItemClick(view: View, position: Int) {
                        val intent = Intent(this@PlayVideoActivity, PlayVideoActivity::class.java)
                        val bundle = Bundle()
                        bundle.putSerializable("data", list[position])
                        intent.putExtras(bundle)
                        startActivity(intent)
                    }

                    override fun onItemLongClick(view: View, position: Int) {

                    }

                })
            } else {
                this.showToast("找不到相关视频诶~")
            }
        })

        rvComment.layoutManager = LinearLayoutManager(App.context)
        viewModel.replyPathData.observe(this, Observer { result ->
            val list = result.getOrNull()
            if (list != null) {
                rvComment.adapter = CommentsAdapter(App.context, list)
            } else {
                this.showToast("该视频没有评论哦~")
            }
        })
    }

    @SuppressLint("SetTextI18n")
    private fun initData() {
        tvTitle = findViewById(R.id.view_player_title)
        tvAuthor = findViewById(R.id.view_player_author)
        tvContent = findViewById(R.id.view_player_content)
        rootView = findViewById(R.id.root_video_player)
        viewPlayer = findViewById(R.id.video_player)
        rvRelated = findViewById(R.id.related_video)
        rvComment = findViewById(R.id.rv_comments)

        val bundle = intent.extras
        val videoData: CommonData = bundle?.getSerializable("data") as CommonData
        playUrl = videoData.playUrl
        tvTitle.text = videoData.title
        tvAuthor.text = "# ${videoData.author}"
        tvContent.text = videoData.description
        blurred = videoData.blurred
        cover = videoData.url
        id = videoData.id
        Glide.with(App.context).asBitmap().load(blurred).into(object : SimpleTarget<Bitmap>() {
            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                val drawable =  BitmapDrawable(resources, resource)
                rootView.background = drawable
            }
        })
        viewPlayer.titleTextView.gone()
        viewPlayer.backButton.gone()
    }

}