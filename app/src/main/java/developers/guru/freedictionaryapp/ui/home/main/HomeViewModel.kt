package developers.guru.freedictionaryapp.ui.home.main

import android.app.Activity
import android.app.Application
import androidx.databinding.ViewDataBinding
import dagger.hilt.android.lifecycle.HiltViewModel
import developers.guru.freedictionaryapp.base.AppViewModel
import developers.guru.freedictionaryapp.databinding.ActivityHomeBinding
import javax.inject.Inject

/**
 * Created by Maulik V. Sinroja on 2021-09-11 16:04.
 */
@HiltViewModel
class HomeViewModel @Inject constructor(application: Application) : AppViewModel(application) {

    private lateinit var activityHomeBinding: ActivityHomeBinding

    override fun initialization(activity: Activity, viewDataBinding: ViewDataBinding) {
        activityHomeBinding = viewDataBinding as ActivityHomeBinding
    }
}