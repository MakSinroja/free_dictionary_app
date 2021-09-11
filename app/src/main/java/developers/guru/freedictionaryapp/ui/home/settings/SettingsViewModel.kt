package developers.guru.freedictionaryapp.ui.home.settings

import android.app.Activity
import android.app.Application
import androidx.databinding.ViewDataBinding
import dagger.hilt.android.lifecycle.HiltViewModel
import developers.guru.freedictionaryapp.base.AppViewModel
import developers.guru.freedictionaryapp.databinding.FragmentSettingsBinding
import javax.inject.Inject

/**
 * Created by Maulik V. Sinroja on 2021-09-11 18:05.
 */
@HiltViewModel
class SettingsViewModel @Inject constructor(application: Application) : AppViewModel(application) {

    private lateinit var fragmentSettingsBinding: FragmentSettingsBinding

    override fun initialization(activity: Activity, viewDataBinding: ViewDataBinding) {
        fragmentSettingsBinding = viewDataBinding as FragmentSettingsBinding
    }
}