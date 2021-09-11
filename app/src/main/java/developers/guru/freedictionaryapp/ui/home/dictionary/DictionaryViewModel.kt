package developers.guru.freedictionaryapp.ui.home.dictionary

import android.app.Activity
import android.app.Application
import androidx.databinding.ViewDataBinding
import dagger.hilt.android.lifecycle.HiltViewModel
import developers.guru.freedictionaryapp.base.AppViewModel
import developers.guru.freedictionaryapp.databinding.FragmentDictionaryBinding
import javax.inject.Inject

/**
 * Created by Maulik V. Sinroja on 2021-09-11 18:05.
 */
@HiltViewModel
class DictionaryViewModel @Inject constructor(application: Application) :
    AppViewModel(application) {

    private lateinit var fragmentDictionaryBinding: FragmentDictionaryBinding

    override fun initialization(activity: Activity, viewDataBinding: ViewDataBinding) {
        fragmentDictionaryBinding = viewDataBinding as FragmentDictionaryBinding
    }
}