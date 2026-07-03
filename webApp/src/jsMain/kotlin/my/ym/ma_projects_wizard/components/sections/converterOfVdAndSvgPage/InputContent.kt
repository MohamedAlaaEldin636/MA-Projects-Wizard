package my.ym.ma_projects_wizard.components.sections.converterOfVdAndSvgPage

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.browser.file.readBytes
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.background
import com.varabyte.kobweb.compose.ui.modifiers.border
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.styleModifier
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import com.varabyte.kobweb.silk.theme.colors.palette.color
import com.varabyte.kobweb.silk.theme.colors.palette.toPalette
import my.ym.ma_projects_wizard.components.widgets.FilePickerButton
import my.ym.ma_projects_wizard.viewModels.converterOfVdAndSvg.models.ConverterOfVdAndSvgContentHolder
import my.ym.ma_projects_wizard.viewModels.converterOfVdAndSvg.models.ConverterOfVdAndSvgIntent
import my.ym.ma_projects_wizard.viewModels.converterOfVdAndSvg.models.ConverterOfVdAndSvgState
import org.jetbrains.compose.web.attributes.placeholder
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.TextArea

context(_: ConverterOfVdAndSvgPageScope)
@Composable
fun InputContent(
	modifier: Modifier = Modifier,
	handleIntent: (ConverterOfVdAndSvgIntent) -> Unit,
	state: ConverterOfVdAndSvgState,
) {
	Column(
		modifier = modifier,
		horizontalAlignment = Alignment.CenterHorizontally,
		verticalArrangement = Arrangement.spacedBy(16.px),
	) {
		FilePickerButton(
			modifier = Modifier.fillMaxWidth(),
			onPickFile = { file ->
				val fileDetails = file?.let { file ->
					ConverterOfVdAndSvgContentHolder.IFile.FileDetails(
						fileName = file.name,
						getFileContent = {
							file.readBytes().decodeToString()
						},
					)
				}
				
				handleIntent(
					ConverterOfVdAndSvgIntent.ChangeContentHolder(
						contentHolder = ConverterOfVdAndSvgContentHolder.IFile(fileDetails = fileDetails),
					)
				)
			},
			filesExtensionsToAccept = setOf("svg", "xml"),
		)
		
		TextArea(
			value = when (val contentHolder = state.contentHolder) {
				is ConverterOfVdAndSvgContentHolder.IFile -> {
					val fileName = contentHolder.getFileNameOrNull()
					if (fileName.isNullOrBlank().not()) {
						"File Name -> $fileName"
					} else {
						"Null File"
					}
				}
				is ConverterOfVdAndSvgContentHolder.IText -> {
					contentHolder.text
				}
			},
			attrs = Modifier
				.fillMaxWidth()
				.weight(1)
				.background(Colors.Transparent)
				.borderRadius(8.px)
				.border(
					width = 2.px,
					style = LineStyle.Solid,
					color = ColorMode.current.toPalette().color,
				)
				.styleModifier {
					property("resize", "none")
					property("outline", "none")
				}
				.padding(all = 12.px)
				.toAttrs {
					onInput { event ->
						handleIntent(
							ConverterOfVdAndSvgIntent.ChangeContentHolder(
								contentHolder = ConverterOfVdAndSvgContentHolder.IText(text = event.value),
							)
						)
					}
					
					placeholder("Enter Content here OR Use above Button")
				}
		)
	}
}
