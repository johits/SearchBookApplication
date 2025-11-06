plugins {
    id("module.android")
}
android {
    namespace = "com.jhs.local"
}
dependencies {
    implementation(projects.data)

    implementation(libs.gson)
    implementation(libs.room.ktx)
    implementation(libs.androidx.datastore)
    kapt(libs.room.compiler)
}
