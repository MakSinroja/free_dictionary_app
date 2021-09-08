package developers.guru.core.utils

import android.util.Log
import timber.log.Timber

/**
 * Created by Maulik V. Sinroja on 2021-09-08 17:01.
 */
class TimberCrashReportingTree : Timber.Tree() {
    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        if (priority == Log.VERBOSE || priority == Log.DEBUG) return

        if (t != null) {
            if (priority == Log.ERROR) {
                Timber.e(t)
            } else if (priority == Log.WARN) {
                Timber.w(t)
            }
        }
    }
}