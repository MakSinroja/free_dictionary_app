package developers.guru.freedictionaryapp.ui.splash

import android.app.Application
import android.os.Handler
import android.os.Looper
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.databinding.ViewDataBinding
import dagger.hilt.android.lifecycle.HiltViewModel
import developers.guru.freedictionaryapp.base.AppViewModel
import developers.guru.freedictionaryapp.databinding.ActivitySplashBinding
import javax.inject.Inject

/**
 * Created by Maulik V. Sinroja on 2021-09-08 18:42.
 */
@HiltViewModel
class SplashViewModel @Inject constructor(application: Application) : AppViewModel(application) {

    var activitySplashBinding: ActivitySplashBinding? = null
    private lateinit var fadeInAnimation: Animation

    override fun initialization(viewDataBinding: ViewDataBinding) {
        activitySplashBinding = viewDataBinding as ActivitySplashBinding

        fadeInAnimation =
            AnimationUtils.loadAnimation(getApplication(), android.R.anim.fade_in).apply {
                duration = 200
            }

        activitySplashBinding?.apply {
            footerContentTextView.startAnimation(fadeInAnimation)
            appNameTextView.startAnimation(fadeInAnimation)

            Handler(Looper.getMainLooper()).postDelayed({
                motionLayout.transitionToEnd()
                motionLayout.startLayoutAnimation()
            }, 2000)

            motionLayout.setTransitionListener(object : MotionLayout.TransitionListener {
                override fun onTransitionCompleted(p0: MotionLayout?, p1: Int) {}

                override fun onTransitionChange(p0: MotionLayout?, p1: Int, p2: Int, p3: Float) {}

                override fun onTransitionStarted(p0: MotionLayout?, p1: Int, p2: Int) {}

                override fun onTransitionTrigger(
                    p0: MotionLayout?,
                    p1: Int,
                    p2: Boolean,
                    p3: Float
                ) {
                }
            })
        }
    }
}