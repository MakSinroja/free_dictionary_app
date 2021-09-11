package developers.guru.freedictionaryapp.ui.home.dictionary

import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import developers.guru.freedictionaryapp.BR
import developers.guru.freedictionaryapp.R
import developers.guru.freedictionaryapp.base.AppFragment
import developers.guru.freedictionaryapp.databinding.FragmentDictionaryBinding

/**
 * Created by Maulik V. Sinroja on 2021-09-11 18:05.
 */
class DictionaryFragment : AppFragment<FragmentDictionaryBinding, DictionaryViewModel>() {

    private val dictionaryViewModel by viewModels<DictionaryViewModel>()

    override fun setContentView(): Int = R.layout.fragment_dictionary

    override fun setBindingVariable(): Int = BR.dictionaryViewModel

    override fun setViewModel(): DictionaryViewModel = dictionaryViewModel

    override fun setLifeCycleOwner(): LifecycleOwner = viewLifecycleOwner

    override fun initialization() {
        dictionaryViewModel.initialization(requireActivity(), getViewModelDataBinding())
    }
}