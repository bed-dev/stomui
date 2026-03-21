import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "2.2.20"
    `maven-publish`
}

val minestomVersion = "2026.03.03-1.21.11"
val minecraftVersion = minestomVersion.substringAfter('-')
val libraryVersion = "0.1.1"

group = "codes.bed"
version = "${libraryVersion}-${minecraftVersion}-DEV"

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(25))
    withSourcesJar()
    withJavadocJar()
}

publishing {
    publications {
        create<MavenPublication>("gpr") {
            from(components["java"])
        }
    }
    repositories {
        mavenLocal()
    }
}

repositories {
    mavenCentral()
    maven("https://jitpack.io")
    maven("https://mvn.utf.lol/releases") { name = "utfRepo" }
    maven(url = "https://central.sonatype.com/repository/maven-snapshots/") {
        content {
            includeModule("net.minestom", "minestom")
            includeModule("net.minestom", "testing")
        }
    }
    maven("https://repo.codemc.io/repository/maven-public/")
}

dependencies {
    // Kotlin
    implementation(kotlin("stdlib"))

    // Minestom Core
    compileOnly("net.minestom:minestom:${minestomVersion}")
    runtimeOnly("net.minestom:minestom:${minestomVersion}")

    // Logging backend for local test server runs
    runtimeOnly("org.slf4j:slf4j-simple:2.0.17")
}

tasks {
    withType<JavaCompile>().configureEach {
        options.release.set(25)
        options.encoding = "UTF-8"
        options.compilerArgs.addAll(
            listOf(
                "-parameters"
            )
        )
    }

    withType<KotlinCompile>().configureEach {
        compilerOptions {
            // Kotlin currently emits up to JVM 24 bytecode; Java sources still target Java 25.
            jvmTarget.set(JvmTarget.fromTarget("24"))
            freeCompilerArgs.addAll(
                listOf(
                    "-Xcontext-receivers"
                )
            )
        }
    }

    register<JavaExec>("runTestServer") {
        group = "application"
        description = "Runs the local Minestom test server entry point"
        classpath = sourceSets.main.get().runtimeClasspath
        mainClass.set("codes.bed.minestom.menus.testing.TestServerKt")

        // Keep console logs compact and readable for local debugging.
        systemProperty("org.slf4j.simpleLogger.showDateTime", "true")
        systemProperty("org.slf4j.simpleLogger.dateTimeFormat", "HH:mm:ss")
        systemProperty("org.slf4j.simpleLogger.showThreadName", "false")
        systemProperty("org.slf4j.simpleLogger.defaultLogLevel", "info")
    }
}
