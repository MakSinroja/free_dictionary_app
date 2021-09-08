package developers.guru.freedictionaryapp.ui.splash

import androidx.activity.viewModels
import androidx.lifecycle.LifecycleOwner
import dagger.hilt.android.AndroidEntryPoint
import developers.guru.freedictionaryapp.BR
import developers.guru.freedictionaryapp.R
import developers.guru.freedictionaryapp.base.AppActivity
import developers.guru.freedictionaryapp.databinding.ActivitySplashBinding

/**
 * Created by Maulik V. Sinroja on 2021-09-08 18:42.
 */
@AndroidEntryPoint
class SplashActivity : AppActivity<ActivitySplashBinding, SplashViewModel>() {

    private val splashViewModel by viewModels<SplashViewModel>()

    override fun fullscreenActivity(): Boolean = true

    override fun transparentActivity(): Boolean = false

    override fun changeStatusBarColor(): Int = R.attr.colorPrimaryVariant

    override fun setContentView(): Int = R.layout.activity_splash

    override fun setBindingVariable(): Int = BR.splashViewModel

    override fun setViewModel(): SplashViewModel = splashViewModel

    override fun setLifecycleOwner(): LifecycleOwner = this@SplashActivity

    override fun initialization() {}
}