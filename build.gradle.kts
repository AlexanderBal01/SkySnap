// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.1.2" apply false
    id("org.jetbrains.kotlin.android") version "1.8.10" apply false
    id("com.google.devtools.ksp") version "1.8.10-1.0.9" apply false
    id("org.jetbrains.dokka") version "1.9.10"
}

tasks.dokkaHtmlMultiModule {
    moduleName.set("SkySnap")
    outputDirectory.set(file("docs"))
    subprojects {
        apply(plugin = "org.jetbrains.dokka")
        tasks.withType<org.jetbrains.dokka.gradle.DokkaTaskPartial>()
            .configureEach {
                val dokkaSourceSet = dokkaSourceSets
                    .create("unit.tests")
                dokkaSourceSet
                    .sourceRoot("src/test/java")
            }
        tasks.withType<org.jetbrains.dokka.gradle.DokkaTaskPartial>()
            .configureEach {
                val dokkaSourceSet = dokkaSourceSets
                    .create("instrumentation.tests")
                dokkaSourceSet
                    .sourceRoot("src/androidTest/java")
            }
    }
}