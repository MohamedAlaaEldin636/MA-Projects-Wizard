package my.ym.ma_projects_wizard.components.widgets

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.silk.components.forms.Button
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import my.ym.ma_projects_wizard.toSitePalette
import my.ym.ma_projects_wizard.utils.overflowWrapAnywhere
import org.jetbrains.compose.web.attributes.accept
import org.jetbrains.compose.web.dom.FileInput
import org.w3c.dom.HTMLInputElement
import org.w3c.files.File

@Composable
fun FilePickerButton(
	modifier: Modifier = Modifier,
	onPickFile: (File?) -> Unit,
	text: String = "Pick a File",
	filesExtensionsToAccept: Set<String> = emptySet(),
) {
	Box(
		modifier = modifier,
	) {
		var fileInputRef by remember { mutableStateOf<HTMLInputElement?>(null) }

		FileInput(
			attrs = {
				// Hide it completely using CSS
				style { property("display", "none") }
				
				// Get a reference to this element when it's rendered
				ref { element ->
					fileInputRef = element
					onDispose { fileInputRef = null }
				}
				
				if (filesExtensionsToAccept.isNotEmpty()) {
					val extensions = filesExtensionsToAccept.joinToString(",") { ".$it" }
					accept(extensions)
				}
				
				// Handle file selection
				onChange { _ ->
					val listOfFiles = fileInputRef?.files
					val file = if (listOfFiles != null && listOfFiles.length > 0) {
						listOfFiles.item(0)
					} else {
						null
					}
					onPickFile(file)
				}
			}
		)
		
		Button(
			modifier = modifier,//.background(color = ColorMode.current.toSitePalette().brand.primary),
			colorPalette = ColorMode.current.toSitePalette().brand.primaryPalette,
			onClick = {
				fileInputRef?.click()
			}
		) {
			SpanText(
				modifier = Modifier.overflowWrapAnywhere(),
				text = text,
			)
		}
	}
}
