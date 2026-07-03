package my.ym.ma_projects_wizard.utils

import kotlin.js.Promise

@JsModule("svg2vectordrawable")
@JsNonModule
external fun svg2vectordrawable(
	svgCode: String,
	options: dynamic = definedExternally
): Promise<String>
