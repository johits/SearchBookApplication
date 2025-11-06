plugins {
    id("module.android")
    id("kotlin-parcelize")
}
android {
    namespace = "com.jhs.presentation"
}
dependencies {
    implementation(projects.domain)
    implementation(projects.dataResource)


    implementation(libs.lifecycle.runtime)
}
