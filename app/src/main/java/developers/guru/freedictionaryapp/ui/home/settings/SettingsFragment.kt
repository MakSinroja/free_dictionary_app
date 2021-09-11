package developers.guru.freedictionaryapp.ui.home.settings

import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import developers.guru.freedictionaryapp.BR
import developers.guru.freedictionaryapp.R
import developers.guru.freedictionaryapp.base.AppFragment
import developers.guru.freedictionaryapp.databinding.FragmentSettingsBinding

/**
 * Created by Maulik V. Sinroja on 2021-09-11 18:05.
 */
class SettingsFragment : AppFragment<FragmentSettingsBinding, SettingsViewModel>() {

    private val settingsViewModel by viewModels<SettingsViewModel>()

    override fun setContentView(): Int = R.layout.fragment_settings

    override fun setBindingVariable(): Int = BR.settingsViewModel

    override fun setViewModel(): SettingsViewModel = settingsViewModel

    override fun setLifeCycleOwner(): LifecycleOwner = viewLifecycleOwner

    override fun initialization() {
        settingsViewModel.initialization(requireActivity(), getViewModelDataBinding())
    }
}