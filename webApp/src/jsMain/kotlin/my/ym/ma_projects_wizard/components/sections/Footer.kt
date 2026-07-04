package my.ym.ma_projects_wizard.components.sections

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.TextAlign
import com.varabyte.kobweb.compose.css.WhiteSpace
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.navigation.Link
import com.varabyte.kobweb.silk.components.navigation.UncoloredLinkVariant
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.base
import com.varabyte.kobweb.silk.style.toModifier
import com.varabyte.kobweb.silk.style.vars.color.ColorVar
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import org.jetbrains.compose.web.dom.Span
import my.ym.ma_projects_wizard.toSitePalette
import org.jetbrains.compose.web.css.cssRem

val FooterStyle = CssStyle.base {
    Modifier
        .backgroundColor(colorMode.toSitePalette().nearBackground)
        .padding(all = 1.cssRem)
}

@Composable
fun Footer(modifier: Modifier = Modifier) {
    Box(FooterStyle.toModifier().then(modifier), contentAlignment = Alignment.Center) {
        Span(Modifier.textAlign(TextAlign.Center).toAttrs()) {
            val sitePalette = ColorMode.current.toSitePalette()
            SpanText("Built by ")
            Link(
                "https://mohamedalaaeldin636.github.io/Portfolio-Public/",
                "Mohamed Alaaeldin",
                Modifier.setVariable(ColorVar, sitePalette.brand.primary),
                variant = UncoloredLinkVariant
            )
            SpanText(", Source Code on ")

            Link(
                "/todo",
                "GitHub",
                Modifier.setVariable(ColorVar, sitePalette.brand.accent).whiteSpace(WhiteSpace.NoWrap),
                variant = UncoloredLinkVariant
            )
        }
    }
}
