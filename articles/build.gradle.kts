plugins {
    id("com.android.library")
    id("dagger.hilt.android.plugin")
    `base-android-config`
}

android {

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
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

    //retrofit
    retrofit()

    // testing
    uiTest()
    unitTest()

    implementation(project(":core"))
}