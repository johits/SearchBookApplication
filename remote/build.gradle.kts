import java.util.Properties

plugins {
    id("module.android")
}

android {
    namespace = "com.jhs.remote"
    buildFeatures.apply {
        buildConfig = true
    }
    defaultConfig {
        val localProps = Properties().apply {
            val file = rootProject.file("local.properties")
            if (file.exists()) {
                load(file.inputStream())
            }
        }

        val kakaoApiKey = localProps.getProperty("KAKAO_API_KEY") as? String ?: ""
        buildConfigField("String", "KAKAO_API_KEY", "\"$kakaoApiKey\"")
    }
}

dependencies {
    implementation(projects.data)

    implementation(libs.okhttp)
    implementation(libs.retrofit.gson)

    implementation(libs.retrofit)
    implementation(libs.okhttp.logging)
}
