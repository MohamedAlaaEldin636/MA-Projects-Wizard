package my.ym.ma_projects_wizard.viewModels.converterOfVdAndSvg.models

sealed interface ConverterOfVdAndSvgIntent {
	
	data class ChangeContentHolder(val contentHolder: ConverterOfVdAndSvgContentHolder) : ConverterOfVdAndSvgIntent
	
	data class SaveAsSvg(val outputValue: ConverterOfVdAndSvgOutputValue) : ConverterOfVdAndSvgIntent
	data class SaveAsVectorDrawable(val outputValue: ConverterOfVdAndSvgOutputValue) : ConverterOfVdAndSvgIntent
	
}
