package my.ym.ma_projects_wizard.data.manager

import my.ym.ma_projects_wizard.domain.models.MAResult

expect object ConverterManager {

	suspend fun convertVectorDrawableToSvg(vectorDrawable: String): MAResult.Immediate<String>
	
	suspend fun convertSvgToVectorDrawable(svg: String): MAResult.Immediate<String>

}
