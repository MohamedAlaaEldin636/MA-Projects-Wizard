package my.ym.ma_projects_wizard

import com.varabyte.kobweb.core.AppGlobals

data object SiteGlobals {
	
	val version = AppGlobals["version"] ?: "0.0.1-SNAPSHOT"
	
}
