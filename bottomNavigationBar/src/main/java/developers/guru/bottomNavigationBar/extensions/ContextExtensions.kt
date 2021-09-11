package developers.guru.bottomNavigationBar.extensions

import android.content.Context
import kotlin.math.roundToInt

/**
 * Created by Maulik V. Sinroja on 2021-09-11 16:52.
 */
internal fun Context.d2p(dp: Float): Float =
    (dp * resources.displayMetrics.density).roundToInt().toFloat()