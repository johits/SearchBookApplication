import build.ApplicationId
import build.Config
import build.Releases

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
//    id("com.vanniktech.dependency.graph.generator") version "0.8.0" 다이어그램 추출 용도
}


android {
    namespace = "com.jhs.searchbookapplication"
    compileSdk = Config.compileSdk

    defaultConfig {
        applicationId = ApplicationId.id
        compileSdk = Config.compileSdk
        minSdk = Config.minSdk
        targetSdk = Config.targetSdk

        versionCode = Releases.versionCode
        versionName = Releases.versionName
        multiDexEnabled = true
    }


    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
    }





    buildTypes {
        named("debug") {
            isMinifyEnabled = true
            proguardFiles("proguard-rules.pro")
        }
        named("release") {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }



    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

}


dependencies {
    implementation(projects.presentation)
    implementation(projects.domain)
    implementation(projects.data)
    implementation(projects.remote)
    implementation(projects.local)
    implementation(projects.ui)

    implementation(platform(libs.compose.bom))
    implementation(libs.activity.compose)

    implementation(libs.multidex)
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
    implementation(libs.coroutines.android)
}
