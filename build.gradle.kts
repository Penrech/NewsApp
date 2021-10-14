buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath(Deps.androidGradle)
        classpath(Deps.hiltAndroidPlugin)
        classpath(Deps.kotlinGradle)
        classpath(Deps.navigationSafeArgs)
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

task<Delete>("clean") {
    delete(rootProject.buildDir)
}