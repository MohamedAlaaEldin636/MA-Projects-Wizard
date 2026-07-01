package my.ym.ma_projects_wizard.pages

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import com.varabyte.kobweb.compose.ui.modifiers.maxHeight
import com.varabyte.kobweb.compose.ui.modifiers.maxWidth
import com.varabyte.kobweb.compose.ui.modifiers.minHeight
import com.varabyte.kobweb.compose.ui.modifiers.minWidth
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.textAlign
import com.varabyte.kobweb.compose.ui.styleModifier
import com.varabyte.kobweb.silk.components.forms.Button
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import com.varabyte.kobweb.silk.theme.colors.palette.color
import com.varabyte.kobweb.silk.theme.colors.palette.toPalette
import kotlinx.coroutines.launch
import my.ym.ma_projects_wizard.components.widgets.FilePickerButton
import my.ym.ma_projects_wizard.domain.models.MAResult
import my.ym.ma_projects_wizard.domain.models.failure
import my.ym.ma_projects_wizard.domain.models.success
import my.ym.ma_projects_wizard.toSitePalette
import my.ym.ma_projects_wizard.utils.convertSvgToVectorDrawable
import my.ym.ma_projects_wizard.utils.convertVectorDrawableToSvg
import my.ym.ma_projects_wizard.utils.overflowWrapAnywhere
import org.jetbrains.compose.web.attributes.placeholder
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.H3
import org.jetbrains.compose.web.dom.TextArea
import org.w3c.dom.HTMLDivElement
import org.w3c.dom.url.URL
import org.w3c.files.Blob
import org.w3c.files.BlobPropertyBag
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
				contentHolderState = contentHolderState,
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
			filesExtensionsToAccept = setOf("svg", "xml"),
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
	contentHolderState: MutableState<ContentHolder>,
) {
	Column(
		modifier = modifier,
		horizontalAlignment = Alignment.CenterHorizontally,
		verticalArrangement = Arrangement.spacedBy(16.px),
	) {
		val coroutineScope = rememberCoroutineScope()
		
		Button(
			modifier = Modifier.fillMaxWidth(),
			colorPalette = ColorMode.current.toSitePalette().brand.primaryPalette,
			onClick = {
				coroutineScope.launch {
					outputValueState.value.saveAsSvg(contentHolder = contentHolderState.value)
				}
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
				coroutineScope.launch {
					outputValueState.value.saveAsVectorDrawable(contentHolder = contentHolderState.value)
				}
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
			val svgTextMAResultState = remember(key1 = outputValueState.value) {
				mutableStateOf<MAResult<String>?>(null)
			}
			
			var htmlDivElement by remember { mutableStateOf<HTMLDivElement?>(null) }
			
			LaunchedEffect(
				key1 = htmlDivElement,
				key2 = outputValueState.value,
			) {
				if (htmlDivElement != null && svgTextMAResultState.value == null) {
					svgTextMAResultState.value = MAResult.Loading
					val svgText = outputValueState.value.getSvgTextOrNull()
					svgTextMAResultState.value = if (svgText != null) {
						htmlDivElement?.innerHTML = svgText.sanitizeSvgText()
						MAResult.success(value = svgText)
					} else {
						htmlDivElement?.innerHTML = ""
						MAResult.failure()
					}
				}
			}
			
			if (svgTextMAResultState.value !is MAResult.Success) {
				val text = when (svgTextMAResultState.value) {
					null -> {
						"No Value Provided Yet Inshallah"
					}
					MAResult.Loading -> {
						"Loading... Inshallah"
					}
					else -> {
						">>>> Error <<<<\n\n${outputValueState.value.text}"
					}
				}
				
				SpanText(
					modifier = Modifier.overflowWrapAnywhere(),
					text = text,
				)
			}
			
			Div(
				attrs = Modifier
					.maxWidth(100.percent)
					.maxHeight(100.percent)
					.minWidth(0.px)   // Forces it to ignore intrinsic width
					.minHeight(0.px)
					.toAttrs {
						ref {
							htmlDivElement = it
							onDispose { }
						}
					}
			)
		}
	}
}

// todo 3 more buttons 1- inverse background switch, 2- show svg text 3- show vector drawable text 4- show image

private suspend fun OutputValue.saveAsSvg(
	contentHolder: ContentHolder
) {
	download(
		fileName = "${getFileNameWithoutExtension(contentHolder = contentHolder)}.svg",
		content = getSvgTextOrNull() ?: return,
	)
}

private suspend fun OutputValue.saveAsVectorDrawable(
	contentHolder: ContentHolder
) { // todo incorrect with the search made it just a black circle so re-adjust Inshallah.
	download(
		fileName = "${getFileNameWithoutExtension(contentHolder = contentHolder)}.xml",
		content = getVectorDrawableTextOrNull() ?: return,
	)
}

// todo handle error ex. on save returns nothing so snackbar or msg Inshallah

// todo try to convert svg to vd not working or vice versa wa7da mnhom ya3ne Inshallah

private fun OutputValue.getFileNameWithoutExtension(
	contentHolder: ContentHolder
): String {
	return contentHolder.getFileNameOrNull()
		?.replaceAfterLast(".", "")
		?.dropLast(1)
		?: "untitled"
}

private fun download(
	fileName: String,
	content: String,
) {
	// 1. Detect or assign the correct MIME type based on the file extension
	val mimeType = when {
		fileName.endsWith(".svg") -> "image/svg+xml"
		fileName.endsWith(".xml") -> "application/xml"
		fileName.endsWith(".json") -> "application/json"
		else -> "text/plain" // Works perfectly for .sas, .txt, .csv, etc.
	}
	
	// 2. Create the blob with the specific type
	val blob = Blob(arrayOf(content), BlobPropertyBag(type = mimeType))
	val url = URL.createObjectURL(blob)
	
	// 3. Trigger the browser download anchor
	val anchor = kotlinx.browser.document.createElement("a").asDynamic()
	anchor.href = url
	anchor.download = fileName // The browser reads the extension straight from here!
	anchor.click()
	
	// 4. Clean up memory
	URL.revokeObjectURL(url)
}

private fun String.sanitizeSvgText(): String {
	return replace(Regex("(?<!-)width=\"([^\"]+)\""), "width=\"100%\"")
		.replace(Regex("(?<!-)height=\"([^\"]+)\""), "height=\"100%\"")
}

/*private fun convertVectorToSvg(xmlStr: String): String {
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

private fun convertSvgToVector(svgStr: String): String {
	try {
		val parser = DOMParser()
		val xmlDoc = parser.parseFromString(svgStr, "application/xml")
		val svgNode = xmlDoc.querySelector("svg") ?: return ""
		
		val viewBox = svgNode.getAttribute("viewBox") ?: ""
		var viewportWidth = "64"
		var viewportHeight = "64"
		
		if (viewBox.isNotEmpty()) {
			val parts = viewBox.trim().split(Regex("\\s+"))
			if (parts.size >= 4) {
				viewportWidth = parts[2]
				viewportHeight = parts[3]
			}
		} else {
			viewportWidth = svgNode.getAttribute("width")?.replace("px", "") ?: "64"
			viewportHeight = svgNode.getAttribute("height")?.replace("px", "") ?: "64"
		}
		
		val gradientMap = mutableMapOf<String, Element>()
		xmlDoc.querySelectorAll("linearGradient, radialGradient").let { nodes ->
			for (i in 0 until nodes.length) {
				val gradEl = nodes.item(i) as Element
				val id = gradEl.getAttribute("id")
				if (!id.isNullOrEmpty()) {
					gradientMap["#$id"] = gradEl
					gradientMap["url(#$id)"] = gradEl
				}
			}
		}
		
		val vectorContent = StringBuilder()
		
		// Process all paths
		val paths = xmlDoc.querySelectorAll("path")
		for (i in 0 until paths.length) {
			val pathNode = paths.item(i) as Element
			val d = pathNode.getAttribute("d") ?: ""
			
			// 1. Get Stroke Attributes (Crucial for outlines!)
			val stroke = pathNode.getAttribute("stroke")?.trim()
			val strokeWidth = pathNode.getAttribute("stroke-width")?.trim()
			val strokeLineCap = pathNode.getAttribute("stroke-linecap")?.trim()
			val strokeLineJoin = pathNode.getAttribute("stroke-linejoin")?.trim()
			
			// 2. Get Fill Attribute (Default to transparent if a stroke is explicitly present)
			val defaultFill = if (!stroke.isNullOrEmpty()) "none" else "#000000"
			val fill = pathNode.getAttribute("fill")?.trim() ?: defaultFill
			
			vectorContent.append("    <path\n")
			vectorContent.append("        android:pathData=\"$d\"\n")
			
			// 3. Handle Stroke Mapping to VectorDrawable
			if (!stroke.isNullOrEmpty() && stroke != "none") {
				val androidStrokeColor = if (stroke == "currentColor") "#000000" else stroke
				vectorContent.append("        android:strokeColor=\"$androidStrokeColor\"\n")
				if (!strokeWidth.isNullOrEmpty()) {
					vectorContent.append("        android:strokeWidth=\"$strokeWidth\"\n")
				}
				if (!strokeLineCap.isNullOrEmpty()) {
					vectorContent.append("        android:strokeLineCap=\"$strokeLineCap\"\n")
				}
				if (!strokeLineJoin.isNullOrEmpty()) {
					vectorContent.append("        android:strokeLineJoin=\"$strokeLineJoin\"\n")
				}
			}
			
			// 4. Handle Fill Mapping (unchanged gradient logic, added fallback fix)
			if (fill.startsWith("url") || gradientMap.containsKey(fill)) {
				val gradKey = if (fill.startsWith("url")) {
					fill.substringAfter("url(").substringBefore(")")
				} else fill
				
				val gradientNode = gradientMap[gradKey] ?: gradientMap["#$gradKey"]
				
				if (gradientNode != null) {
					vectorContent.append("    >\n")
					vectorContent.append("        <aapt:attr name=\"android:fillColor\">\n")
					
					val isGradientLinear = gradientNode.tagName == "linearGradient"
					val type = if (isGradientLinear) "linear" else "radial"
					
					vectorContent.append("            <gradient android:type=\"$type\"")
					
					if (isGradientLinear) {
						val x1 = gradientNode.getAttribute("x1") ?: "0"
						val y1 = gradientNode.getAttribute("y1") ?: "0"
						val x2 = gradientNode.getAttribute("x2") ?: "0"
						val y2 = gradientNode.getAttribute("y2") ?: "0"
						vectorContent.append(" android:startX=\"$x1\" android:startY=\"$y1\" android:endX=\"$x2\" android:endY=\"$y2\"")
					} else {
						val cx = gradientNode.getAttribute("cx") ?: "0"
						val cy = gradientNode.getAttribute("cy") ?: "0"
						val r = gradientNode.getAttribute("r") ?: "0"
						vectorContent.append(" android:centerX=\"$cx\" android:centerY=\"$cy\" android:gradientRadius=\"$r\"")
					}
					vectorContent.append(">\n")
					
					val stops = gradientNode.querySelectorAll("stop")
					for (j in 0 until stops.length) {
						val stop = stops.item(j) as Element
						val offset = stop.getAttribute("offset") ?: "0"
						val color = stop.getAttribute("stop-color") ?: "#000000"
						vectorContent.append("                <item android:offset=\"$offset\" android:color=\"$color\"/>\n")
					}
					
					vectorContent.append("            </gradient>\n")
					vectorContent.append("        </aapt:attr>\n")
					vectorContent.append("    </path>\n")
				} else {
					vectorContent.append("        android:fillColor=\"#000000\"/>\n")
				}
			} else {
				val androidColor = if (fill == "none" || fill == "transparent") "#00000000" else if (fill == "currentColor") "#000000" else fill
				vectorContent.append("        android:fillColor=\"$androidColor\"/>\n")
			}
		}
		
		return """
          <vector xmlns:android="http://schemas.android.com/apk/res/android"
              xmlns:aapt="http://schemas.android.com/aapt"
              android:width="${viewportWidth}dp"
              android:height="${viewportHeight}dp"
              android:viewportWidth="$viewportWidth"
              android:viewportHeight="$viewportHeight">
          $vectorContent
          </vector>
        """.trimIndent()
	} catch (_: Throwable) {
		return ""
	}
}*/

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
	
	suspend fun getSvgTextOrNull(): String? {
		return when (this) {
			is SVG -> text
			is VectorDrawable -> convertVectorDrawableToSvg(text)
			is Unknown -> null
		}
	}
	
	suspend fun getVectorDrawableTextOrNull(): String? {
		return when (this) {
			is SVG -> convertSvgToVectorDrawable(text)
			is VectorDrawable -> text
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
	
	fun getFileNameOrNull(): String? {
		return if (this is IFile) file?.name else null
	}
	
}

private enum class ContentType {
	Loading,
	Svg,
	VectorDrawable,
	Unknown,
}
