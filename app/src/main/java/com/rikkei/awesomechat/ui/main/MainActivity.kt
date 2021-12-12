package com.rikkei.awesomechat.ui.main

import android.view.WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.rikkei.awesomechat.R
import com.rikkei.awesomechat.base.BaseActivity
import com.rikkei.awesomechat.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {

    override val layoutResource get() = R.layout.activity_main
    private val navHostFragment by lazy {
        supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
    }

    private val navController by lazy { navHostFragment.navController }

    override fun initViews() {
        viewBinding.bottomNavigation.apply {
            setupWithNavController(navController)
        }
    }

    fun changeBottomNavStatus(isEnable: Int) {
        viewBinding.groupBottomNav.visibility = isEnable
    }
}
