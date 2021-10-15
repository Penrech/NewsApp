import com.android.build.gradle.BaseExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

class AndroidBasePlugin: Plugin<Project> {

    override fun apply(target: Project) {

        target.plugins.apply("kotlin-android")
        target.plugins.apply("kotlin-kapt")
        target.plugins.apply("androidx.navigation.safeargs.kotlin")

        (target.extensions.getByName("android") as? BaseExtension)?.apply {
            compileSdkVersion(DefaultConfig.compileSdk)
            defaultConfig {
                targetSdk = DefaultConfig.targetSdk
                minSdk = DefaultConfig.minSdk
                vectorDrawables.useSupportLibrary = true

                testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
            }
            compileOptions {
                sourceCompatibility = JavaVersion.VERSION_11
                targetCompatibility = JavaVersion.VERSION_11
            }
            target.tasks.withType(KotlinCompile::class.java).configureEach {
                kotlinOptions {
                    jvmTarget = JavaVersion.VERSION_11.toString()
                }
            }
        }
    }
}