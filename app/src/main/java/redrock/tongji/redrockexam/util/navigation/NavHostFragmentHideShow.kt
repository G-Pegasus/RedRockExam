package redrock.tongji.redrockexam.util.navigation

import android.view.View
import androidx.navigation.Navigator
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.NavHostFragment
import redrock.tongji.redrockexam.R

/**
 * 作者　: hegaojian
 * 时间　: 2021/6/29
 * 描述　: Hide - Show NavHostFragment
 */
class NavHostFragmentHideShow : NavHostFragment() {
    /**
     * @return 使用自己的FragmentNavigator
     */
    @Deprecated("Deprecated in Java")
    override fun createFragmentNavigator(): Navigator<out FragmentNavigator.Destination?> {
        return FragmentNavigatorHideShow(requireContext(), childFragmentManager, containerId)
    }


    private val containerId: Int
        get() {
            val id = id
            return id
        }
}