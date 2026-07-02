package my.ym.ma_projects_wizard

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "MA Projects Wizard",
    ) {
        //App()
    }
}