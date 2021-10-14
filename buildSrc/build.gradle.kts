plugins {
    `java-gradle-plugin`
    `kotlin-dsl`
    `kotlin-dsl-precompiled-script-plugins`
}

repositories {
    google()
    mavenCentral()
}

dependencies {
    compileOnly(gradleApi())
    implementation("com.android.tools.build:gradle:7.0.2")
    implementation(kotlin("gradle-plugin", "1.5.31"))
    implementation(kotlin("android-extensions"))
}
