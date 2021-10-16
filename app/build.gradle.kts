plugins {
    id("com.android.application")
    id("dagger.hilt.android.plugin")
    `base-android-config`
}

android {
    defaultConfig {
        versionCode = 1
        versionName = "1.0"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    hilt {
        enableAggregatingTask = true
    }
}

dependencies {
    implementation(Deps.kotlin)

    //arch components
    coroutines()
    navigation()
    hilt()
    lifecycle()
    implementation(Deps.appCompat)
    implementation(Deps.recyclerview)
    implementation(Deps.constraintlayout)
    implementation(Deps.androidMaterial)
    implementation(Deps.coreKtx)

    // testing
    uiTest()
    unitTest()

    //Projects
    implementation(project(":core"))
    implementation(project(":articles"))
}