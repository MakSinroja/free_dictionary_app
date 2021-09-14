package developers.guru.freedictionaryapp.ui.home.wordHistory

import android.app.Application
import dagger.hilt.android.lifecycle.HiltViewModel
import developers.guru.freedictionaryapp.base.AppViewModel
import javax.inject.Inject

/**
 * Created by Maulik V. Sinroja on 2021-09-11 18:06.
 */
@HiltViewModel
class WordHistoryViewModel @Inject constructor(application: Application) :
    AppViewModel(application)