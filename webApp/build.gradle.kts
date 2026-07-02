import com.varabyte.kobweb.gradle.application.util.configAsKobwebApplication

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    
    alias(libs.plugins.composeCompiler)
    
    alias(libs.plugins.kobweb.application)
    alias(libs.plugins.kobwebx.markdown)
    //alias(libs.plugins.buildkonfig)
}

group = "my.ym.ma_projects_wizard"
version = "1.0.0"

/*buildkonfig {
    packageName = "my.ym.ma_projects_wizard"
    exposeObjectWithName = "BuildKonfig"
}*/

kobweb {
    app {
        index {
            description.set("Utilities Needed for KMP Development Inshallah.")
        }
    }
}

kotlin {
    configAsKobwebApplication(moduleName = "ma_projects_wizard", includeServer = false)

    sourceSets {
        jsMain.dependencies {
            implementation(projects.shared)
            
            implementation(libs.compose.runtime)
        
            implementation(libs.compose.html.core)
            
            implementation(libs.kobweb.core)
            implementation(libs.kobweb.silk)
            implementation(libs.kobweb.silk.icons.fa)
            implementation(libs.kobwebx.markdown)
            
            implementation(npm("svg2vectordrawable", "2.9.1"))
            implementation(npm("vector-drawable-svg", "1.1.4"))
        }
    }
}
