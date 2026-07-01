package my.ym.ma_projects_wizard.utils

import com.varabyte.kobweb.compose.css.OverflowWrap
import com.varabyte.kobweb.compose.css.WhiteSpace
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.overflowWrap
import com.varabyte.kobweb.compose.ui.modifiers.whiteSpace

fun Modifier.overflowWrapAnywhere(): Modifier {
	return whiteSpace(WhiteSpace.Normal)
		.overflowWrap(OverflowWrap.Anywhere)
}
