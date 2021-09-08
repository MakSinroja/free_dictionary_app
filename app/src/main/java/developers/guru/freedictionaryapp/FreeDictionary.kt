package developers.guru.freedictionaryapp

import androidx.multidex.MultiDexApplication
import dagger.hilt.android.HiltAndroidApp
import developers.guru.core.utils.TimberCrashReportingTree
import timber.log.Timber

/**
 * Created by Maulik V. Sinroja on 2021-09-08 18:30.
 */
@HiltAndroidApp
class FreeDictionary : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree()) else Timber.plant(
            TimberCrashReportingTree()
        )
    }
}