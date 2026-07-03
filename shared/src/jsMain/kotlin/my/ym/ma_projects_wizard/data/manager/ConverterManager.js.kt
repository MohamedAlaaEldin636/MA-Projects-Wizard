package my.ym.ma_projects_wizard.data.manager

import kotlinx.coroutines.await
import my.ym.ma_projects_wizard.domain.models.MAResult
import my.ym.ma_projects_wizard.domain.models.failure
import my.ym.ma_projects_wizard.domain.models.success
import my.ym.ma_projects_wizard.utils.svg2vectordrawable
import my.ym.ma_projects_wizard.utils.transform
import kotlin.js.json

actual data object ConverterManager {
	
	actual suspend fun convertVectorDrawableToSvg(vectorDrawable: String): MAResult.Immediate<String> {
		return kotlin.runCatching {
			// Build the configuration options matching the JS spec
			val options = json(
				"pretty" to true,
				"override" to json(
					"@color/colorPrimary" to "#6200EE",
					"?android:attr/textColorPrimary" to "#000000"
				)
			)
			
			MAResult.success(value = transform(vectorDrawable, options))
		}.getOrElse {
			it.printStackTrace()
			
			MAResult.failure(throwable = it)
		}
	}
	
	actual suspend fun convertSvgToVectorDrawable(svg: String): MAResult.Immediate<String> {
		return kotlin.runCatching {
			val options = json(
				"floatPrecision" to 3,
				"xmlTag" to true // Adds <?xml version="1.0" encoding="utf-8"?>
			)
			
			MAResult.success(value = svg2vectordrawable(svg, options).await())
		}.getOrElse {
			it.printStackTrace()
			
			MAResult.failure(throwable = it)
		}
	}
	
}
