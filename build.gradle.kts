import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "2.0.0"
}

group = "codes.bed"
version = "0.1.0-DEV"

repositories {
    mavenCentral()
    maven("https://jitpack.io")
    maven("https://mvn.utf.lol/releases") { name = "utfRepo" }
    maven("https://repo.codemc.io/repository/maven-public/")
}

dependencies {
    // Kotlin
    compileOnly(kotlin("stdlib"))

    // Minestom Core
    compileOnly("net.minestom:minestom-snapshots:39d445482f")
}

tasks {
    withType<JavaCompile> {
        options.release.set(22)
        options.encoding = "UTF-8"
        options.compilerArgs.addAll(
            listOf(
                "-parameters"
            )
        )
    }

    withType<KotlinCompile> {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_22)
            freeCompilerArgs.addAll(
                listOf(
                    "-Xcontext-receivers"
                )
            )
        }
    }
}
