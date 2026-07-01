package my.ym.ma_projects_wizard.utils

import kotlinx.coroutines.await
import kotlin.js.json

suspend fun convertSvgToVectorDrawable(svgCode: String): String? {
	return runCatching {
		val options = json(
			"floatPrecision" to 3,
			"xmlTag" to true // Adds <?xml version="1.0" encoding="utf-8"?>
		)
		
		//Svg2VectorDrawable.fetch(svgCode = svgCode, options = options).await()
		svg2vectordrawable(svgCode/*, options*/).await()
	}.getOrElse {
		it.printStackTrace(); null
	}
}
