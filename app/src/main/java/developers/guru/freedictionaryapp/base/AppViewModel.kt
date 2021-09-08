package developers.guru.freedictionaryapp.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel

/**
 * Created by Maulik V. Sinroja on 2021-09-08 19:35.
 */
abstract class AppViewModel(application: Application) : AndroidViewModel(application) {

    abstract fun initialization()
}