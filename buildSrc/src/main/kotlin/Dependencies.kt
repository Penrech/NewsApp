import org.gradle.api.artifacts.dsl.DependencyHandler

object Deps {
    const val androidGradle = "com.android.tools.build:gradle:${Versions.androidGradleVersion}"
    const val kotlinGradle = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlinVersion}"
    const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlinVersion}"

    const val appCompat = "androidx.appcompat:appcompat:${Versions.appCompatVersion}"
    const val coroutinesAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutinesVersion}"
    const val coroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutinesVersion}"
    const val coroutinesAdapter =
        "com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:${Versions.coroutinesAdapterVersion}"
    const val androidMaterial =
        "com.google.android.material:material:${Versions.androidMaterialVersion}"
    const val constraintlayout =
        "androidx.constraintlayout:constraintlayout:${Versions.constraintLayoutVersion}"
    const val recyclerview = "androidx.recyclerview:recyclerview:${Versions.recyclerViewVersion}"

    const val coreKtx = "androidx.core:core-ktx:${Versions.coreVersion}"
    const val viewmodel =
        "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycleVersion}"
    const val viewmodelSavedState = "androidx.lifecycle:lifecycle-viewmodel-savedstate:${Versions.lifecycleVersion}"
    const val androidxLifecycleProcess = "androidx.lifecycle:lifecycle-process:${Versions.lifecycleVersion}"
    const val lifecycleLiveData = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycleVersion}"
    const val lifecycleCompiler =
        "androidx.lifecycle:lifecycle-common-java8:${Versions.lifecycleVersion}"
    const val lifecycleRuntime = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycleVersion}"
    const val navigationFragment = "androidx.navigation:navigation-fragment-ktx:${Versions.navigationVersion}"
    const val navigationUi = "androidx.navigation:navigation-ui-ktx:${Versions.navigationVersion}"
    const val navigationSafeArgs = "androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.navigationVersion}"

    //retrofit
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit2Version}"
    const val converterGson = "com.squareup.retrofit2:converter-gson:${Versions.retrofit2Version}"

    //parser
    const val gson = "com.google.code.gson:gson:${Versions.gsonVersion}"

    //http
    const val okhttp = "com.squareup.okhttp3:okhttp:${Versions.okhttpVersion}"
    const val loggingInterceptor =
        "com.squareup.okhttp3:logging-interceptor:${Versions.okhttpVersion}"

    //dependency injection
    const val dagger = "com.google.dagger:dagger:${Versions.daggerVersion}"
    const val daggerCompiler = "com.google.dagger:dagger-compiler:${Versions.daggerVersion}"
    const val daggerProcessor =
        "com.google.dagger:dagger-android-processor:${Versions.daggerVersion}"
    const val hilt = "com.google.dagger:hilt-android:${Versions.hiltVersion}"
    const val hiltCompiler = "com.google.dagger:hilt-android-compiler:${Versions.hiltVersion}"
    const val hiltAndroidPlugin = "com.google.dagger:hilt-android-gradle-plugin:${Versions.hiltVersion}"
    const val hiltXCompiler = "androidx.hilt:hilt-compiler:${Versions.hiltXVersion}"
}

object TestDeps {
    const val junit = "junit:junit:${Versions.junitVersion}"

    const val kotlinTestJunit = "org.jetbrains.kotlin:kotlin-test-junit:${Versions.kotlinVersion}"
    const val mockk = "io.mockk:mockk:${Versions.mockkVersion}"
    const val androidCoreTesting =
        "androidx.arch.core:core-testing:${Versions.androidCoreTestingVersion}"
    const val coroutinesTest =
        "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutinesVersion}"
    const val androidxJunit = "androidx.test.ext:junit:${Versions.androidxJunitVersion}"
    const val espresso = "androidx.test.espresso:espresso-core:${Versions.espressoVersion}"
    const val androidxTestRunner = "androidx.test:runner:${Versions.androidxTestRunnerVersion}"
    const val androidxTestRules = "androidx.test:rules:${Versions.androidxTestRulesVersion}"
    const val robolectric = "org.robolectric:robolectric:${Versions.robolectricVersion}"
}

fun DependencyHandler.coroutines() {
    add("implementation", Deps.coroutinesAndroid)
    add("implementation", Deps.coroutinesCore)
}

fun DependencyHandler.retrofit() {
    add("implementation", Deps.retrofit)
    add("implementation", Deps.gson)
    add("implementation", Deps.converterGson)
    add("implementation", Deps.okhttp)
    add("implementation", Deps.coroutinesAdapter)
    add("implementation", Deps.loggingInterceptor)
}

fun DependencyHandler.dagger() {
    add("kapt", Deps.daggerCompiler)
    add("implementation", Deps.dagger)
    add("kapt", Deps.daggerProcessor)
}

fun DependencyHandler.hilt() {
    add("kapt", Deps.hiltCompiler)
    add("kapt", Deps.hiltXCompiler)
    add("implementation", Deps.hilt)
}

fun DependencyHandler.navigation() {
    add("implementation", Deps.navigationFragment)
    add("implementation", Deps.navigationUi)
}

fun DependencyHandler.lifecycle() {
    add("implementation", Deps.lifecycleLiveData)
    add("implementation", Deps.viewmodel)
    add("implementation", Deps.androidxLifecycleProcess)
    add("implementation", Deps.viewmodelSavedState)
    add("implementation", Deps.lifecycleCompiler)
    add("implementation", Deps.lifecycleRuntime)
}

fun DependencyHandler.uiTest() {
    add("androidTestImplementation", TestDeps.androidxJunit)
    add("androidTestImplementation", TestDeps.espresso)
    add("androidTestImplementation", TestDeps.androidxTestRunner)
    add("androidTestImplementation", TestDeps.androidxTestRules)
}

fun DependencyHandler.unitTest() {
    add("testImplementation", TestDeps.junit)
    add("testImplementation", TestDeps.mockk)
    add("testImplementation", TestDeps.kotlinTestJunit)
    add("testImplementation", TestDeps.androidCoreTesting)
    add("testImplementation", TestDeps.coroutinesTest)
}