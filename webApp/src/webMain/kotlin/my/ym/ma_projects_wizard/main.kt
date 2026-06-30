package my.ym.ma_projects_wizard

import androidx.compose.ui.ExperimentalComposeUiApi

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    val isRendered = renderComposeHtmlApp()
	if (isRendered.not()) {
		renderComposeComposableApp()
	}
}
