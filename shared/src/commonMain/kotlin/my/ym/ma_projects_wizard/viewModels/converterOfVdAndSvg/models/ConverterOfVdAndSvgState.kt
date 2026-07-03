package my.ym.ma_projects_wizard.viewModels.converterOfVdAndSvg.models

import my.ym.ma_projects_wizard.domain.models.MAResult

/**
 * @property outputValueMAResult `null` means no input yet Inshallah.
 */
data class ConverterOfVdAndSvgState(
	val contentHolder: ConverterOfVdAndSvgContentHolder = ConverterOfVdAndSvgContentHolder.IText(text = ""),
	val contentType: ConverterOfVdAndSvgContentType = ConverterOfVdAndSvgContentType.Unknown,
	val outputValueMAResult: MAResult<ConverterOfVdAndSvgOutputValue>? = null,
)
