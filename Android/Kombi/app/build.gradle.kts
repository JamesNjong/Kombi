plugins {
    id("com.android.application")
}

android {
    namespace = "xyz.thisisjames.boulevard.android.kombi"
    compileSdk = 34

    defaultConfig {
        applicationId = "xyz.thisisjames.boulevard.android.kombi"
        minSdk = 26
        targetSdk = 33
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.2")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")
    implementation("androidx.navigation:navigation-fragment:2.7.2")
    implementation("androidx.navigation:navigation-ui:2.7.2")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    //splashscreen api
    implementation("androidx.core:core-splashscreen:1.0.1")


    // cicular indicator for scores
    implementation("com.github.antonKozyriatskyi:CircularProgressIndicator:1.3.0")


    // better management of activity transitions
    implementation("com.github.AtifSayings:Animatoo:1.0.1")

    implementation(platform("org.jetbrains.kotlin:kotlin-bom:1.8.0"))


    //tab indicator
    implementation("com.tbuonomo:dotsindicator:5.0")


    //country code picker
    implementation("com.github.Ajinkrishnak:CountryCodePicker:1.0.2")
}

