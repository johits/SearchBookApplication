import gradle.kotlin.dsl.accessors._410e14f66005fdc3b16859e745da1f68.implementation

plugins {
    id("kotlin")
    id("kotlin-kapt")
}

val libs: VersionCatalog = extensions.getByType<VersionCatalogsExtension>().named("libs")


dependencies {
    implementation(libs.getLibrary("hilt.core"))
    kapt(libs.getLibrary("hilt.compiler"))
    implementation(libs.getLibrary("kotlinx-metadata-jvm"))
    implementation(libs.getLibrary("coroutines.core"))

    testImplementation(libs.getLibrary("junit"))
    testImplementation(libs.getLibrary("mockk-core"))
    testImplementation(libs.getLibrary("coroutines-test"))
}
