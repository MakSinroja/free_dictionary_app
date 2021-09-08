package developers.guru.core.extensions

import android.content.Context
import android.util.TypedValue
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.appcompat.widget.AppCompatTextView

/**
 * Created by Maulik V. Sinroja on 2021-09-08 19:49.
 */
@ColorInt
fun Context.getColorFromAttribute(
    @AttrRes attributeColor: Int,
    typedValue: TypedValue = TypedValue(),
    resolveRefs: Boolean = true
): Int {
    theme.resolveAttribute(attributeColor, typedValue, resolveRefs)
    return typedValue.data
}

fun AppCompatTextView.setTextColorRes(@ColorInt color: Int) = setTextColor(color)