package developers.guru.freedictionaryapp.ui.home.wordHistory

import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import developers.guru.freedictionaryapp.BR
import developers.guru.freedictionaryapp.R
import developers.guru.freedictionaryapp.base.AppFragment
import developers.guru.freedictionaryapp.databinding.FragmentWordHistoryBinding

/**
 * Created by Maulik V. Sinroja on 2021-09-11 18:06.
 */
class WordHistoryFragment : AppFragment<FragmentWordHistoryBinding, WordHistoryViewModel>() {

    private val wordHistoryViewModel by viewModels<WordHistoryViewModel>()

    override fun setContentView(): Int = R.layout.fragment_word_history

    override fun setBindingVariable(): Int = BR.wordHistoryViewModel

    override fun setViewModel(): WordHistoryViewModel = wordHistoryViewModel

    override fun setLifeCycleOwner(): LifecycleOwner = viewLifecycleOwner

    override fun initialization() {}
}