package developers.guru.freedictionaryapp.ui.home.main

import android.app.Application
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import developers.guru.core.extensions.replaceFragment
import developers.guru.freedictionaryapp.R
import developers.guru.freedictionaryapp.base.AppViewModel
import developers.guru.freedictionaryapp.ui.home.dictionary.DictionaryFragment
import developers.guru.freedictionaryapp.ui.home.settings.SettingsFragment
import developers.guru.freedictionaryapp.ui.home.wordHistory.WordHistoryFragment
import javax.inject.Inject

/**
 * Created by Maulik V. Sinroja on 2021-09-11 16:04.
 */
@HiltViewModel
class HomeViewModel @Inject constructor(application: Application) : AppViewModel(application) {

    var selectedFragment = MutableLiveData<Fragment>()

    init {
        selectedFragment.value = DictionaryFragment()
    }

    fun setListeners(activity: HomeActivity) {
        activity.getViewDataBinding().apply {
            bottomNavigationBar.onItemBottomNavigationBarSelectedListener = activity

            selectedFragment.observe(activity, { fragment ->
                activity.replaceFragment(fragment, R.id.frameLayout)

                when (fragment) {
                    is DictionaryFragment -> toolbar.title =
                        activity.getString(R.string.str_dictionary)
                    is WordHistoryFragment -> toolbar.title =
                        activity.getString(R.string.str_word_history)
                    is SettingsFragment -> toolbar.title = activity.getString(R.string.str_settings)
                }
            })
        }
    }
}