import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
// Get API key from local.properties
val apiKey: String = gradleLocalProperties(rootDir).getProperty("GOOGLE_API_KEY")

plugins {
    id("com.android.application")
    id("com.google.gms.google-services")
}

android {
    namespace = "edu.northeastern.groupproject_outandabout"
    compileSdk = 34

    defaultConfig {
        applicationId = "edu.northeastern.groupproject_outandabout"
        minSdk = 27
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            signingConfig = signingConfigs.getByName("debug")

            buildConfigField("String", "GOOGLE_API_KEY", apiKey)
        }

        debug {
           buildConfigField("String", "GOOGLE_API_KEY", apiKey)
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildFeatures {
        viewBinding = true
        buildConfig = true
    }
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation ("androidx.cardview:cardview:1.0.0")


    // Firebase
    implementation("com.google.firebase:firebase-auth:22.3.0")
    implementation ("com.firebaseui:firebase-ui-auth:7.2.0")

    // Import the Firebase BoM
    implementation(platform("com.google.firebase:firebase-bom:32.6.0"))


    // TODO: Add the dependencies for Firebase products we want to use
    // do we want google login?
    implementation("com.google.firebase:firebase-analytics")


    implementation("com.google.android.material:material:1.5.0")

    implementation("androidx.annotation:annotation:1.6.0")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.1")
    implementation("com.google.firebase:firebase-database:20.3.0")

    implementation("androidx.recyclerview:recyclerview:1.3.2")
    implementation ("androidx.recyclerview:recyclerview:1.2.1")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

}