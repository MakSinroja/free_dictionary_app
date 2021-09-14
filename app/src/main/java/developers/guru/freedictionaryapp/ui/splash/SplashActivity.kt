package developers.guru.freedictionaryapp.ui.splash

import android.content.Intent
import androidx.activity.viewModels
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.lifecycle.LifecycleOwner
import dagger.hilt.android.AndroidEntryPoint
import developers.guru.freedictionaryapp.BR
import developers.guru.freedictionaryapp.R
import developers.guru.freedictionaryapp.base.AppActivity
import developers.guru.freedictionaryapp.databinding.ActivitySplashBinding
import developers.guru.freedictionaryapp.ui.home.main.HomeActivity

/**
 * Created by Maulik V. Sinroja on 2021-09-08 18:42.
 */
@AndroidEntryPoint
class SplashActivity : AppActivity<ActivitySplashBinding, SplashViewModel>(),
    MotionLayout.TransitionListener {

    private val splashViewModel by viewModels<SplashViewModel>()

    override fun fullscreenActivity(): Boolean = false

    override fun transparentActivity(): Boolean = false

    override fun changeStatusBarColor(): Int = R.attr.colorPrimaryVariant

    override fun setContentView(): Int = R.layout.activity_splash

    override fun setBindingVariable(): Int = BR.splashViewModel

    override fun setViewModel(): SplashViewModel = splashViewModel

    override fun setLifecycleOwner(): LifecycleOwner = this@SplashActivity

    override fun initialization() {
        splashViewModel.setListeners(this@SplashActivity)
    }

    override fun onTransitionStarted(p0: MotionLayout?, p1: Int, p2: Int) {}

    override fun onTransitionChange(p0: MotionLayout?, p1: Int, p2: Int, p3: Float) {}

    override fun onTransitionCompleted(p0: MotionLayout?, p1: Int) {
        startActivity(Intent(this@SplashActivity, HomeActivity::class.java))
        finish()
    }

    override fun onTransitionTrigger(p0: MotionLayout?, p1: Int, p2: Boolean, p3: Float) {}
}