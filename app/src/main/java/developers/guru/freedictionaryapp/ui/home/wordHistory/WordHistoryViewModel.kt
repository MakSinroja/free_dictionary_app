package developers.guru.freedictionaryapp.ui.home.wordHistory

import android.app.Activity
import android.app.Application
import androidx.databinding.ViewDataBinding
import dagger.hilt.android.lifecycle.HiltViewModel
import developers.guru.freedictionaryapp.base.AppViewModel
import developers.guru.freedictionaryapp.databinding.FragmentWordHistoryBinding
import javax.inject.Inject

/**
 * Created by Maulik V. Sinroja on 2021-09-11 18:06.
 */
@HiltViewModel
class WordHistoryViewModel @Inject constructor(application: Application) :
    AppViewModel(application) {

    private lateinit var fragmentWordHistoryBinding: FragmentWordHistoryBinding

    override fun initialization(activity: Activity, viewDataBinding: ViewDataBinding) {
        fragmentWordHistoryBinding = viewDataBinding as FragmentWordHistoryBinding
    }
}