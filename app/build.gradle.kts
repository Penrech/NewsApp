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

    navigation()
    hilt()
    implementation(Deps.appCompat)
    implementation(Deps.coreKtx)

    //Projects
    implementation(project(":core"))
    implementation(project(":articles"))
}