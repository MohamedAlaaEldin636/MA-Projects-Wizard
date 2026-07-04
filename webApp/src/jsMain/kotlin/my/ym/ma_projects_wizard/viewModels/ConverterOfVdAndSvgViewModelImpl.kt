package my.ym.ma_projects_wizard.viewModels

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import my.ym.ma_projects_wizard.data.manager.ConverterManager
import my.ym.ma_projects_wizard.domain.models.MAResult
import my.ym.ma_projects_wizard.domain.models.failure
import my.ym.ma_projects_wizard.domain.models.mapImmediate
import my.ym.ma_projects_wizard.utils.DownloadsUtils
import my.ym.ma_projects_wizard.viewModels.converterOfVdAndSvg.ConverterOfVdAndSvgViewModel
import my.ym.ma_projects_wizard.viewModels.converterOfVdAndSvg.models.ConverterOfVdAndSvgContentHolder
import my.ym.ma_projects_wizard.viewModels.converterOfVdAndSvg.models.ConverterOfVdAndSvgContentType
import my.ym.ma_projects_wizard.viewModels.converterOfVdAndSvg.models.ConverterOfVdAndSvgIntent
import my.ym.ma_projects_wizard.viewModels.converterOfVdAndSvg.models.ConverterOfVdAndSvgOutputValue

class ConverterOfVdAndSvgViewModelImpl : ConverterOfVdAndSvgViewModel() {
	
	override fun handleIntent(intent: ConverterOfVdAndSvgIntent) {
		when (intent) {
			is ConverterOfVdAndSvgIntent.ChangeContentHolder -> {
				updateState {
					it.copy(
						contentHolder = intent.contentHolder,
						contentType = ConverterOfVdAndSvgContentType.Loading,
						outputValueMAResult = MAResult.Loading,
					)
				}
				
				viewModelScope.launch {
					val content = when (
						val contentHolder = intent.contentHolder
					) {
						is ConverterOfVdAndSvgContentHolder.IFile -> {
							contentHolder.fileDetails?.getFileContent().orEmpty()
						}
						is ConverterOfVdAndSvgContentHolder.IText -> {
							contentHolder.text
						}
					}
					
					val contentType = getContentType(content = content)
					
					val maResultOfOutputValue = when (contentType) {
						ConverterOfVdAndSvgContentType.Loading, ConverterOfVdAndSvgContentType.Unknown -> {
							val errorMsg = if (content.isBlank()) "Blank Content" else "Unknown"
							
							MAResult.failure(throwable = RuntimeException("Error -> $errorMsg"))
						}
						ConverterOfVdAndSvgContentType.Svg -> {
							ConverterManager
								.convertSvgToVectorDrawable(svg = content)
								.mapImmediate { vectorDrawableString ->
									ConverterOfVdAndSvgOutputValue(
										svgString = content,
										vectorDrawableString = vectorDrawableString,
									)
								}
						}
						ConverterOfVdAndSvgContentType.VectorDrawable -> {
							ConverterManager
								.convertVectorDrawableToSvg(vectorDrawable = content)
								.mapImmediate { svgString ->
									ConverterOfVdAndSvgOutputValue(
										svgString = svgString,
										vectorDrawableString = content,
									)
								}
						}
					}
					
					updateState {
						it.copy(
							contentType = contentType,
							outputValueMAResult = maResultOfOutputValue,
						)
					}
				}
			}
			is ConverterOfVdAndSvgIntent.SaveAsSvg -> {
				saveAs(
					extension = "svg",
					contentHolder = stateValue.contentHolder,
					content = intent.outputValue.svgString,
				)
			}
			is ConverterOfVdAndSvgIntent.SaveAsVectorDrawable -> {
				saveAs(
					extension = "xml",
					contentHolder = stateValue.contentHolder,
					content = intent.outputValue.vectorDrawableString,
				)
			}
		}
	}
	
	private fun getContentType(content: String): ConverterOfVdAndSvgContentType {
		return when {
			"<svg" in content && "</svg>" in content -> {
				ConverterOfVdAndSvgContentType.Svg
			}
			"<vector" in content && "</vector>" in content -> {
				ConverterOfVdAndSvgContentType.VectorDrawable
			}
			else -> {
				ConverterOfVdAndSvgContentType.Unknown
			}
		}
	}
	
	private fun saveAs(
		extension: String,
		contentHolder: ConverterOfVdAndSvgContentHolder,
		content: String,
	) {
		DownloadsUtils.download(
			fileName = "${getFileNameWithoutExtension(contentHolder = contentHolder)}.$extension",
			content = content,
		)
	}
	
	private fun getFileNameWithoutExtension(
		contentHolder: ConverterOfVdAndSvgContentHolder,
	): String {
		return contentHolder.getFileNameOrNull()
			?.replaceAfterLast(".", "")
			?.dropLast(1)
			?: "untitled"
	}
	
}
