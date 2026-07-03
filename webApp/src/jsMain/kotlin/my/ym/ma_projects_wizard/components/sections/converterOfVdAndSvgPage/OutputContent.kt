package my.ym.ma_projects_wizard.components.sections.converterOfVdAndSvgPage

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.varabyte.kobweb.compose.css.TextAlign
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.border
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.maxHeight
import com.varabyte.kobweb.compose.ui.modifiers.maxWidth
import com.varabyte.kobweb.compose.ui.modifiers.minHeight
import com.varabyte.kobweb.compose.ui.modifiers.minWidth
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.textAlign
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.forms.Button
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import com.varabyte.kobweb.silk.theme.colors.palette.color
import com.varabyte.kobweb.silk.theme.colors.palette.toPalette
import my.ym.ma_projects_wizard.domain.models.MAResult
import my.ym.ma_projects_wizard.toSitePalette
import my.ym.ma_projects_wizard.utils.overflowWrapAnywhere
import my.ym.ma_projects_wizard.viewModels.converterOfVdAndSvg.models.ConverterOfVdAndSvgIntent
import my.ym.ma_projects_wizard.viewModels.converterOfVdAndSvg.models.ConverterOfVdAndSvgState
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Div
import org.w3c.dom.HTMLDivElement

context(_: ConverterOfVdAndSvgPageScope)
@Composable
fun OutputContent(
	modifier: Modifier = Modifier,
	handleIntent: (ConverterOfVdAndSvgIntent) -> Unit,
	state: ConverterOfVdAndSvgState,
) {
	Column(
		modifier = modifier,
		horizontalAlignment = Alignment.CenterHorizontally,
		verticalArrangement = Arrangement.spacedBy(16.px),
	) {
		Button(
			modifier = Modifier.fillMaxWidth(),
			colorPalette = ColorMode.current.toSitePalette().brand.primaryPalette,
			onClick = {
				when (val maResult = state.outputValueMAResult) {
					is MAResult.Success -> {
						handleIntent(ConverterOfVdAndSvgIntent.SaveAsSvg(outputValue = maResult.value))
					}
					else -> {}
				}
			},
			enabled = state.outputValueMAResult is MAResult.Success,
		) {
			SpanText(
				modifier = Modifier
					.textAlign(TextAlign.Center)
					.overflowWrapAnywhere(),
				text = "Save as Svg"
			)
		}
		
		Button(
			modifier = Modifier.fillMaxWidth(),
			colorPalette = ColorMode.current.toSitePalette().brand.primaryPalette,
			onClick = {
				when (val maResult = state.outputValueMAResult) {
					is MAResult.Success -> {
						handleIntent(ConverterOfVdAndSvgIntent.SaveAsVectorDrawable(outputValue = maResult.value))
					}
					else -> {}
				}
			},
			enabled = state.outputValueMAResult is MAResult.Success,
		) {
			SpanText(
				modifier = Modifier
					.textAlign(TextAlign.Center)
					.overflowWrapAnywhere(),
				text = "Save as Vector Drawable"
			)
		}
		
		Box(
			modifier = Modifier
				.fillMaxWidth()
				.weight(1f)
				.borderRadius(8.px)
				.border(
					width = 2.px,
					style = LineStyle.Solid,
					color = ColorMode.current.toPalette().color,
				)
				.padding(all = 12.px),
			contentAlignment = Alignment.Center,
		) {
			var htmlDivElement by remember { mutableStateOf<HTMLDivElement?>(null) }
			
			LaunchedEffect(key1 = htmlDivElement, key2 = state.outputValueMAResult) {
				val maResult = state.outputValueMAResult
				htmlDivElement?.innerHTML = if (maResult is MAResult.Success) {
					maResult.value.svgString.sanitizeSvgText()
				} else {
					""
				}
			}
			
			val outputInfoMsg = when (val maResult = state.outputValueMAResult) {
				null -> {
					"No Value Provided Yet Inshallah"
				}
				MAResult.Loading -> {
					"Loading... Inshallah"
				}
				is MAResult.Failure -> {
					maResult.throwable?.message ?: "Unknown Error - C1"
				}
				is MAResult.Success -> {
					null
				}
			}
			
			if (outputInfoMsg != null) {
				SpanText(
					modifier = Modifier.overflowWrapAnywhere(),
					text = outputInfoMsg,
				)
			}
			
			Div(
				attrs = Modifier
					.maxWidth(100.percent)
					.maxHeight(100.percent)
					.minWidth(0.px)   // Forces it to ignore intrinsic width
					.minHeight(0.px)
					.toAttrs {
						ref {
							htmlDivElement = it
							onDispose { }
						}
					}
			)
		}
	}
}

private fun String.sanitizeSvgText(): String {
	return replace(Regex("(?<!-)width=\"([^\"]+)\""), "width=\"100%\"")
		.replace(Regex("(?<!-)height=\"([^\"]+)\""), "height=\"100%\"")
}
