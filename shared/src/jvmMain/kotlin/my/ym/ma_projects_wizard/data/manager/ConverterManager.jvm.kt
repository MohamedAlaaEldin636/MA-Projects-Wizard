package my.ym.ma_projects_wizard.data.manager

import Vector2Svg
import com.android.ide.common.vectordrawable.Svg2Vector
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import my.ym.ma_projects_wizard.domain.models.MAResult
import my.ym.ma_projects_wizard.domain.models.failure
import my.ym.ma_projects_wizard.domain.models.success
import java.io.ByteArrayOutputStream
import java.io.File
import java.util.UUID

actual data object ConverterManager {
	
	actual suspend fun convertVectorDrawableToSvg(vectorDrawable: String): MAResult.Immediate<String> {
		val uniqueId = UUID.randomUUID().toString()
		val tempDir = getTempDir()
		
		val tempXmlFile = File(tempDir, "vector_$uniqueId.xml")
		val expectedSvgFile = File(tempDir, "vector_$uniqueId.svg")
		
		return try {
			// 2. Write your XML string into the temporary file
			tempXmlFile.writeText(vectorDrawable)
			
			// 3. Pass the temporary file to the library
			val converter = Vector2Svg(tempXmlFile, expectedSvgFile)
			val success = converter.createSvg()
			
			if (success && expectedSvgFile.exists()) {
				// 4. Read the generated SVG content back into a String
				val svgContent = expectedSvgFile.readText()
				MAResult.success(value = svgContent)
			} else {
				MAResult.failure()
			}
		} catch (throwable: Throwable) {
			throwable.printStackTrace()
			MAResult.failure(throwable = throwable)
		} finally {
			// 5. Clean up! Delete the temporary files from the system
			if (tempXmlFile.exists()) tempXmlFile.delete()
			if (expectedSvgFile.exists()) expectedSvgFile.delete()
		}
	}
	
	actual suspend fun convertSvgToVectorDrawable(svg: String): MAResult.Immediate<String> {
		return withContext(context = Dispatchers.IO) {
			// 1. Create a safe temporary file for the input SVG content
			val uniqueId = UUID.randomUUID().toString()
			val tempDir = getTempDir()
			val tempSvgFile = File(tempDir, "input_$uniqueId.svg")
			
			// 2. Prepare an in-memory output stream to catch the converted XML string
			val outputStream = ByteArrayOutputStream()
			
			return@withContext try {
				// Write incoming SVG string into the temp file
				tempSvgFile.writeText(svg)
				
				// 3. Call Google's Svg2Vector API
				val errorLog: String = Svg2Vector.parseSvgToXml(tempSvgFile.toPath(), outputStream)
				
				// If the error log is empty, conversion succeeded!
				if (errorLog.isEmpty()) {
					MAResult.success(value = outputStream.toString("UTF-8"))
				} else {
					println("Svg2Vector Error Log: $errorLog")
					
					MAResult.failure(throwable = RuntimeException(errorLog))
				}
			} catch (throwable: Throwable) {
				throwable.printStackTrace()
				MAResult.failure(throwable = throwable)
			} finally {
				// 4. Clean up resources and temporary file
				outputStream.close()
				if (tempSvgFile.exists()) {
					tempSvgFile.delete()
				}
			}
		}
	}
	
	private fun getTempDir(): String {
		return System.getProperty("java.io.tmpdir")
	}
	
}
