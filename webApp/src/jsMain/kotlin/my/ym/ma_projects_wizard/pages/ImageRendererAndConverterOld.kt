package my.ym.ma_projects_wizard.pages
/*
package my.ym.ma_projects_wizard.pages

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.varabyte.kobweb.browser.file.readBytes
import com.varabyte.kobweb.compose.ui.toAttrs
import org.jetbrains.compose.web.dom.Div
import org.w3c.dom.Element
import org.w3c.dom.parsing.DOMParser
import com.varabyte.kobweb.compose.css.TextAlign
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Box
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
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.background
import com.varabyte.kobweb.compose.ui.modifiers.border
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxHeight
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.height
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.textAlign
import com.varabyte.kobweb.compose.ui.styleModifier
import com.varabyte.kobweb.silk.components.forms.Button
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import com.varabyte.kobweb.silk.theme.colors.palette.color
import com.varabyte.kobweb.silk.theme.colors.palette.toPalette
import my.ym.ma_projects_wizard.components.widgets.FilePickerButton
import my.ym.ma_projects_wizard.toSitePalette
import my.ym.ma_projects_wizard.utils.overflowWrapAnywhere
import org.jetbrains.compose.web.attributes.placeholder
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.H3
import org.jetbrains.compose.web.dom.TextArea
import org.w3c.dom.HTMLDivElement
import org.w3c.files.File

const val RouteOfImageRendererAndConverterPage = "/image-renderer-and-converter"

@InitRoute
fun initImageRendererAndConverterPage(ctx: InitRouteContext) {
	ctx.data.add(PageLayoutData("Image Renderer"))
}

@Page(RouteOfImageRendererAndConverterPage)
@Layout(".components.layouts.PageLayout")
@Composable
fun ImageRendererAndConverterPage() {
	Column(
		modifier = Modifier.fillMaxSize(),
		horizontalAlignment = Alignment.CenterHorizontally,
	) {
		val contentHolderState = remember {
			mutableStateOf<ContentHolder>(ContentHolder.IText(text = ""))
		}
		
		val contentTypeState = remember { mutableStateOf(ContentType.Unknown) }
		
		val outputValueState = remember {
			mutableStateOf<OutputValue>(OutputValue.Unknown(text = ""))
		}
		
		CalculateContentTypeEffect(contentHolderState, contentTypeState, outputValueState = outputValueState)
		
		H3 {
			SpanText(text = "Detected Content -> ")
			
			SpanText(
				text = "Type: ${contentTypeState.value}",
				modifier = Modifier.color(ColorMode.current.toSitePalette().brand.primary)
			)
			
			SpanText(text = ", ")
			
			SpanText(
				text = "Holder: ${contentHolderState.value.toSimpleString()}",
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
				contentHolderState = contentHolderState,
			)
			
			OutputContent(
				modifier = Modifier.fillMaxHeight().fillMaxWidth(),
				outputValueState = outputValueState,
			)
		}
		
		Div(attrs = Modifier.height(24.px).toAttrs())
	}
}

@Composable
private fun InputContent(
	modifier: Modifier = Modifier,
	contentHolderState: MutableState<ContentHolder>,
) {
	Column(
		modifier = modifier,
		horizontalAlignment = Alignment.CenterHorizontally,
		verticalArrangement = Arrangement.spacedBy(16.px),
	) {
		FilePickerButton(
			modifier = Modifier.fillMaxWidth(),
			onPickFile = {
				contentHolderState.value = ContentHolder.IFile(it)
			},
			filesExtensionsToAccept = setOf("svg"),
		)
		
		TextArea(
			value = when (val contentHolder = contentHolderState.value) {
				is ContentHolder.IFile -> {
					val fileName = contentHolder.file?.name.orEmpty()
					if (fileName.isNotBlank()) {
						"File Name -> $fileName"
					} else {
						"Unknown File Name"
					}
				}
				is ContentHolder.IText -> {
					contentHolder.text
				}
			},
			attrs = Modifier
				.fillMaxWidth()
				.weight(1)
				.background(Colors.Transparent)
				.borderRadius(8.px)
				.border(
					width = 2.px,
					style = LineStyle.Solid,
					color = ColorMode.current.toPalette().color,
				)
				.styleModifier {
					property("resize", "none")
					property("outline", "none")
				}
				.padding(all = 12.px)
				.toAttrs {
					onInput { event ->
						contentHolderState.value = ContentHolder.IText(text = event.value)
					}
					
					placeholder("Enter Content here OR Use above Button")
				}
		)
	}
}

@Composable
private fun OutputContent(
	modifier: Modifier = Modifier,
	outputValueState: MutableState<OutputValue>,
) {
	Column(
		modifier = modifier,
		horizontalAlignment = Alignment.CenterHorizontally,
		verticalArrangement = Arrangement.spacedBy(16.px),
	) {
		Button(
			modifier = Modifier.fillMaxWidth(),
			colorPalette = ColorMode.current.toSitePalette().brand.primaryPalette,
			onClick = {
				saveAsSvg()
			},
			enabled = outputValueState.value !is OutputValue.Unknown,
		) {
			SpanText(
				modifier = Modifier
					.textAlign(TextAlign.Center)
					.overflowWrapAnywhere(),
				text = "Save as Svg"
			)
		}
		
		Button(
			modifier = Modifier.fillMaxWidth(),
			colorPalette = ColorMode.current.toSitePalette().brand.primaryPalette,
			onClick = {
				saveAsVectorDrawable()
			},
			enabled = outputValueState.value !is OutputValue.Unknown,
		) {
			SpanText(
				modifier = Modifier
					.textAlign(TextAlign.Center)
					.overflowWrapAnywhere(),
				text = "Save as Vector Drawable"
			)
		}
		
		Box(
			modifier = Modifier
				.fillMaxWidth()
				.weight(1f)
				.borderRadius(8.px)
				.border(
					width = 2.px,
					style = LineStyle.Solid,
					color = ColorMode.current.toPalette().color,
				)
				.padding(all = 12.px),
			contentAlignment = Alignment.Center,
		) {
			val svgText = outputValueState.value.getSvgTextOrNull()
			
			var htmlDivElement by remember { mutableStateOf<HTMLDivElement?>(null) }
			
			LaunchedEffect(key1 = svgText, key2 = htmlDivElement) {
				if (htmlDivElement != null && svgText != null) {
					htmlDivElement?.innerHTML = svgText.sanitizeSvgText()
				}
			}
			
			if (svgText == null) {
				SpanText(
					modifier = Modifier.overflowWrapAnywhere(),
					text = ">>>> Error <<<<\n\n${outputValueState.value.text}",
				)
			} else {
				Div(
					attrs = Modifier
						.fillMaxSize()
						.toAttrs {
							ref {
								htmlDivElement = it
								onDispose {  }
							}
						}
				)
			}
		}
	}
}

// todo 3 more buttons 1- inverse background switch, 2- show svg text 3- show vector drawable text 4- show image

private fun saveAsSvg() {
	TODO()
}

private fun saveAsVectorDrawable() {
	TODO()
}

private fun String.sanitizeSvgText(): String {
	return replace(Regex("(?<!-)width=\"([^\"]+)\""), "width=\"100%\"")
		.replace(Regex("(?<!-)height=\"([^\"]+)\""), "height=\"100%\"")
}

private fun convertVectorToSvg(xmlStr: String): String {
	try {
		// 1. Use the browser's built-in XML Parser
		val parser = DOMParser()
		val xmlDoc = parser.parseFromString(xmlStr, "application/xml")
		val vectorNode = xmlDoc.querySelector("vector") ?: return ""
		
		// 2. Extract dimensions
		val viewBoxWidth = vectorNode.getAttribute("android:viewportWidth") ?: "64"
		val viewBoxHeight = vectorNode.getAttribute("android:viewportHeight") ?: "64"
		
		val svgContent = StringBuilder()
		val defsContent = StringBuilder()
		var gradientCounter = 0
		
		// 3. Find and extract all paths
		val paths = xmlDoc.querySelectorAll("path")
		for (i in 0 until paths.length) {
			val pathNode = paths.item(i) as Element
			val d = pathNode.getAttribute("android:pathData") ?: ""
			var fill = pathNode.getAttribute("android:fillColor") ?: "none"
			
			// 4. Handle Complex Gradients (<aapt:attr>)
			val gradientNode = pathNode.querySelector("gradient")
			if (gradientNode != null) {
				gradientCounter++
				val id = "vector_gradient_$gradientCounter"
				fill = "url(#$id)" // Reference the gradient ID in SVG
				
				val type = gradientNode.getAttribute("android:type")
				val items = gradientNode.querySelectorAll("item")
				val stopsHtml = StringBuilder()
				
				for (j in 0 until items.length) {
					val item = items.item(j) as Element
					val offset = item.getAttribute("android:offset") ?: "0"
					// Strip alpha if it has 8 hex characters #FF5383EC -> #5383EC
					var color = item.getAttribute("android:color") ?: "#000"
					if (color.startsWith("#") && color.length == 9) {
						color = "#" + color.substring(3)
					}
					stopsHtml.append("<stop offset=\"$offset\" stop-color=\"$color\"/>")
				}
				
				if (type == "linear") {
					val x1 = gradientNode.getAttribute("android:startX") ?: "0"
					val y1 = gradientNode.getAttribute("android:startY") ?: "0"
					val x2 = gradientNode.getAttribute("android:endX") ?: "0"
					val y2 = gradientNode.getAttribute("android:endY") ?: "0"
					defsContent.append("<linearGradient id=\"$id\" x1=\"$x1\" y1=\"$y1\" x2=\"$x2\" y2=\"$y2\" gradientUnits=\"userSpaceOnUse\">$stopsHtml</linearGradient>")
				} else if (type == "radial") {
					val cx = gradientNode.getAttribute("android:centerX") ?: "0"
					val cy = gradientNode.getAttribute("android:centerY") ?: "0"
					val r = gradientNode.getAttribute("android:gradientRadius") ?: "0"
					defsContent.append("<radialGradient id=\"$id\" cx=\"$cx\" cy=\"$cy\" r=\"$r\" gradientUnits=\"userSpaceOnUse\">$stopsHtml</radialGradient>")
				}
			}
			
			// Clean hex color alpha if it's plain text (#FF6075F2 -> #6075F2)
			if (fill.startsWith("#") && fill.length == 9) fill = "#" + fill.substring(3)
			
			svgContent.append("<path d=\"$d\" fill=\"$fill\"/>")
		}
		
		// 5. Package it into a native SVG format
		return """
            <svg width="100%" height="100%" viewBox="0 0 $viewBoxWidth $viewBoxHeight" xmlns="http://www.w3.org/2000/svg">
                <defs>$defsContent</defs>
                $svgContent
            </svg>
        """.trimIndent()
		
	} catch (_: Throwable) {
		return ""
	}
}

@Composable
private fun CalculateContentTypeEffect(
	contentHolderState: MutableState<ContentHolder>,
	contentTypeState: MutableState<ContentType>,
	outputValueState: MutableState<OutputValue>,
) {
	println("Pre Eff contentHolderState.value ${contentHolderState.value}")
	LaunchedEffect(key1 = contentHolderState.value) {
		println("After Eff contentHolderState.value ${contentHolderState.value}")
		contentTypeState.value = ContentType.Loading
		contentTypeState.value = when (val contentHolder = contentHolderState.value) {
			is ContentHolder.IFile -> {
				if (contentHolder.file != null) {
					outputValueState.value = contentHolder.file.readBytes().decodeToString().calculateOutputValue()
					
					outputValueState.value.calculateContentType()
				} else {
					outputValueState.value = OutputValue.Unknown(text = "`Null` File is Provided")
					
					ContentType.Unknown
				}
			}
			is ContentHolder.IText -> {
				if (contentHolder.text.isNotBlank()) {
					outputValueState.value = contentHolder.text.calculateOutputValue()
					
					outputValueState.value.calculateContentType()
				} else {
					outputValueState.value = OutputValue.Unknown(text = "Blank Text is Provided")
					
					ContentType.Unknown
				}
			}
		}
	}
}

private fun String.calculateOutputValue(): OutputValue {
	return when {
		"<svg" in this && "</svg>" in this -> {
			OutputValue.SVG(text = this)
		}
		"<vector" in this && "</vector>" in this -> {
			OutputValue.VectorDrawable(text = this)
		}
		else -> {
			OutputValue.Unknown(text = this)
		}
	}
}

private fun OutputValue.calculateContentType(): ContentType {
	return when (this) {
		is OutputValue.SVG -> ContentType.Svg
		is OutputValue.VectorDrawable -> ContentType.VectorDrawable
		is OutputValue.Unknown -> ContentType.Unknown
	}
}

private sealed class OutputValue {
	
	abstract val text: String
	
	data class SVG(override val text: String) : OutputValue()
	
	data class VectorDrawable(override val text: String) : OutputValue()
	
	data class Unknown(override val text: String) : OutputValue()
	
	fun getSvgTextOrNull(): String? {
		return when (this) {
			is SVG -> text
			is VectorDrawable -> convertVectorToSvg(xmlStr = text)
			is Unknown -> null
		}
	}
	
}

private sealed class ContentHolder {
	
	data class IFile(val file: File?) : ContentHolder()
	
	data class IText(val text: String) : ContentHolder()
	
	fun toSimpleString(): String {
		return this::class.simpleName?.drop(1).toString()
	}
	
}

private enum class ContentType {
	Loading,
	Svg,
	VectorDrawable,
	Unknown,
}
*/
