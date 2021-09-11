package developers.guru.bottomNavigationBar.utils

import android.content.Context
import android.content.res.Resources
import android.content.res.XmlResourceParser
import android.graphics.drawable.Drawable
import androidx.annotation.XmlRes
import androidx.core.content.ContextCompat
import developers.guru.bottomNavigationBar.models.BottomNavigationBarItem

/**
 * Created by Maulik V. Sinroja on 2021-09-11 16:57.
 */
internal class BottomNavigationBarParser(private val context: Context, @XmlRes res: Int) {

    private val parser: XmlResourceParser = context.resources.getXml(res)

    fun parse(): List<BottomNavigationBarItem> {
        val items: MutableList<BottomNavigationBarItem> = mutableListOf()
        var eventType: Int?

        do {
            eventType = parser.next()
            if (eventType == XmlResourceParser.START_TAG && parser.name == ITEM_TAG) {
                items.add(getTabConfig(parser))
            }
        } while (eventType != XmlResourceParser.END_DOCUMENT)

        return items
    }

    private fun getTabConfig(parser: XmlResourceParser): BottomNavigationBarItem {
        val attributeCount = parser.attributeCount
        var itemText: String? = null
        var itemDrawable: Drawable? = null
        var contentDescription: String? = null

        for (index in 0 until attributeCount) {
            when (parser.getAttributeName(index)) {
                ICON_ATTRIBUTE -> itemDrawable = ContextCompat.getDrawable(
                    context,
                    parser.getAttributeResourceValue(index, 0)
                )
                TITLE_ATTRIBUTE -> itemText = try {
                    context.getString(parser.getAttributeResourceValue(index, 0))
                } catch (notFoundException: Resources.NotFoundException) {
                    parser.getAttributeValue(index)
                }
                CONTENT_DESCRIPTION_ATTRIBUTE -> contentDescription = try {
                    context.getString(parser.getAttributeResourceValue(index, 0))
                } catch (notFoundException: Resources.NotFoundException) {
                    parser.getAttributeValue(index)
                }
            }
        }

        if (itemDrawable == null) {
            throw Throwable("Item icon can not be null!")
        }

        return BottomNavigationBarItem(
            itemText.toString(),
            contentDescription ?: itemText.toString(),
            itemDrawable,
            alpha = 0
        )
    }

    companion object {
        private const val ITEM_TAG = "item"
        private const val ICON_ATTRIBUTE = "icon"
        private const val TITLE_ATTRIBUTE = "title"
        private const val CONTENT_DESCRIPTION_ATTRIBUTE = "contentDescription"
    }
}