package redrock.tongji.redrockexam.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import redrock.tongji.redrockexam.R
import redrock.tongji.redrockexam.ext.showToast
import redrock.tongji.redrockexam.ui.adapter.PhotoShowAdapter

class PhotoShowActivity : AppCompatActivity() {

    private lateinit var photoAdapter: PhotoShowAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo_show)

        val mViewPager: ViewPager2 = findViewById(R.id.show_photo)
        val urlList = intent.getStringArrayListExtra("data")
        if (urlList != null) {
            photoAdapter = PhotoShowAdapter(urlList)
            mViewPager.adapter = photoAdapter

        } else {
            this.showToast("图片加载失败了")
        }
    }
}