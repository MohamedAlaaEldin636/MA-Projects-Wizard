package my.ym.ma_projects_wizard.pages

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.TextAlign
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
import com.varabyte.kobweb.silk.components.forms.Button
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.theme.colors.ColorPalettes
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Text
import my.ym.ma_projects_wizard.components.layouts.PageLayoutData

@InitRoute
fun initHomePage(ctx: InitRouteContext) {
    ctx.data.add(PageLayoutData("Home"))
}

@Page
@Layout(".components.layouts.PageLayout")
@Composable
fun HomePage() {
    val ctx = rememberPageContext()
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Button(
            onClick = {
                ctx.router.tryRoutingTo(RouteOfConverterOfVdAndSvgPage)
            },
            colorPalette = ColorPalettes.Blue
        ) {
            Text("Image Renderer And Converter")
        }
        
        SpanText(
            modifier = Modifier
                .padding(top = 8.px)
                .textAlign(TextAlign.Center),
            text = "-> Can Render Svg & Vector Drawable -> From Text you Paste OR File you pick Inshallah",
        )
        
        SpanText(
            modifier = Modifier.textAlign(TextAlign.Center),
            text = "-> Can Also Convert Svg to Vector Drawable Inshallah",
        )
    }
}
