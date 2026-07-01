package my.ym.ma_projects_wizard.utils

import kotlinx.coroutines.await
import kotlin.js.json

suspend fun convertVectorDrawableToSvg(vectorDrawableXml: String): String? {
	return runCatching {
		// Build the configuration options matching the JS spec
		val options = json(
			"pretty" to true,
			"override" to json(
				"@color/colorPrimary" to "#6200EE",
				"?android:attr/textColorPrimary" to "#000000"
			)
		)
		
		// Call the external JavaScript transform module seamlessly
		transform(vectorDrawableXml, options)
		
		//vd2Svg(vectorDrawableXml = vectorDrawableXml).await()
	}.getOrElse {
		it.printStackTrace(); null
	}
}
