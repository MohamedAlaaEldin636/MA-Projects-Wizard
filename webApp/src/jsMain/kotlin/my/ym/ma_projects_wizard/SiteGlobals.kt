package my.ym.ma_projects_wizard

//import com.varabyte.kobweb.core.AppGlobals
import androidx.compose.runtime.Composable
import com.varabyte.kobweb.silk.components.text.SpanText
import my.ym.ma_projects_wizard.shared.BuildKonfig

data object SiteGlobals {
	
	//val version = AppGlobals["version"] ?: "0.0.1-SNAPSHOT"
	@Composable
	fun AppVersion() {
		SpanText(text = BuildKonfig.VERSION)
	}
	
}
