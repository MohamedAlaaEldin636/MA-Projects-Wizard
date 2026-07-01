package my.ym.ma_projects_wizard.pages

/*
El7mdullah! I'm so glad to hear that making the options `dynamic` worked perfectly for your Web target.

To answer your questions about the Desktop target and running JS libraries inside Kotlin:

---

## 1. Libraries for the Desktop/JVM Target

Yes! For your **Desktop target (JVM)**, you do not need to rely on JavaScript libraries. There are official and community libraries built specifically for Java/Kotlin that handle SVG-to-Vector-Drawable conversions.

### For SVG ➔ Vector Drawable (or Compose Vector)

Instead of converting directly to Android's XML `VectorDrawable` and then parsing it in Compose, the standard approach in Compose Multiplatform (Desktop) is to convert SVGs directly into **`ImageVector`** objects (Kotlin code) or use tools that load SVGs directly.

* **Build-time Conversion (Recommended):** You can use the **BuildKonfig** or dedicated Gradle plugins like **`Viktor`** or **`svg-to-compose`**. They take your SVG files and generate Kotlin `ImageVector` properties during the Gradle build. This gives you native, high-performance vectors with autocomplete.
* **Runtime Loading:** If you need to load external SVGs dynamically at runtime on desktop, you can use **`Colil`** (a KMP port of Coil) or **`Compose-Image-Loader` (by Qiniu)** which natively supports rendering SVG strings or files directly onto a Compose Canvas.

### For Vector Drawable ➔ SVG

If you need to convert from an Android Vector XML back to a standard SVG file on the JVM, you can use the official Android build tools library wrapper:

```kotlin
// In your desktop/jvm main dependencies
implementation("com.android.tools:common:31.2.0") // Contains VectorDrawableConverter utilities

```

---

## 2. Can you run JS libraries inside the JVM (Desktop)?

**No, you cannot run JavaScript libraries directly inside the JVM target the same way you run Java inside Kotlin.** Here is why:

* When compiling for **Web**, Kotlin compiles your Kotlin code down into JavaScript. That's why it can easily talk to `svg2vectordrawable` in the browser—they are both running in the same JavaScript engine.
* When compiling for **Desktop**, Kotlin compiles down to JVM bytecode. The JVM does not natively understand JavaScript modules (`npm`, `require`, `Promise`, etc.).

### How to handle this using Clean Architecture (MVI/KMP style)

Since you are structuring a multiplatform app, you should use Kotlin’s **`expect` / `actual**` mechanism. This allows you to use your fast JS library on the web, and a native Java/Kotlin library on the desktop!

#### 1. Define the interface in your common layer (`commonMain`):

```kotlin
expect class SvgConverter() {
    suspend fun convertSvgToVector(svgText: String): String
}

```

#### 2. Implement the Web version using your JS library (`jsMain` / `wasmJsMain`):

```kotlin
import kotlinx.coroutines.await

actual class SvgConverter {
    actual suspend fun convertSvgToVector(svgText: String): String {
        // Calls the JS library you just fixed!
        return svg2vectordrawable(svgText).await()
    }
}

```

#### 3. Implement the Desktop version using a Java/Kotlin library (`desktopMain` or `jvmMain`):

On Desktop, you can use a pure Java implementation or an embedded parsing library to transform the XML nodes from SVG format to Vector Drawable format:

```kotlin
actual class SvgConverter {
    actual suspend fun convertSvgToVector(svgText: String): String {
        // Use a JVM-specific library or custom pure-Kotlin XML parsing logic here
        // For example, using standard Java DOM parsers to transform tags
        return transformSvgToVectorXml(svgText)
    }
}

```

This way, your UI code just calls `SvgConverter().convertSvgToVector(text)` without caring whether it's running on a browser or a desktop computer, **inshallah**!
 */
