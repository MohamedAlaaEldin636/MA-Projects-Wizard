package my.ym.ma_projects_wizard

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform