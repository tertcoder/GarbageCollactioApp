plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.garbagecollectionapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.garbagecollectionapp"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    // Retrofit for API calls
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.3")

    // Glide for image loading
    implementation("com.github.bumptech.glide:glide:4.12.0")
    annotationProcessor("com.github.bumptech.glide:compiler:4.12.0")

    // Material datetime picker
    implementation("com.wdullaer:materialdatetimepicker:4.2.3")

    // Circular image view
    implementation("de.hdodenhof:circleimageview:3.1.0")

    // Gson
    implementation("com.google.code.gson:gson:2.8.9")

    // Shimmer effect
    implementation("com.facebook.shimmer:shimmer:0.5.0")

    implementation("com.android.volley:volley:1.2.1")
}