package my.ym.ma_projects_wizard.utils

import kotlinx.browser.document
import org.w3c.dom.url.URL
import org.w3c.files.Blob
import org.w3c.files.BlobPropertyBag

data object DownloadsUtils {
	
	fun download(
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
		val anchor = document.createElement("a").asDynamic()
		anchor.href = url
		anchor.download = fileName // The browser reads the extension straight from here!
		anchor.click()
		
		// 4. Clean up memory
		URL.revokeObjectURL(url)
	}
	
}
