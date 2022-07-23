package redrock.tongji.redrockexam.util;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author Tongji
 * @Description UnPeek 简易修改版，主要用于监听网络状态变化
 * @Date create in 2022/7/17 12:14
 */

public class UnPeekLiveData<T> extends LiveData<T> {

    public UnPeekLiveData() {
        super();
    }

    private final static int START_VERSION = -1;

    private final AtomicInteger mCurrentVersion = new AtomicInteger(START_VERSION);

    protected boolean isAllowNullValue;

    private final HashMap<Integer, Boolean> observers = new HashMap<>();

    @Override
    public void setValue(T value) {
        mCurrentVersion.getAndIncrement();
        super.setValue(value);
    }

    // 在Activity中使用
    public void observeInActivity(@NonNull AppCompatActivity activity, @NonNull Observer<? super T> observer) {
        Integer storeId = System.identityHashCode(activity.getViewModelStore());
        observe(storeId, activity, observer);
    }

    @SuppressWarnings("ConstantConditions")
    private void observe(@NonNull Integer storeId,
                         @NonNull LifecycleOwner owner,
                         @NonNull Observer<? super T> observer) {

        if (observers.get(storeId) == null) {
            observers.put(storeId, true);
        }

        super.observe(owner, t -> {
            if (!observers.get(storeId)) {
                observers.put(storeId, true);
                if (t != null || isAllowNullValue) {
                    observer.onChanged(t);
                }
            }
        });
    }

}
