# 暑期考核 —— 开眼App

## 1、功能介绍

>App使用`BottomNavigationView ` + `ViewPager2`实现底部导航栏，点击切换界面。App部分页面设置沉浸式状态栏提升体验，RV列表添加动画，更加丝滑一些。对RV列表添加`FloatingActionButton`，可以在用户滑很远想回到上面的时候一键返回。整体结构借鉴了林潼学长的MVVM（比我之前的结构好...）

### （1）首页

首页有两个页面，分别是推荐和日报，使用`TabLayout`和`ViewPager2`实现页面的切换   
![shouye1](https://github.com/liutongji/RedRockExam/blob/master/gif/shouye1.gif) 
![shouye2](https://github.com/liutongji/RedRockExam/blob/master/gif/shouye2.gif)

### （2）社区

社区有三个界面，广场、发现和人气。广场用`PhotoView` + `ViewPager2`实现图片的查看（第一次加载的时候有些慢），发现页面实现了Banner轮播图，分类等功能，人气为请求到的各种榜单。  
![shequ1](https://github.com/liutongji/RedRockExam/blob/master/gif/shequ1.gif) 
![shequ1](https://github.com/liutongji/RedRockExam/blob/master/gif/shequ2.gif)

### （3）通知

通知没什么好说的...  
![tongzhi](https://github.com/liutongji/RedRockExam/blob/master/gif/tongzhi.gif)

### （4）我的

我的页面也是体现本App一大特色的地方，实现了一键换肤、用户信息修改和收藏功能，前者通过全局的`ViewModel`实现，设置颜色主题后，通知所有界面主要颜色改变。后两个使用`Room`实现本地持久化存储。  
![wode](https://github.com/liutongji/RedRockExam/blob/master/gif/wode.gif) 
![like](https://github.com/liutongji/RedRockExam/blob/master/gif/addlike.gif)

### （5）视频播放界面

视频播放调第三方库，可旋转全屏观看，在界面内可点击收藏，视频播放下面有相关视频推荐和评论。  
![shipin](https://github.com/liutongji/RedRockExam/blob/master/gif/shipin.gif)

### （6）分类详情界面

视频内容点击更多可展开。

## 2、技术

### （1）App换肤

实现该功能需要获取一个全局的`ViewModel`，供全局观察，当在设置好颜色之后，将`appViewModel.appColor`的值改变，在别的界面通过`Observe`观察其变化，发现其值发生改变，立即修改一些控件的颜色与主题一致。

在`Application`中，自己定义一个工厂获取到`Application`的实例，并以此创建`ViewModel`可以在全局使用。将创建的`ViewModel`保存到自己的`ViewModelStoreOwner`中。

```kotlin
// 供全局引用
val appViewModel: AppViewModel by lazy { App.appViewModelInstance }

class App : Application(), ViewModelStoreOwner {

    private var mFactory: ViewModelProvider.Factory? = null
    private lateinit var mAppViewModelStore: ViewModelStore

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
        lateinit var appViewModelInstance: AppViewModel
    }

    // 获取全局的 ViewModel
    private fun getAppViewModelProvider(): ViewModelProvider {
        return ViewModelProvider(this, this.getAppFactory())
    }

    // 自己定义 Factory
    private fun getAppFactory(): ViewModelProvider.Factory {
        if (mFactory == null) {
            mFactory = ViewModelProvider.AndroidViewModelFactory.getInstance(this)
        }
        return mFactory as ViewModelProvider.Factory
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        mAppViewModelStore = ViewModelStore()
        appViewModelInstance = getAppViewModelProvider()[AppViewModel::class.java]
    }

    override fun getViewModelStore(): ViewModelStore {
        return mAppViewModelStore
    }
}
```

在其他界面使用该`ViewModel`，setUiTheme()是一个工具函数，用于修改控件颜色。

```kotlin
appViewModel.run {
    appColor.observe(this@DailyFragment) {
        setUiTheme(it, mDatabind.includeList.floatbtn, 	mDatabind.includeList.includeRecyclerview.swipeRefresh)
    }
}
```

但该实现方式有很大的缺点，就是可能忘记在某个界面设置颜色>_<，但目前只能想到这样实现换肤功能。

### （2）扩展函数

我对一些可能经常复用的逻辑代码封装了很多扩展函数，比如初始化控件等。

```kotlin
// 初始化 ViewPager2
fun ViewPager2.init(
    fragment: Fragment,
    fragments: ArrayList<Fragment>,
    isUserInputEnabled: Boolean = true
): ViewPager2 {
    //是否可滑动
    this.isUserInputEnabled = isUserInputEnabled
    //设置适配器
    adapter = object : FragmentStateAdapter(fragment) {
        override fun createFragment(position: Int) = fragments[position]
        override fun getItemCount() = fragments.size
    }
    return this
}

// 初始化 FloatButton
fun RecyclerView.initFloatBtn(floatBtn: FloatingActionButton) {
    // 监听recyclerview滑动到顶部的时候，把向上返回顶部的按钮隐藏
    addOnScrollListener(object : RecyclerView.OnScrollListener() {
        @SuppressLint("RestrictedApi")
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            if (!canScrollVertically(-1)) {
                floatBtn.visibility = View.INVISIBLE
            }
        }
    })
    floatBtn.backgroundTintList = ColorUtil.getOneColorStateList(App.context)
    floatBtn.setOnClickListener {
        val layoutManager = layoutManager as LinearLayoutManager
        // 如果当前recyclerview 最后一个视图位置的索引大于等于20，则迅速返回顶部，否则带有滚动动画效果返回到顶部
        if (layoutManager.findLastVisibleItemPosition() >= 30) {
            scrollToPosition(0) // 没有动画迅速返回到顶部
        } else {
            smoothScrollToPosition(0) // 有滚动动画返回到顶部
        }
    }
}

// 初始化BottomNavigationView，在init内即可实现其与ViewPager2的结合
fun BottomNavigationView.init(navigationItemSelectedAction: (Int) -> Unit) : BottomNavigationView {
    itemIconTintList = ColorUtil.getColorStateList(ColorUtil.getColor(App.context))
    itemTextColor = ColorUtil.getColorStateList(App.context)
    setOnNavigationItemSelectedListener {
        navigationItemSelectedAction.invoke(it.itemId)
        true
    }
    return this
}
......
```

### （3）网络状态变化的监听

定义一个`NetStateManager`，提供实例给外部，在MainActivity中监听网络状态，可以管理多个界面网络状态的变化。

```kotlin
class NetStateManager private constructor() {

    val mNetworkStateCallback =
        EventLiveData<NetState>()

    companion object {
        val instance: NetStateManager by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            NetStateManager()
        }
    }
}

// MainActivity中
private fun onNetworkStateChanged(netState: NetState) {
	if (netState.isSuccess) {
    	Toast.makeText(applicationContext, "我现在有网哦!", Toast.LENGTH_SHORT).show()
    } else {
        Toast.makeText(applicationContext, "我怎么断网了呀!", Toast.LENGTH_SHORT).show()
    }
}
```



### （4）网络请求数据与本地存储数据结合

在本项目中，除了在网络上请求数据，还使用`Room`数据库框架实现本地数据持久化存储，主要存储**我的喜欢**以及用户的个人信息。二者数据都在仓库层中获取。

```kotlin
// 从网络获取数据
fun loadNotify() = fire(Dispatchers.IO) {
        val response = ApiLoad.loadNotify()
        val length = response.messageList.size
        val list = mutableListOf<NotifyData>()
        for (i in 0 until length) {
            val messageData = NotifyData(
                response.messageList[i].title,
                response.messageList[i].content,
                response.messageList[i].date,
                response.nextPageUrl
            )
            list.add(messageData)
        }

        Result.success(list)
    }

// 从数据库中获取数据
fun loadMyLike() : LiveData<List<VideoInfoBean>> {
    return videoDao.loadAllVideos()
}
```

### （5）自定义悬浮按钮Behavior

实现悬浮按钮，用户滑动过多通过该按钮一键返回顶部，提升体验。

```java
@Override
public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout,
                                   @NonNull FloatingActionButton child,
                                   @NonNull View directTargetChild,
                                   @NonNull View target,
                                   int nestedScrollAxes) {

    // 判断是否是垂直滚动，是则返回true
    return nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL ||
            super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target, nestedScrollAxes);
}

@SuppressLint("RestrictedApi")
@Override
public void onNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull FloatingActionButton child,
                           @NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {

    super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);

    if (dyConsumed > 0 && child.getVisibility() == View.VISIBLE) {
        // 如果向上滑动则隐藏
        child.setVisibility(View.INVISIBLE);
    } else if (dyConsumed < 0 && child.getVisibility() != View.VISIBLE) {
        // 向下滑动则展示出来
        child.show();
    }
}
```

## 3、心得体会

本次考核我认为与我几个月前的的项目相比提升蛮大的，我也明白了只有在自己一步一步实现的项目中才能提升的更多。在项目中不断测试自己的App，寻找bug，寻找可以提升用户体验的地方。我想这就是我们移动端开发的魅力所在，时刻为用户着想。除此之外，我也体会到很多和同伴一起开发的快乐，开发之余吐吐槽可以缓解很多敲代码的疲劳，也可以见识到许多自己不知道的技术。这可能也是部门的温馨之处吧，之前自己一个人摸索着学习真的挺累也挺难坚持的。很感谢这几个月在网校的时光，不仅提升了不少自己的技术，也收获到了难得的友情，这才是**最珍贵**之处。随着学习的越来越深入，也越发的发现自己的不足，项目中有很多待提升之处，只有通过不断地学习才能敲出更精致优美的代码。最后，感谢网校给予了我一个珍贵难忘充实的暑期。
