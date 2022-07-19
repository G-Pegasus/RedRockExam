package redrock.tongji.redrockexam.util;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.AttrRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;

import redrock.tongji.redrockexam.R;

/**
 * @Author Tongji
 * @Description
 * @Date create in 2022/7/19 15:45
 */
public class EasyBanner extends FrameLayout implements ViewPager.OnPageChangeListener {
    /**
     * 每个广告条目的图片地址
     */
    private List<String> mImageUrlList;
    /**
     * 用来盛放广告条目的
     */
    private ViewPager mViewPager;
    /**
     * 底部小圆点整个布局
     */
    private LinearLayout mPointLayout;
    /**
     * 用来加载banner图片的
     */
    private List<ImageView> mImageViewList;
    /**
     * 小圆点上一次的位置
     */
    private int mLastPosition;
    /**
     * 底部小圆点默认大小
     */
    private final static float POINT_DEFAULT_SIZE = 10f;
    /**
     * 切换广告的时长  单位：ms
     */
    private final static int BANNER_SWITCH_DELAY_MILLIS = 3000;
    /**
     * 用户是否正在触摸banner
     */
    private boolean mIsTouched = false;
    private final PollingHandler mHandler = new PollingHandler();

    /**
     * 图片加载器
     */
    private ImageLoader mImageLoader;

    @SuppressWarnings("deprecation")
    private static class PollingHandler extends Handler {
    }

    /**
     * 开启轮询?
     */
    private boolean pollingEnable = false;

    public EasyBanner(@NonNull Context context) {
        super(context);
        initView();
    }

    public EasyBanner(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public EasyBanner(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int
            defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    /**
     * 初始化View
     */
    private void initView() {
        //加载布局   子View个数==0  则还没有加载布局
        if (getChildCount() == 0) {
            View.inflate(getContext(), R.layout.layout_banner, this);
            mViewPager = findViewById(R.id.vp_banner);
            mPointLayout = findViewById(R.id.ll_banner_point);
        }
    }

    /**
     * 初始化banner
     *
     * @param imageUrlList 每个广告条目的图片地址
     */
    public void initBanner(@NonNull List<String> imageUrlList) {
        this.mImageUrlList = imageUrlList;
        if (imageUrlList.size() == 0) {
            throw new IllegalArgumentException("传入图片地址不能为空");
        }

        initView();
        initData();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        mImageViewList = new ArrayList<>();
        View pointView;

        int bannerSize = mImageUrlList.size();
        for (int i = 0; i < bannerSize; i++) {
            //加载图片
            ImageView imageView = new ImageView(getContext());
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

            //让外部去实现图片加载，实现解耦
            if (mImageLoader != null) {
                mImageLoader.loadImage(imageView, mImageUrlList.get(i));
            }

            mImageViewList.add(imageView);

            //底部的小白点
            pointView = new View(getContext());
            //设置背景
            pointView.setBackgroundResource(R.drawable.selector_banner_point);
            //设置小圆点的大小
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(DensityUtil
                    .dip2px(getContext(), POINT_DEFAULT_SIZE), DensityUtil.dip2px(getContext(),
                    POINT_DEFAULT_SIZE));

            //除第一个以外，其他小白点都需要设置左边距
            if (i != 0) {
                layoutParams.leftMargin = DensityUtil.dip2px(getContext(), POINT_DEFAULT_SIZE / 2);
                pointView.setEnabled(false); //默认小白点是不可用的
            }

            pointView.setLayoutParams(layoutParams);
            mPointLayout.addView(pointView);  //添加到linearLayout中
        }

        BannerAdapter bannerAdapter = new BannerAdapter();
        mViewPager.setAdapter(bannerAdapter);
        //页面切换监听器
        mViewPager.addOnPageChangeListener(this);

        //将ViewPager的起始位置放在  一个很大的数处，那么一开始就可以往左划动了   那个数必须是imageUrlList.size()的倍数
        int remainder = (Integer.MAX_VALUE / 2) % mImageUrlList.size();
        mViewPager.setCurrentItem(Integer.MAX_VALUE / 2 - remainder);
        mPointLayout.getChildAt(0).setEnabled(true);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

        int newPosition = position % mImageUrlList.size();

        //当页面切换时，将底部白点的背景颜色换掉
        mPointLayout.getChildAt(newPosition).setEnabled(true);
        mPointLayout.getChildAt(mLastPosition).setEnabled(false);
        mLastPosition = newPosition;
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mIsTouched = true;   //正在触摸  按下
                break;
            case MotionEvent.ACTION_MOVE: break;
            case MotionEvent.ACTION_UP:
                mIsTouched = false;
                break;
            default:
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 延时的任务
     */
    Runnable delayRunnable = new Runnable() {
        @Override
        public void run() {
            //用户在触摸时不能进行自动滑动
            if (!mIsTouched) {
                //ViewPager设置为下一项
                mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
            }
            if (pollingEnable) {
                //继续延迟切换广告
                mHandler.postDelayed(delayRunnable, BANNER_SWITCH_DELAY_MILLIS);
            }
        }
    };

    /**
     * banner中ViewPager的adapter
     */
    private class BannerAdapter extends PagerAdapter {
        /**
         * 返回资源一共有的条目数
         */
        @Override
        public int getCount() {
            return 100;
        }

        /**
         * 复用判断逻辑
         */
        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            final int newPosition = position % mImageUrlList.size();
            ImageView imageView;
            imageView = mImageViewList.get(newPosition);
            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }
    }

    /**
     * 设置图片加载器  --必须设置 否则图片不会加载出来
     */
    public void setImageLoader(@NonNull ImageLoader imageLoader) {
        this.mImageLoader = imageLoader;
        if (mImageViewList != null) {
            int imageSize = mImageViewList.size();
            for (int i = 0; i < imageSize; i++) {
                imageLoader.loadImage(mImageViewList.get(i), mImageUrlList.get(i));
            }
        }
    }

    /**
     * 向外部暴露的图片加载器，外界需要通过Glide或者其他方式来进行网络加载图片
     */
    public interface ImageLoader {
        void loadImage(ImageView imageView, String url);
    }

    /**
     * 开始轮播
     */
    public void start() {
        // 之前已经开启轮播  无需再开启
        if (pollingEnable) {
            return;
        }
        pollingEnable = true;
        mHandler.postDelayed(delayRunnable, BANNER_SWITCH_DELAY_MILLIS);
    }

    /**
     * 结束轮播
     */
    public void stop() {
        pollingEnable = false;
        mIsTouched = false;
        //移除Handler Callback 和 Message 防止内存泄漏
        mHandler.removeCallbacksAndMessages(null);
    }
}
