package ui

import com.soywiz.korge.input.onClick
import com.soywiz.korge.ui.UIView
import com.soywiz.korge.ui.uiButton
import com.soywiz.korge.ui.uiText
import com.soywiz.korge.view.*
import com.soywiz.korio.async.Signal

inline fun Container.uiIncDecNumber(
    value: Int,
    minValue: Int = Int.MIN_VALUE,
    maxValue: Int = Int.MAX_VALUE,
    width: Double = 96.0,
    height: Double = 80.0,
    block: @ViewDslMarker UIIncDecNumber.() -> Unit = {}
    ) = append(UIIncDecNumber(value, minValue, maxValue, width, height)).apply(block)

class UIIncDecNumber(
    defaultValue: Int,
    var minValue: Int = Int.MIN_VALUE,
    var maxValue: Int = Int.MAX_VALUE,
    width: Double = 96.0,
    height: Double = 80.0
) : UIView(width, height) {
    var value = defaultValue
        set(value) {
            field = value.coerceIn(minValue, maxValue)
            textView.text = field.toString()
            onSetValue(this)
        }

    val onSetValue = Signal<UIIncDecNumber>()

    private val textView = uiText(value.toString())

    private val incrementButton = uiButton("+", width = width / 3, height = height * .4) {
        alignBottomToBottomOf(this@UIIncDecNumber)
        alignLeftToLeftOf(this@UIIncDecNumber)
        onClick { value++ }
    }

    private val decrementButton = uiButton("-", width = width / 3, height = height * .4) {
        alignBottomToBottomOf(this@UIIncDecNumber)
        alignRightToRightOf(this@UIIncDecNumber)
        onClick { value-- }
    }

    override fun updateState() {
        incrementButton.enabled = enabled
        decrementButton.enabled = enabled
        super.updateState()
    }
}