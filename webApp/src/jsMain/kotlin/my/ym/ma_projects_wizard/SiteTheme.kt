package my.ym.ma_projects_wizard

import com.varabyte.kobweb.compose.ui.graphics.Color
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.silk.init.InitSilk
import com.varabyte.kobweb.silk.init.InitSilkContext
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import com.varabyte.kobweb.silk.theme.colors.ColorPalette
import com.varabyte.kobweb.silk.theme.colors.palette.background
import com.varabyte.kobweb.silk.theme.colors.palette.color
import my.ym.ma_projects_wizard.utils.ColorPaletteImpl

/**
 * @property nearBackground A useful color to apply to a container that should differentiate itself from the background
 *   but just a little.
 */
class SitePalette(
    val nearBackground: Color,
    val cobweb: Color,
    val brand: Brand,
) {
    class Brand(
        val primary: Color = Color.rgb(0x3C83EF),
        val primaryPalette: ColorPalette,
        val accent: Color = Color.rgb(0xF3DB5B),
    )
}

object SitePalettes {
    val light = SitePalette(
        nearBackground = Color.rgb(0xF4F6FA),
        cobweb = Colors.LightGray,
        brand = SitePalette.Brand(
            primary = Color.rgb(0x3C83EF),
            primaryPalette = DarkPrimaryPalette,
            accent = Color.rgb(0xFCBA03),
        )
    )
    val dark = SitePalette(
        nearBackground = Color.rgb(0x13171F),
        cobweb = Colors.LightGray.inverted(),
        brand = SitePalette.Brand(
            primary = Color.rgb(0x3C83EF),
            primaryPalette = DarkPrimaryPalette,
            accent = Color.rgb(0xF3DB5B),
        )
    )
}

private val DarkPrimaryPalette = ColorPaletteImpl(
    _50 = Color.rgb(0xF0F6FF),   // Softest accent background
    _100 = Color.rgb(0xD9E8FC),  // Light tint / Hover states
    _200 = Color.rgb(0x3C83EF),  // <-- Your base color
    _300 = Color.rgb(0x2B6ED1),  // Slightly deeper blue
    _400 = Color.rgb(0x1F59B3),  // Solid primary midtone
    _500 = Color.rgb(0x164696),  // Standard text / strong brand color
    _600 = Color.rgb(0x0F3478),  // Darker accent
    _700 = Color.rgb(0x0A245A),  // Deep navy
    _800 = Color.rgb(0x06173D),  // Very deep navy
    _900 = Color.rgb(0x030B21)   // Deepest midnight background shadow
)

fun ColorMode.toSitePalette(): SitePalette {
    return when (this) {
        ColorMode.LIGHT -> SitePalettes.light
        ColorMode.DARK -> SitePalettes.dark
    }
}

@InitSilk
fun initTheme(ctx: InitSilkContext) {
    ctx.theme.palettes.light.background = Color.rgb(0xFAFAFA)
    ctx.theme.palettes.light.color = Colors.Black
    ctx.theme.palettes.dark.background = Color.rgb(0x06080B)
    ctx.theme.palettes.dark.color = Colors.White
}
