import com.codingfeline.buildkonfig.compiler.FieldSpec.Type

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.buildkonfig)
}

buildkonfig {
    packageName = "my.ym.ma_projects_wizard.shared"
    exposeObjectWithName = "BuildKonfig"
    
    defaultConfigs {
        buildConfigField(Type.STRING, "VERSION", "1.0.0")
    }
}

kotlin {
    jvm()
    
    js {
        browser()
    }
    
    compilerOptions {
        freeCompilerArgs.add("-Xcontext-parameters")
    }
    
    sourceSets {
        commonMain.dependencies {
            implementation(libs.compose.runtime)
            implementation(libs.androidx.lifecycle.viewmodel)
            
            implementation(libs.kotlinx.serialization)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
        jvmMain.dependencies {
            implementation(files("src/jvmMain/libs/vector2svg-1.1.1.jar"))
            implementation("com.android.tools:sdk-common:32.2.1")
        }
        jsMain.dependencies {
            implementation(libs.wrappers.browser)
            
            implementation(npm("svg2vectordrawable", "2.9.1"))
            implementation(npm("vector-drawable-svg", "1.1.4"))
        }
    }
}
