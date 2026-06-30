package my.ym.ma_projects_wizard.domain.models

sealed class PlatformType {
	
	data object Desktop : PlatformType()
	
	sealed class Web : PlatformType() {
		
		data object Js : Web()
		
		data object WasmJs : Web()
		
	}
	
}

expect fun getPlatformType(): PlatformType
