package developers.guru.freedictionaryapp.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner
import developers.guru.core.constants.THEME_DARK
import developers.guru.core.constants.THEME_LIGHT
import developers.guru.core.extensions.*
import developers.guru.core.listeners.AppFragmentListeners
import developers.guru.core.listeners.SnackBarMessagesListeners
import developers.guru.core.preferences.StorePreferences
import javax.inject.Inject

/**
 * Created by Maulik V. Sinroja on 2021-09-08 19:34.
 */
abstract class AppActivity<T : ViewDataBinding, V : AppViewModel> : AppCompatActivity(),
    AppFragmentListeners, SnackBarMessagesListeners {

    @Inject
    lateinit var storePreferences: StorePreferences

    abstract fun fullscreenActivity(): Boolean
    abstract fun transparentActivity(): Boolean
    abstract fun changeStatusBarColor(): Int
    abstract fun setContentView(): Int
    abstract fun setBindingVariable(): Int
    abstract fun setViewModel(): V
    abstract fun setLifecycleOwner(): LifecycleOwner
    abstract fun initialization()

    override fun onFragmentAttached() {}
    override fun onFragmentDetached(tag: String) {}

    private var snackBarMessagesListeners: SnackBarMessagesListeners? = null

    private lateinit var mViewDataBinding: T
    private var mViewModel: V? = null

    fun getViewDataBinding(): T = mViewDataBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        if (transparentActivity())
            this.setupTransparentActivity()

        this.setupStatusBarColor(changeStatusBarColor())
        super.onCreate(savedInstanceState)
        setSelectedTheme(storePreferences.themeId)

        setupViewBinding()

        snackBarMessagesListeners = this
    }

    private fun setupViewBinding() {
        mViewDataBinding = DataBindingUtil.setContentView(this, setContentView())
        this.mViewModel = if (mViewModel == null) setViewModel() else mViewModel
        mViewDataBinding.setVariable(setBindingVariable(), mViewModel)
        mViewDataBinding.lifecycleOwner = setLifecycleOwner()

        if (fullscreenActivity())
            this.setupFullScreenActivity()

        this.initialization()
    }

    override fun onDestroy() {
        super.onDestroy()
        snackBarMessagesListeners = null
    }

    fun setSelectedTheme(selectedTheme: String) {
        when (selectedTheme) {
            THEME_LIGHT -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            THEME_DARK -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
    }

    override fun onFailure(message: String) =
        message.showFailureSnackBarView(window.decorView.findViewById(android.R.id.content))

    override fun onSuccess(message: String) =
        message.showSuccessSnackBarView(window.decorView.findViewById(android.R.id.content))

    override fun onWarning(message: String) =
        message.showWarningSnackBarView(window.decorView.findViewById(android.R.id.content))
}