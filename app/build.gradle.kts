import com.android.build.api.dsl.Packaging


val mapkitApiKey: String = "9db68f6d-8b0c-45d2-80c4-a487ced945ec"

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
}

android {

    packaging{
        resources{
            excludes += "META-INF/LICENSE.md"
            excludes += "META-INF/LICENSE-notice.md"
        }
    }

    namespace = "com.michel.vkmap"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.michel.vkmap"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures{
        buildConfig = true
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            buildConfigField("String", "MAP_API_KEY", mapkitApiKey)
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation("com.vk:android-sdk-core:4.1.0")
    implementation("com.vk:android-sdk-api:4.1.0")

    implementation("com.yandex.android:maps.mobile:4.4.0-lite")

    implementation("io.insert-koin:koin-core:3.5.3")
    implementation("io.insert-koin:koin-android:3.5.3")
    implementation("com.google.firebase:firebase-database-ktx:20.3.0")
    implementation("androidx.activity:activity:1.8.0")
    implementation("com.makeramen:roundedimageview:2.3.0")
    implementation("org.mockito.kotlin:mockito-kotlin:5.3.1")
    implementation("org.junit.jupiter:junit-jupiter:5.11.0-M1")
    implementation("org.mockito:mockito-core:5.11.0")
    testImplementation("io.insert-koin:koin-test:3.5.3")

    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")
    implementation("androidx.activity:activity-compose:1.7.0")

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}