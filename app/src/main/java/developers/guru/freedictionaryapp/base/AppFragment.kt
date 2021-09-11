package developers.guru.freedictionaryapp.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import developers.guru.core.preferences.StorePreferences
import javax.inject.Inject

/**
 * Created by Maulik V. Sinroja on 2021-09-11 18:11.
 */
@Suppress("UNCHECKED_CAST")
abstract class AppFragment<T : ViewDataBinding, V : AppViewModel> : Fragment() {

    var mainAppActivity: AppActivity<T, V>? = null

    @Inject
    lateinit var storePreferences: StorePreferences

    abstract fun setContentView(): Int
    abstract fun setBindingVariable(): Int
    abstract fun setViewModel(): V
    abstract fun setLifeCycleOwner(): LifecycleOwner
    abstract fun initialization()

    private lateinit var rootView: View
    private lateinit var mViewModelDataBinding: T
    private var mViewModel: V? = null

    fun getViewModelDataBinding(): T = mViewModelDataBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is AppActivity<*, *>) {
            mainAppActivity = context as AppActivity<T, V>?
            mainAppActivity?.onFragmentAttached()
        }
    }

    override fun onDetach() {
        super.onDetach()
        mainAppActivity = null
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mViewModelDataBinding =
            DataBindingUtil.inflate(inflater, setContentView(), container, false)
        rootView = mViewModelDataBinding.root
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.mViewModel = if (mViewModel == null) setViewModel() else mViewModel
        mViewModelDataBinding.setVariable(setBindingVariable(), mViewModel)
        mViewModelDataBinding.lifecycleOwner = setLifeCycleOwner()
        mViewModelDataBinding.executePendingBindings()
        this.initialization()
    }

    fun getAppActivity(): AppActivity<T, V>? = mainAppActivity
}