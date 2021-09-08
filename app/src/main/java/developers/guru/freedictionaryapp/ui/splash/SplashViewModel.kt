package developers.guru.freedictionaryapp.ui.splash

import android.app.Application
import dagger.hilt.android.lifecycle.HiltViewModel
import developers.guru.freedictionaryapp.base.AppViewModel
import javax.inject.Inject

/**
 * Created by Maulik V. Sinroja on 2021-09-08 18:42.
 */
@HiltViewModel
class SplashViewModel @Inject constructor(application: Application) : AppViewModel(application) {

    override fun initialization() {

    }
}