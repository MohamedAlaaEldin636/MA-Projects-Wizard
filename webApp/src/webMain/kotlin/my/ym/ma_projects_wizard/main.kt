package my.ym.ma_projects_wizard

import androidx.compose.ui.ExperimentalComposeUiApi

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
	// BuildKonfig.IS_KOBWEB_ACTIVE // todo maybe useless Inshallah.
	val isRendered = renderComposeHtmlApp()
	if (isRendered.not()) {
		renderComposeComposableApp()
	}
}
