package developers.guru.freedictionaryapp.ui.splash

import android.app.Application
import android.os.Handler
import android.os.Looper
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import developers.guru.freedictionaryapp.base.AppViewModel
import javax.inject.Inject

/**
 * Created by Maulik V. Sinroja on 2021-09-08 18:42.
 */
@HiltViewModel
class SplashViewModel @Inject constructor(application: Application) : AppViewModel(application) {

    private lateinit var fadeInAnimation: Animation

    fun setListeners(activity: SplashActivity) {
        fadeInAnimation =
            AnimationUtils.loadAnimation(activity, android.R.anim.fade_in).apply {
                duration = 200
            }

        activity.getViewDataBinding().apply {
            footerContentTextView.startAnimation(fadeInAnimation)
            appNameTextView.startAnimation(fadeInAnimation)

            Handler(Looper.getMainLooper()).postDelayed({
                motionLayout.transitionToEnd()
                motionLayout.startLayoutAnimation()
            }, 1000)

            motionLayout.setTransitionListener(activity)
        }
    }
}