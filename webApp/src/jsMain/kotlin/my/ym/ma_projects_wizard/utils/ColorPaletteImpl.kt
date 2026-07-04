@file:Suppress("ObjectPropertyName")

package my.ym.ma_projects_wizard.utils

import com.varabyte.kobweb.compose.css.ColorInterpolationMethod
import com.varabyte.kobweb.compose.css.functions.colorMix
import com.varabyte.kobweb.compose.ui.graphics.Color
import com.varabyte.kobweb.silk.theme.colors.ColorPalette

data class ColorPaletteImpl(
	override val _50: Color,
	override val _100: Color,
	override val _200: Color,
	override val _300: Color,
	override val _400: Color,
	override val _500: Color,
	override val _600: Color,
	override val _700: Color,
	override val _800: Color,
	override val _900: Color,
) : ColorPalette

val ColorPalette._750: Color
	get() {
		return colorMix(
			color1 = _700,
			color2 = _800,
			interpolation = ColorInterpolationMethod.Srgb,
		).asDynamic()
	}
