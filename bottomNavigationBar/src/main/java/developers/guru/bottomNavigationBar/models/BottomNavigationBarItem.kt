package developers.guru.bottomNavigationBar.models

import android.graphics.RectF
import android.graphics.drawable.Drawable

/**
 * Created by Maulik V. Sinroja on 2021-09-11 16:54.
 */
data class BottomNavigationBarItem(
    var title: String,
    var contentDescription: String,
    val icon: Drawable,
    var rect: RectF = RectF(),
    var alpha: Int
)