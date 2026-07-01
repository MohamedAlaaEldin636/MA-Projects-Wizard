import com.varabyte.kobweb.gradle.application.util.configAsKobwebApplication
import com.codingfeline.buildkonfig.compiler.FieldSpec
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.buildkonfig)
    alias(libs.plugins.kobweb.application)
    alias(libs.plugins.kobwebx.markdown)
}

group = "my.ym.ma_projects_wizard"
version = "1.0.0"

val isKobwebTaskActive = gradle.startParameter.taskNames.any { taskName ->
    taskName.contains("kobweb", ignoreCase = true)
}

buildkonfig {
    packageName = "my.ym.ma_projects_wizard"
    exposeObjectWithName = "BuildKonfig"
    
    defaultConfigs {
        buildConfigField(
            FieldSpec.Type.BOOLEAN,
            "IS_KOBWEB_ACTIVE",
            isKobwebTaskActive.toString()
        )
    }
}

kobweb {
    app {
        index {
            //title.set("MA Projects Wizard")
            description.set("Powered by Kobweb")
        }
    }
}

kotlin {
    configAsKobwebApplication("ma_projects_wizard"/*, includeServer = true*/)
    
    js {
        browser()
        binaries.executable()
        /*this.browser {
            commonWebpackConfig {
                this.outputFileName = "main.js"
            }
        }*/
    }
    
    /*@OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        browser()
        binaries.executable()
    }*/

    sourceSets {
        commonMain.dependencies {
            implementation(projects.shared)

            implementation(libs.compose.ui)
        }
        jsMain.dependencies {
            implementation(libs.compose.runtime)
        
            implementation(libs.compose.html.core)
            
            implementation(libs.kobweb.core)
            implementation(libs.kobweb.silk)
            implementation(libs.kobweb.silk.icons.fa)
            implementation(libs.kobwebx.markdown)
            
            implementation(npm("svg2vectordrawable", "2.9.1"))
            implementation(npm("vector-drawable-svg", "1.1.4"))
            //implementation(npm("vd2svg", "0.3.3"))
            //implementation(npm("stream-browserify", "3.0.0"))
        }
        
        /*jvmMain.dependencies {
            compileOnly(libs.kobweb.api) // Provided by Kobweb backend at runtime
        }*/
    }
}

/*afterEvaluate {
    rootProject.extensions.configure<org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootExtension> {
        versions.webpackDevServer.version = "4.0.0"
        versions.webpackCli.version = "4.10.0"
    }
}*/
