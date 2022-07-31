import com.soywiz.korge.Korge
import com.soywiz.korge.component.onAttachDetach
import com.soywiz.korge.view.circle
import com.soywiz.korge.view.fixedSizeContainer
import com.soywiz.korim.color.Colors
import ui.UI1
import ui.ui1


suspend fun main() = Korge(width = 512, height = 512, bgcolor = Colors["#2b2b2b"]) {

	//ui1(4, (1..8).map { { UIText("$it ") } } )

	val colors = listOf(
		Colors.ORANGERED,
		Colors.PURPLE,
		Colors.ANTIQUEWHITE,
		Colors.DARKRED,
		Colors.GOLD,
		Colors.DARKSLATEBLUE,
		Colors.DEEPSKYBLUE,
		Colors.DARKSLATEGRAY
	)

	fun ui1(): UI1 = ui1(4, List(8) {
		{
			val d = height
			fixedSizeContainer(d, d) { circle(d * .48, colors[it]) }
		}
	}).onAttachDetach {
		ui1()
	}

	ui1()
}