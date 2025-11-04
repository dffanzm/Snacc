plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.example.snacc"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.snacc"
        minSdk = 29
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        viewBinding = true
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    defaultConfig {
        applicationId = "com.example.snacc"
        minSdk = 29
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        // ⬇️ INI WAJIB BIAR ICON VECTOR BISA KETINT
        vectorDrawables.useSupportLibrary = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    // ⚡ Core Android
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)

    // 🪄 Animasi & UI
    implementation("com.airbnb.android:lottie:6.3.0") // buat animasi Lottie nanti
    implementation("androidx.dynamicanimation:dynamicanimation:1.1.0-alpha03") // smooth animation

    // 🌫️ Blur + efek visual (RenderScript support lama)
    implementation("jp.wasabeef:blurry:4.0.1")

    // 🔤 Fonts & UI tambahan (optional tapi cakep)
    implementation("androidx.core:core-splashscreen:1.0.1")

    // 🧪 Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
