import com.varabyte.kobweb.gradle.application.util.configAsKobwebApplication

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    
    alias(libs.plugins.composeCompiler)
    
    alias(libs.plugins.kobweb.application)
    alias(libs.plugins.kobwebx.markdown)
}

group = "my.ym.ma_projects_wizard"
version = "1.0.0"

kobweb {
    app {
        index {
            description.set("Utilities Needed for KMP Development Inshallah.")
        }
        
        //globals.put("version", "1.0.0")
        // can then get it via -> com.varabyte.kobweb.core.AppGlobals["version"] in code Inshallah.
    }
    
    markdown {
        imports.add("my.ym.ma_projects_wizard.SiteGlobals")
    }
}

kotlin {
    configAsKobwebApplication(moduleName = "ma_projects_wizard", includeServer = false)
    
    compilerOptions {
        freeCompilerArgs.add("-Xcontext-parameters")
    }

    sourceSets {
        jsMain.dependencies {
            implementation(projects.shared)
            
            implementation(libs.compose.runtime)
            implementation(libs.androidx.lifecycle.viewmodel)
            
            implementation(libs.compose.html.core)
            
            implementation(libs.kobweb.core)
            implementation(libs.kobweb.silk)
            //implementation(libs.kobweb.silk.icons.fa)
            implementation(libs.kobwebx.markdown)
        }
    }
}
