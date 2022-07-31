package ui

import com.soywiz.korge.annotations.KorgeExperimental
import com.soywiz.korge.input.onClick
import com.soywiz.korge.ui.*
import com.soywiz.korge.view.*
import com.soywiz.korim.text.TextAlignment
import kotlinx.coroutines.delay
import masterMindSolver.IMasterMindSolver
import masterMindSolver.MasterMindSolver
import masterMindSolver.Response
import ui.StatusText.*
import kotlin.time.Duration.Companion.milliseconds


@KorgeExperimental
inline fun Container.ui1(
    codeLength: Int,
    codeItems: List<UIHorizontalFill.()->View>,
    width: Double = this.width,
    height: Double = this.height,
    block: @ViewDslMarker UI1.() -> Unit = {}
) = append(UI1(codeLength, codeItems, width, height)).apply(block)


@KorgeExperimental
class UI1 private constructor(
    width: Double,
    height: Double,
    private val masterMindSolver: MasterMindSolver<UIHorizontalFill.()->View>
): UIView(width, height), IMasterMindSolver<UIHorizontalFill.()->View> by masterMindSolver {

    constructor(codeLength: Int, codeItems: List<UIHorizontalFill.()->View>, width: Double, height: Double) :
            this(width, height, MasterMindSolver(codeLength, codeItems))

    private val labelResponse = uiText("B / W") {
        alignTopToTopOf(this@UI1, 32)
        alignLeftToLeftOf(this@UI1, 32)
    }

    private val labelGuess = uiText("Guess") {
        alignTopToTopOf(this@UI1, 32)
        centerXOn(this@UI1)
    }

    private val labelPossible = uiText("possible Codes") {
        textAlignment = TextAlignment.RIGHT
        alignTopToTopOf(this@UI1, 32)
        alignRightToRightOf(this@UI1, 32)
    }

    private val responseStack = uiVerticalStack {
        alignTopToBottomOf(labelResponse, 32)
        alignLeftToLeftOf(this@UI1, 32)
    }

    private val guessStack = uiVerticalStack {
        alignTopToBottomOf(labelGuess, 32)
        alignLeftToLeftOf(labelGuess)
    }

    private val possibleStack = uiVerticalStack {
        alignTopToBottomOf(labelPossible, 32)
        alignRightToRightOf(this@UI1, 32)
    }

    private val black: UIIncDecNumber  = uiIncDecNumber(0, 0, codeLength) {
        alignBottomToBottomOf(this@UI1, 32)
        alignLeftToLeftOf(this@UI1, 32)
        onSetValue {
            white.maxValue = codeLength - it.value
        }
    }

    private val labelBlack = uiText( "Black") {
        alignBottomToTopOf(black, 32)
        alignLeftToLeftOf(black)
    }

    private val labelWhite = uiText ("White") {
        alignBottomToBottomOf(labelBlack)
        centerXOn(this@UI1)
    }

    private val white: UIIncDecNumber = uiIncDecNumber(0,0, codeLength) {
        alignBottomToBottomOf(black)
        alignLeftToLeftOf(labelWhite)
        onSetValue {
            black.maxValue = codeLength - it.value
        }
    }

    private val continueButton = uiButton("Continue") {
        alignBottomToBottomOf(black)
        alignRightToRightOf(this@UI1, 32)

        onClick {
            val response = Response(black.value, white.value)
            black.value = 0
            white.value = 0
            addToResponseStack(response)
            this@UI1.disable()
            if (response.isSolved()) {
                status.text = StatusText[Solved]
                end()
            } else {
                status.text = StatusText[Processing]
                delay(500.milliseconds)
                try {
                    masterMindSolver.processNext(response)
                    addToPossibleStack()
                    addToGuessStack()
                    status.text = StatusText[WaitingForInput]
                    this@UI1.enable()
                } catch (e: IllegalStateException) {
                    status.text = StatusText[Error(e.message)]
                    end()
                }
            }
        }
    }

    init {
        addToPossibleStack()
        addToGuessStack()
    }

    private val status = uiText(StatusText[WaitingForInput]) {
        alignBottomToTopOf(labelBlack, 32)
        alignLeftToLeftOf(this@UI1, 32)
    }

    override fun updateState() {
        black.enabled = enabled
        white.enabled = enabled
        continueButton.enabled = enabled
        super.updateState()
    }

    private fun end() {
        uiButton("Done") {
            alignTopToTopOf(continueButton)
            alignLeftToLeftOf(continueButton)
            onClick { this@UI1.removeFromParent() }
        }
    }

    private fun addToResponseStack(response: Response) {
        responseStack.addChild(uiText("${response.black} / ${response.white}"))
    }

    private fun addToGuessStack() {
        guessStack.addChild(
            uiHorizontalFill {
                masterMindSolver.guess.forEach {
                    addChild(it())
                }
            }
        )
    }

    private fun addToPossibleStack() {
        possibleStack.addChild(uiText("${masterMindSolver.s.size}") { textAlignment = TextAlignment.RIGHT })
    }
}

private sealed class StatusText {

    abstract val string: String

    companion object {
        operator fun get(text: StatusText) = text.string
    }

    object WaitingForInput : StatusText() { override val string = "Enter how much black and white pegs the last guess has." }
    object Processing : StatusText() { override val string = "Processing..." }
    object Solved : StatusText() { override val string = "Code solved!" }
    class Error(msg: String?) : StatusText() { override val string = "Error! $msg"}
}