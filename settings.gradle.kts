rootProject.name = "SearchBookApplication"
include(":app")
include(":domain")
include(":data")
include(":remote")
include(":local")
include(":presentation")
include(":data-resource")
include(":ui")


pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")



include(":local")
