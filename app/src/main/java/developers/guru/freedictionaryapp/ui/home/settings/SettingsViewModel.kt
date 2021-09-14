package developers.guru.freedictionaryapp.ui.home.settings

import android.app.Application
import dagger.hilt.android.lifecycle.HiltViewModel
import developers.guru.freedictionaryapp.base.AppViewModel
import javax.inject.Inject

/**
 * Created by Maulik V. Sinroja on 2021-09-11 18:05.
 */
@HiltViewModel
class SettingsViewModel @Inject constructor(application: Application) : AppViewModel(application)