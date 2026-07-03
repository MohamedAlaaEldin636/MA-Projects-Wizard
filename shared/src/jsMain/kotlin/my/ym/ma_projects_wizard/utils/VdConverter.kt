@file:JsModule("vector-drawable-svg")
@file:JsNonModule
package my.ym.ma_projects_wizard.utils

import kotlin.js.Json

/**
 * Binds directly to the 'transform' function from 'vector-drawable-svg' NPM module.
 * * @param xmlContent The raw XML String of the Android Vector Drawable.
 * @param options A dynamic JS configuration object (e.g., pretty, override map).
 * @return The converted, valid SVG string layout.
 */
external fun transform(xmlContent: String, options: Json = definedExternally): String
