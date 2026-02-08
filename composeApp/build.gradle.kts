import com.android.aaptcompiler.parseReference
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    id("org.jetbrains.kotlin.plugin.serialization") version "2.1.20"
    id("org.jetbrains.kotlin.native.cocoapods")
    id("com.google.gms.google-services")
}

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }

    listOf(
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }

    cocoapods {
        summary = "Jogo Forca shared module"
        homepage = "https://example.com"
        ios.deploymentTarget = "14.1"
        version = "0.0.1"
        pod("FirebaseDatabase", version = "10.29.0")
    }

    val koinVersion = "4.1.0"

    sourceSets {
        androidMain.dependencies {
            implementation(libs.compose.uiToolingPreview)
            implementation(libs.androidx.activity.compose)
            implementation("androidx.compose.material3:material3")
            implementation("androidx.core:core-splashscreen:1.0.1")
            implementation("io.insert-koin:koin-android:3.5.3")
            implementation("com.google.firebase:firebase-config:22.1.1")
            implementation("com.google.firebase:firebase-database:21.0.0")
            implementation("com.google.accompanist:accompanist-permissions:0.37.3")
            implementation(libs.androidx.datastore.preferences)
        }
        commonMain.dependencies {
            implementation(libs.compose.runtime)
            implementation(libs.compose.foundation)
            implementation(libs.compose.material3)
            implementation(libs.compose.ui)
            implementation(libs.compose.components.resources)
            implementation(libs.compose.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodelCompose)
            implementation(libs.androidx.lifecycle.runtimeCompose)

            implementation("io.insert-koin:koin-core:${koinVersion}")
            implementation("io.insert-koin:koin-compose:${koinVersion}")
            implementation("io.insert-koin:koin-compose-viewmodel:${koinVersion}")

            implementation("org.jetbrains.androidx.navigation:navigation-compose:2.9.0-beta03")
            implementation("org.jetbrains.compose.material:material-icons-extended:1.7.3")
            implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")
            implementation(libs.gitlive.firebase.app)
            implementation(libs.gitlive.firebase.database)

        }
        iosMain.dependencies {

        }

    }
}

compose.resources {
    nameOfResClass = "Res"
    publicResClass = false
    packageOfResClass = "com.game.forca.game_forca.resources"
    generateResClass = auto
}

android {
    namespace = "com.game.forca.game_forca"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    buildFeatures {
        buildConfig = true
    }

    defaultConfig {
        applicationId = "com.game.forca.flipsofts"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    implementation(libs.firebase.config.ktx)
    implementation(libs.androidx.exifinterface)
    debugImplementation(libs.compose.uiTooling)
}



 //3 LOGAR VERIFICAR SE EH MESMA SENHA,
 //4 Cada ponto atualizar o score local e do firebase
 //5 ranking pega dos 5 que tema mais pontos
 // 6 depois de criar o objeto com o code de push sempre atualizar o objeto