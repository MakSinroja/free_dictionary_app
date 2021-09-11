package developers.guru.freedictionaryapp.ui.home.main

import androidx.activity.viewModels
import androidx.lifecycle.LifecycleOwner
import developers.guru.freedictionaryapp.BR
import developers.guru.freedictionaryapp.R
import developers.guru.freedictionaryapp.base.AppActivity
import developers.guru.freedictionaryapp.databinding.ActivityHomeBinding

/**
 * Created by Maulik V. Sinroja on 2021-09-11 16:04.
 */
class HomeActivity : AppActivity<ActivityHomeBinding, HomeViewModel>() {

    private val homeViewModel by viewModels<HomeViewModel>()

    override fun fullscreenActivity(): Boolean = false

    override fun transparentActivity(): Boolean = false

    override fun changeStatusBarColor(): Int = R.attr.colorPrimaryVariant

    override fun setContentView(): Int = R.layout.activity_home

    override fun setBindingVariable(): Int = BR.homeViewModel

    override fun setViewModel(): HomeViewModel = homeViewModel

    override fun setLifecycleOwner(): LifecycleOwner = this@HomeActivity

    override fun initialization() {
        homeViewModel.initialization(this@HomeActivity, getViewDataBinding())
    }
}