package my.ym.ma_projects_wizard.pages

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import com.varabyte.kobweb.compose.ui.toAttrs
import org.jetbrains.compose.web.dom.Div
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.data.add
import com.varabyte.kobweb.core.init.InitRoute
import com.varabyte.kobweb.core.init.InitRouteContext
import com.varabyte.kobweb.core.layout.Layout
import my.ym.ma_projects_wizard.components.layouts.PageLayoutData
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.border
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxHeight
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.height
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import my.ym.ma_projects_wizard.components.sections.converterOfVdAndSvgPage.ConverterOfVdAndSvgPageScope
import my.ym.ma_projects_wizard.components.sections.converterOfVdAndSvgPage.InputContent
import my.ym.ma_projects_wizard.components.sections.converterOfVdAndSvgPage.OutputContent
import my.ym.ma_projects_wizard.toSitePalette
import my.ym.ma_projects_wizard.viewModels.ConverterOfVdAndSvgViewModelImpl
import my.ym.ma_projects_wizard.viewModels.converterOfVdAndSvg.models.ConverterOfVdAndSvgIntent
import my.ym.ma_projects_wizard.viewModels.converterOfVdAndSvg.models.ConverterOfVdAndSvgState
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.H3

const val RouteOfConverterOfVdAndSvgPage = "/converter-of-vd-and-svg"

@InitRoute
fun initConverterOfVdAndSvgPage(ctx: InitRouteContext) {
	ctx.data.add(PageLayoutData("Image Renderer"))
}

@Page(RouteOfConverterOfVdAndSvgPage)
@Layout(".components.layouts.PageLayout")
@Composable
fun ConverterOfVdAndSvgPage() {
	val viewModel = remember { ConverterOfVdAndSvgViewModelImpl() }
	
	val state by viewModel.state.collectAsState()
	
	context(ConverterOfVdAndSvgPageScope) {
		PageContent(
			handleIntent = viewModel::handleIntent,
			state = state,
		)
	}
}

context(_: ConverterOfVdAndSvgPageScope)
@Composable
private fun PageContent(
	handleIntent: (ConverterOfVdAndSvgIntent) -> Unit,
	state: ConverterOfVdAndSvgState,
) {
	Column(
		modifier = Modifier.fillMaxSize(),
		horizontalAlignment = Alignment.CenterHorizontally,
	) {
		H3 {
			SpanText(text = "Detected Content -> ")
			
			SpanText(
				text = "Type: ${state.contentType}",
				modifier = Modifier.color(ColorMode.current.toSitePalette().brand.primary)
			)
			
			SpanText(text = ", ")
			
			SpanText(
				text = "Holder: ${state.contentHolder.toSimpleString()}",
				modifier = Modifier.color(ColorMode.current.toSitePalette().brand.accent)
			)
		}
		
		Row(
			modifier = Modifier
				.fillMaxWidth()
				.weight(1)
				.borderRadius(16.px)
				.border(
					color = ColorMode.current.toSitePalette().brand.primary,
					width = 2.px,
					style = LineStyle.Solid,
				)
				.padding(all = 16.px),
			horizontalArrangement = Arrangement.spacedBy(space = 16.px),
		) {
			InputContent(
				modifier = Modifier.fillMaxHeight().fillMaxWidth(),
				handleIntent = handleIntent,
				state = state,
			)
			
			OutputContent(
				modifier = Modifier.fillMaxHeight().fillMaxWidth(),
				handleIntent = handleIntent,
				state = state,
			)
		}
		
		// todo COLLECT ALL TODOS IN A SINGLE PLACE INSHALLAH. ex. local gitignored place Inshallah
		//  then make new master as this then compat into 1 commit Inshallah, or add it in project's README.md 3ade Inshallah,
		//  and maybe even add it here too Inshallah.
		
		// todo re-adjust page layout to contain that padding already and maybe footor of version and made by who
		//  or in header since it already takes space Inshallah.
		Div(attrs = Modifier.height(24.px).toAttrs())
	}
}

// todo better code structuring to be able to show on desktop too Inshallah.

// todo multiple files at same time Inshallah + drag n drop Inshallah.
//  files should have own placement as a small flow row 2 rows max and hz scroll with close click
//  in case want files + 1 text as well Inshallah

// todo 3 more buttons 1- inverse background switch, 2- show svg text 3- show vector drawable text 4- show image
