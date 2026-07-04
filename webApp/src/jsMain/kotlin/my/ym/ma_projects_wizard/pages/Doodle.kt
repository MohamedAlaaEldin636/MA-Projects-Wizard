package my.ym.ma_projects_wizard.pages

/*import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.data.add
import com.varabyte.kobweb.core.init.InitRoute
import com.varabyte.kobweb.core.init.InitRouteContext
import com.varabyte.kobweb.core.layout.Layout
import my.ym.ma_projects_wizard.components.layouts.PageLayoutData
import org.jetbrains.compose.web.dom.CheckboxInput
import org.jetbrains.compose.web.dom.DateTimeLocalInput
import org.jetbrains.compose.web.dom.FileInput
import org.jetbrains.compose.web.dom.HiddenInput
import org.jetbrains.compose.web.dom.MonthInput
import org.jetbrains.compose.web.dom.NumberInput
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.graphics.Colors
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.px
import com.varabyte.kobweb.silk.components.forms.Button
import org.jetbrains.compose.web.dom.Text
import org.w3c.dom.HTMLInputElement

@InitRoute
fun initDoodlePage(ctx: InitRouteContext) {
	ctx.data.add(PageLayoutData("Doodle"))
}

@Page
@Layout(".components.layouts.PageLayout")
@Composable
fun DoodlePage() {
	var isChecked by remember { mutableStateOf(false) }
	Column {
		CheckboxInput(
			checked = isChecked,
		) {
			onChange { isChecked = it.value }
		}
		
		DateTimeLocalInput()
		MonthInput()
		NumberInput()
		FileInput()
		HiddenInput()
		
		CustomFileInput()
	}
}

@Composable
fun CustomFileInput() {
	// 1. Keep track of the selected file name to show the user
	var fileName by remember { mutableStateOf("No file chosen") }
	
	// 2. Create a reference to the hidden input element
	var fileInputRef by remember { mutableStateOf<HTMLInputElement?>(null) }
	
	Box(modifier = Modifier.margin(topBottom = 16.px)) {
		
		// 3. The ACTUAL file input (Hidden from view)
		FileInput(
			attrs = {
				// Hide it completely using CSS
				style { property("display", "none") }
				
				// Get a reference to this element when it's rendered
				ref { element ->
					fileInputRef = element
					onDispose { fileInputRef = null }
				}
				
				// Handle file selection
				onChange { event ->
					val files = fileInputRef?.files
					if (files != null && files.length > 0) {
						//val f = files.item(0)
						//f!!.readBytes()
						fileName = files.item(0)?.name ?: "Unknown file"
					}
				}
			}
		)
		
		// 4. Your BEAUTIFUL, custom-styled button layout
		Button(
			onClick = {
				// When clicked, programmatically click the hidden input
				fileInputRef?.click()//onClick { fileInputRef?.click() }
			},
			modifier = Modifier
				.padding(topBottom = 10.px, leftRight = 20.px)
				.backgroundColor(Colors.DeepSkyBlue)
				.color(Colors.White)
				.borderRadius(8.px)
				.border(width = 0.px, style = LineStyle.None)
				.cursor(Cursor.Pointer)
		) {
			Text("Upload File")
		}
	}
	
	// Optional: Display the selected file name next to it
	Text("Selected: $fileName")
}*/
