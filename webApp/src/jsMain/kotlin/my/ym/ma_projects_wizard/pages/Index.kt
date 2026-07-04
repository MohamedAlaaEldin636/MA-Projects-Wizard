package my.ym.ma_projects_wizard.pages

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.TextAlign
import com.varabyte.kobweb.compose.css.WhiteSpace
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.data.add
import com.varabyte.kobweb.core.init.InitRoute
import com.varabyte.kobweb.core.init.InitRouteContext
import com.varabyte.kobweb.core.layout.Layout
import com.varabyte.kobweb.core.rememberPageContext
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.selectors.active
import com.varabyte.kobweb.silk.style.selectors.hover
import com.varabyte.kobweb.silk.style.toModifier
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import com.varabyte.kobweb.silk.theme.colors.palette.background
import com.varabyte.kobweb.silk.theme.colors.palette.color
import com.varabyte.kobweb.silk.theme.colors.palette.toPalette
import org.jetbrains.compose.web.css.px
import my.ym.ma_projects_wizard.components.layouts.PageLayoutData
import my.ym.ma_projects_wizard.toSitePalette
import org.jetbrains.compose.web.css.cssRem

@InitRoute
fun initHomePage(ctx: InitRouteContext) {
    ctx.data.add(PageLayoutData("Home"))
}

@Page
@Layout(".components.layouts.PageLayout")
@Composable
fun HomePage() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        NavItemButton(
	        title = "Image Renderer And Converter",
	        description = "Render Svg & Vector Drawable From Text you Paste OR File you pick Inshallah\n" +
                    "Convert Svg to Vector Drawable & Vice Versa Inshallah",
	        route = RouteOfConverterOfVdAndSvgPage,
        )
    }
}

@Composable
private fun NavItemButton(
    title: String,
    description: String,
    route: String,
) {
    val ctx = rememberPageContext()
    val colorMode = ColorMode.current
    
    Column(
        modifier = CustomInteractiveCardStyle.toModifier()
            .fillMaxWidth()
            
            .borderRadius(12.px)
            .cursor(Cursor.Pointer)
            
            .onClick { ctx.router.navigateTo(route) }
            
            .padding(16.px),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        val textColor = if (colorMode.isDark) {
            colorMode.toPalette().color
        } else {
            colorMode.toPalette().background
        }
        SpanText(
            modifier = Modifier
                .whiteSpace(WhiteSpace.PreLine)
                .textAlign(TextAlign.Center)
                .fontWeight(FontWeight.Bold)
                .fontSize(1.25.cssRem)
                .color(textColor),
            text = title,
        )
        
        val descriptionColor = if (colorMode.isDark) {
            colorMode.toSitePalette().brand.primaryPalette._100
        } else {
            colorMode.toSitePalette().nearBackground
        }
        SpanText(
            modifier = Modifier
                .padding(top = 8.px)
                .whiteSpace(WhiteSpace.PreLine)
                .textAlign(TextAlign.Center)
                .fontSize(1.cssRem)
                .color(descriptionColor),
            text = description
        )
    }
}
val CustomInteractiveCardStyle = CssStyle {
    val colorPalette = colorMode.toSitePalette().brand.primaryPalette
    
    base {
        Modifier
            .cursor(Cursor.Pointer)
            .backgroundColor(if (colorMode.isDark) colorPalette._800 else colorPalette._500)
    }
    hover {
        Modifier.backgroundColor(if (colorMode.isDark) colorPalette._700 else colorPalette._600)
    }
    active {
        Modifier.backgroundColor(if (colorMode.isDark) colorPalette._600 else colorPalette._700)
    }
}
