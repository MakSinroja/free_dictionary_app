package developers.guru.core.preferences

import android.content.Context
import developers.guru.core.constants.THEME_ID
import developers.guru.core.constants.THEME_LIGHT
import developers.guru.core.extensions.PreferencesExtensions
import javax.inject.Inject

/**
 * Created by Maulik V. Sinroja on 2021-09-08 16:59.
 */
class StorePreferences @Inject constructor(context: Context) {
    var themeId: String by PreferencesExtensions(context, THEME_ID, THEME_LIGHT)
}