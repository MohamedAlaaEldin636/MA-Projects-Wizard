package my.ym.ma_projects_wizard.viewModels.converterOfVdAndSvg.models

sealed class ConverterOfVdAndSvgContentHolder {
	
	data class IFile(val fileDetails: FileDetails?) : ConverterOfVdAndSvgContentHolder() {
		data class FileDetails(val fileName: String, val getFileContent: suspend () -> String)
	}
	
	data class IText(val text: String) : ConverterOfVdAndSvgContentHolder()
	
	fun toSimpleString(): String {
		return this::class.simpleName?.drop(1).toString()
	}
	
	fun getFileNameOrNull(): String? {
		return if (this is IFile) fileDetails?.fileName else null
	}
	
	fun isEmpty(): Boolean {
		return when (this) {
			is IFile -> fileDetails == null
			is IText -> text.isBlank()
		}
	}
	
}
