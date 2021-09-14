package developers.guru.bottomNavigationBar.ui

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.os.Build
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.accessibility.AccessibilityEvent
import android.view.animation.DecelerateInterpolator
import androidx.annotation.ColorInt
import androidx.annotation.Dimension
import androidx.annotation.FontRes
import androidx.annotation.XmlRes
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.view.ViewCompat
import developers.guru.bottomNavigationBar.R
import developers.guru.bottomNavigationBar.extensions.d2p
import developers.guru.bottomNavigationBar.listeners.OnItemBottomNavigationBarReselectedListener
import developers.guru.bottomNavigationBar.listeners.OnItemBottomNavigationBarSelectedListener
import developers.guru.bottomNavigationBar.models.BottomNavigationBarItem
import developers.guru.bottomNavigationBar.utils.AccessibleExploreByTouchHelper
import developers.guru.bottomNavigationBar.utils.BottomNavigationBarParser

/**
 * Created by Maulik V. Sinroja on 2021-09-11 16:58.
 */
class BottomNavigationBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.BottomNavigationBarStyle
) : View(context, attrs, defStyleAttr) {

    // Dynamic Variables
    private var itemWidth: Float = 0F

    private var currentIconTint = itemIconTintActive

    private var indicatorLocation = barSideMargins

    private val rect = RectF()

    private var items = listOf<BottomNavigationBarItem>()

    // Attribute Defaults
    @ColorInt
    private var _barBackgroundColor = Color.WHITE

    @ColorInt
    private var _barIndicatorColor = Color.parseColor(DEFAULT_INDICATOR_COLOR)

    @Dimension
    private var _barIndicatorRadius = context.d2p(DEFAULT_CORNER_RADIUS)

    @Dimension
    private var _barSideMargins = context.d2p(DEFAULT_SIDE_MARGIN)

    @Dimension
    private var _barCornerRadius = context.d2p(DEFAULT_BAR_CORNER_RADIUS)

    @Dimension
    private var _itemPadding = context.d2p(DEFAULT_ITEM_PADDING)

    private var _itemAnimDuration = DEFAULT_ANIM_DURATION

    @Dimension
    private var _itemIconSize = context.d2p(DEFAULT_ICON_SIZE)

    @Dimension
    private var _itemIconMargin = context.d2p(DEFAULT_ICON_MARGIN)

    @ColorInt
    private var _itemIconTint = Color.parseColor(DEFAULT_TINT)

    @ColorInt
    private var _itemIconTintActive = Color.WHITE

    @ColorInt
    private var _itemTextColor = Color.WHITE

    @Dimension
    private var _itemTextSize = context.d2p(DEFAULT_TEXT_SIZE)

    @FontRes
    private var _itemFontFamily: Int = INVALID_RES

    @XmlRes
    private var _itemMenuRes: Int = INVALID_RES

    private var _itemActiveIndex: Int = 0

    // Core Attributes
    var barBackgroundColor: Int
        @ColorInt get() = _barBackgroundColor
        set(@ColorInt value) {
            _barBackgroundColor = value
            paintBackground.color = value
            invalidate()
        }

    var barIndicatorColor: Int
        @ColorInt get() = _barIndicatorColor
        set(@ColorInt value) {
            _barIndicatorColor = value
            paintIndicator.color = value
            invalidate()
        }

    var barIndicatorRadius: Float
        @Dimension get() = _barIndicatorRadius
        set(@Dimension value) {
            _barIndicatorRadius = value
            invalidate()
        }

    var barSideMargins: Float
        @Dimension get() = _barSideMargins
        set(@Dimension value) {
            _barSideMargins = value
            invalidate()
        }

    var barCornerRadius: Float
        @Dimension get() = _barCornerRadius
        set(@Dimension value) {
            _barCornerRadius = value
            invalidate()
        }

    var itemTextSize: Float
        @Dimension get() = _itemTextSize
        set(@Dimension value) {
            _itemTextSize = value
            paintText.textSize = value
            invalidate()
        }

    var itemTextColor: Int
        @ColorInt get() = _itemTextColor
        set(@ColorInt value) {
            _itemTextColor = value
            paintText.color = value
            invalidate()
        }

    var itemPadding: Float
        @Dimension get() = _itemPadding
        set(@Dimension value) {
            _itemPadding = value
            invalidate()
        }

    var itemAnimDuration: Long
        get() = _itemAnimDuration
        set(value) {
            _itemAnimDuration = value
        }

    var itemIconSize: Float
        @Dimension get() = _itemIconSize
        set(@Dimension value) {
            _itemIconSize = value
            invalidate()
        }

    var itemIconMargin: Float
        @Dimension get() = _itemIconMargin
        set(@Dimension value) {
            _itemIconMargin = value
            invalidate()
        }

    var itemIconTint: Int
        @ColorInt get() = _itemIconTint
        set(@ColorInt value) {
            _itemIconTint = value
            invalidate()
        }

    var itemIconTintActive: Int
        @ColorInt get() = _itemIconTintActive
        set(@ColorInt value) {
            _itemIconTintActive = value
            invalidate()
        }

    var itemFontFamily: Int
        @FontRes get() = _itemFontFamily
        set(@FontRes value) {
            _itemFontFamily = value
            if (value != INVALID_RES) {
                paintText.typeface = ResourcesCompat.getFont(context, value)
                invalidate()
            }
        }

    var itemMenuRes: Int
        @XmlRes get() = _itemMenuRes
        set(@XmlRes value) {
            _itemMenuRes = value
            if (value != INVALID_RES) {
                items = BottomNavigationBarParser(context, value).parse()
                invalidate()
            }
        }

    var itemActiveIndex: Int
        get() = _itemActiveIndex
        set(value) {
            _itemActiveIndex = value
            applyItemActiveIndex()
        }

    // Listeners
    var onItemBottomNavigationBarSelectedListener: OnItemBottomNavigationBarSelectedListener? = null

    var onItemBottomNavigationBarReselectedListener: OnItemBottomNavigationBarReselectedListener? =
        null

    var onItemSelected: ((Int) -> Unit)? = null

    var onItemReselected: ((Int) -> Unit)? = null

    // Paints
    private val paintBackground = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.FILL
        color = barIndicatorColor
    }

    private val paintIndicator = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.FILL
        color = barIndicatorColor
    }

    private val paintText = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.FILL
        color = itemTextColor
        textSize = itemTextSize
        textAlign = Paint.Align.CENTER
        isFakeBoldText = true
    }

    private var exploreByTouchHelper: AccessibleExploreByTouchHelper

    init {
        obtainStyledAttributes(attrs, defStyleAttr)
        exploreByTouchHelper = AccessibleExploreByTouchHelper(this, items, ::onClickAction)

        ViewCompat.setAccessibilityDelegate(this, exploreByTouchHelper)
    }

    private fun obtainStyledAttributes(attrs: AttributeSet?, defStyleAttr: Int) {
        val typedArray = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.BottomNavigationBar,
            defStyleAttr,
            0
        )

        try {
            barBackgroundColor = typedArray.getColor(
                R.styleable.BottomNavigationBar_bnbBackgroundColor,
                barBackgroundColor
            )
            barIndicatorColor = typedArray.getColor(
                R.styleable.BottomNavigationBar_bnbIndicatorColor,
                barIndicatorColor
            )
            barIndicatorRadius = typedArray.getDimension(
                R.styleable.BottomNavigationBar_bnbIndicatorRadius,
                barIndicatorRadius
            )
            barSideMargins = typedArray.getDimension(
                R.styleable.BottomNavigationBar_bnbSideMargins,
                barSideMargins
            )
            barCornerRadius = typedArray.getDimension(
                R.styleable.BottomNavigationBar_bnbCornerRadius,
                barCornerRadius
            )
            itemPadding = typedArray.getDimension(
                R.styleable.BottomNavigationBar_bnbItemPadding,
                itemPadding
            )
            itemTextColor = typedArray.getColor(
                R.styleable.BottomNavigationBar_bnbTextColor,
                itemTextColor
            )
            itemTextSize = typedArray.getDimension(
                R.styleable.BottomNavigationBar_bnbTextSize,
                itemTextSize
            )
            itemIconSize = typedArray.getDimension(
                R.styleable.BottomNavigationBar_bnbIconSize,
                itemIconSize
            )
            itemIconMargin = typedArray.getDimension(
                R.styleable.BottomNavigationBar_bnbIconMargin,
                itemIconMargin
            )
            itemIconTint = typedArray.getColor(
                R.styleable.BottomNavigationBar_bnbIconTint,
                itemIconTint
            )
            itemIconTintActive = typedArray.getColor(
                R.styleable.BottomNavigationBar_bnbIconTintActive,
                itemIconTintActive
            )
            itemActiveIndex = typedArray.getInt(
                R.styleable.BottomNavigationBar_bnbActiveItem,
                itemActiveIndex
            )
            itemFontFamily = typedArray.getResourceId(
                R.styleable.BottomNavigationBar_bnbItemFontFamily,
                itemFontFamily
            )
            itemAnimDuration = typedArray.getInt(
                R.styleable.BottomNavigationBar_bnbDuration,
                itemAnimDuration.toInt()
            ).toLong()
            itemMenuRes = typedArray.getResourceId(
                R.styleable.BottomNavigationBar_bnbMenu,
                itemMenuRes
            )
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            typedArray.recycle()
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        var lastX = barSideMargins
        itemWidth = (width - (barSideMargins * 2)) / items.size

        // reverse items layout order if layout direction is RTL
        val itemsToLayout = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1
            && layoutDirection == LAYOUT_DIRECTION_RTL
        ) items.reversed() else items

        for (item in itemsToLayout) {
            // Prevent text overflow by shortening the item title
            var shorted = false
            while (paintText.measureText(item.title) > itemWidth - itemIconSize - itemIconMargin - (itemPadding * 2)) {
                item.title = item.title.dropLast(1)
                shorted = true
            }

            // Add ellipsis character to item text if it is shorted
            if (shorted) {
                item.title = item.title.dropLast(1)
                item.title += context.getString(R.string.ellipsis)
            }

            item.rect = RectF(lastX, 0f, itemWidth + lastX, height.toFloat())
            lastX += itemWidth
        }

        // Set initial active item
        applyItemActiveIndex()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // Draw background
        if (barCornerRadius > 0 && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            canvas.drawRoundRect(
                0f, 0f,
                width.toFloat(),
                height.toFloat(),
                barCornerRadius,
                barCornerRadius,
                paintBackground
            )
        } else {
            canvas.drawRect(
                0f, 0f,
                width.toFloat(),
                height.toFloat(),
                paintBackground
            )
        }

        // Draw indicator
        rect.left = indicatorLocation
        rect.top = items[itemActiveIndex].rect.centerY() - itemIconSize / 2 - itemPadding
        rect.right = indicatorLocation + itemWidth
        rect.bottom = items[itemActiveIndex].rect.centerY() + itemIconSize / 2 + itemPadding

        canvas.drawRoundRect(
            rect,
            barIndicatorRadius,
            barIndicatorRadius,
            paintIndicator
        )

        val textHeight = (paintText.descent() + paintText.ascent()) / 2

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1
            && layoutDirection == LAYOUT_DIRECTION_RTL
        ) {
            for ((index, item) in items.withIndex()) {
                val textLength = paintText.measureText(item.title)
                item.icon.mutate()
                item.icon.setBounds(
                    item.rect.centerX()
                        .toInt() - itemIconSize.toInt() / 2 + ((textLength / 2) * (1 - (OPAQUE - item.alpha) / OPAQUE.toFloat())).toInt(),
                    height / 2 - itemIconSize.toInt() / 2,
                    item.rect.centerX()
                        .toInt() + itemIconSize.toInt() / 2 + ((textLength / 2) * (1 - (OPAQUE - item.alpha) / OPAQUE.toFloat())).toInt(),
                    height / 2 + itemIconSize.toInt() / 2
                )

                tintAndDrawIcon(item, index, canvas)

                paintText.alpha = item.alpha
                canvas.drawText(
                    item.title,
                    item.rect.centerX() - (itemIconSize / 2 + itemIconMargin),
                    item.rect.centerY() - textHeight, paintText
                )
            }

        } else {
            for ((index, item) in items.withIndex()) {
                val textLength = paintText.measureText(item.title)

                item.icon.mutate()
                item.icon.setBounds(
                    item.rect.centerX()
                        .toInt() - itemIconSize.toInt() / 2 - ((textLength / 2) * (1 - (OPAQUE - item.alpha) / OPAQUE.toFloat())).toInt(),
                    height / 2 - itemIconSize.toInt() / 2,
                    item.rect.centerX()
                        .toInt() + itemIconSize.toInt() / 2 - ((textLength / 2) * (1 - (OPAQUE - item.alpha) / OPAQUE.toFloat())).toInt(),
                    height / 2 + itemIconSize.toInt() / 2
                )

                tintAndDrawIcon(item, index, canvas)

                paintText.alpha = item.alpha
                canvas.drawText(
                    item.title,
                    item.rect.centerX() + itemIconSize / 2 + itemIconMargin,
                    item.rect.centerY() - textHeight, paintText
                )
            }
        }
    }

    private fun tintAndDrawIcon(
        item: BottomNavigationBarItem,
        index: Int,
        canvas: Canvas
    ) {
        DrawableCompat.setTint(
            item.icon,
            if (index == itemActiveIndex) currentIconTint else itemIconTint
        )

        item.icon.draw(canvas)
    }

    /**
     * Handle item clicks
     */
    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                return true
            }
            MotionEvent.ACTION_UP -> {
                for ((i, item) in items.withIndex()) {
                    if (item.rect.contains(event.x, event.y)) {
                        onClickAction(i)
                        break
                    }
                }
            }
        }
        return super.onTouchEvent(event)
    }

    override fun dispatchHoverEvent(event: MotionEvent): Boolean {
        return exploreByTouchHelper.dispatchHoverEvent(event) || super.dispatchHoverEvent(event)
    }

    private fun onClickAction(viewId: Int) {
        exploreByTouchHelper.invalidateVirtualView(viewId)
        if (viewId != itemActiveIndex) {
            itemActiveIndex = viewId
            onItemSelected?.invoke(viewId)
            onItemBottomNavigationBarSelectedListener?.onItemSelect(viewId)
        } else {
            onItemReselected?.invoke(viewId)
            onItemBottomNavigationBarReselectedListener?.onItemReselect(viewId)
        }
        exploreByTouchHelper.sendEventForVirtualView(
            viewId,
            AccessibilityEvent.TYPE_VIEW_CLICKED
        )
    }

    private fun applyItemActiveIndex() {
        if (items.isNotEmpty()) {
            for ((index, item) in items.withIndex()) {
                if (index == itemActiveIndex) {
                    animateAlpha(item, OPAQUE)
                } else {
                    animateAlpha(item, TRANSPARENT)
                }
            }

            ValueAnimator.ofFloat(
                indicatorLocation,
                items[itemActiveIndex].rect.left
            ).apply {
                duration = itemAnimDuration
                interpolator = DecelerateInterpolator()
                addUpdateListener { animation ->
                    indicatorLocation = animation.animatedValue as Float
                }
                start()
            }

            ValueAnimator.ofObject(ArgbEvaluator(), itemIconTint, itemIconTintActive).apply {
                duration = itemAnimDuration
                addUpdateListener {
                    currentIconTint = it.animatedValue as Int
                }
                start()
            }
        }
    }

    private fun animateAlpha(item: BottomNavigationBarItem, to: Int) {
        ValueAnimator.ofInt(item.alpha, to).apply {
            duration = itemAnimDuration
            addUpdateListener {
                val value = it.animatedValue as Int
                item.alpha = value
                invalidate()
            }
            start()
        }
    }

    /**
     *
     * Just call [BottomNavigationBar.setOnItemSelectedListener] to override [onItemBottomNavigationBarSelectedListener]
     *
     * @sample
     * setOnItemSelectedListener { position ->
     *     //TODO: Something
     * }
     */
    fun setOnItemSelectedListener(listener: (position: Int) -> Unit) {
        onItemBottomNavigationBarSelectedListener =
            object : OnItemBottomNavigationBarSelectedListener {
                override fun onItemSelect(position: Int): Boolean {
                    listener.invoke(position)
                    return true
                }
            }
    }

    /**
     *
     * Just call [BottomNavigationBar.setOnItemReselectedListener] to override [onItemBottomNavigationBarReselectedListener]
     *
     * @sample
     * setOnItemReselectedListener { position ->
     *     //TODO: Something
     * }
     */
    fun setOnItemReselectedListener(listener: (position: Int) -> Unit) {
        onItemBottomNavigationBarReselectedListener =
            object : OnItemBottomNavigationBarReselectedListener {
                override fun onItemReselect(position: Int) {
                    listener.invoke(position)
                }
            }
    }

    companion object {
        private const val INVALID_RES = -1
        private const val DEFAULT_INDICATOR_COLOR = "#2DFFFFFF"
        private const val DEFAULT_TINT = "#C8FFFFFF"

        private const val DEFAULT_SIDE_MARGIN = 10f
        private const val DEFAULT_ITEM_PADDING = 10f
        private const val DEFAULT_ANIM_DURATION = 200L
        private const val DEFAULT_ICON_SIZE = 18F
        private const val DEFAULT_ICON_MARGIN = 4F
        private const val DEFAULT_TEXT_SIZE = 11F
        private const val DEFAULT_CORNER_RADIUS = 20F
        private const val DEFAULT_BAR_CORNER_RADIUS = 0F

        private const val OPAQUE = 255
        private const val TRANSPARENT = 0
    }
}