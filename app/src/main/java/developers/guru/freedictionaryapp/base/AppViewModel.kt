package developers.guru.freedictionaryapp.base

import android.app.Activity
import android.app.Application
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import developers.guru.core.preferences.StorePreferences

/**
 * Created by Maulik V. Sinroja on 2021-09-08 19:35.
 */
abstract class AppViewModel(application: Application) : AndroidViewModel(application) {

    var storePreferences: StorePreferences = StorePreferences(application)

    var isLoading = MutableLiveData<Boolean>()

    abstract fun initialization(activity: Activity, viewDataBinding: ViewDataBinding)
}