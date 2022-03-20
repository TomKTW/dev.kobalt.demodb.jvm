plugins {
    kotlin("jvm") version "1.6.10"
    id("com.github.johnrengelman.shadow") version "6.0.0"
}

group = "dev.kobalt"
version = "0000.00.00.00.00.00.000"

repositories {
    mavenCentral()
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

fun kobalt(module: String, version: String) = "dev.kobalt:$module:$version"
fun ktor(module: String, version: String) = "io.ktor:ktor-$module:$version"
fun exposed(module: String, version: String) = "org.jetbrains.exposed:exposed-$module:$version"
fun general(module: String, version: String) = "$module:$version"
fun kotlinx(module: String, version: String) = "org.jetbrains.kotlinx:kotlinx-$module:$version"
fun kotlinw(module: String, version: String) = "org.jetbrains.kotlin-wrappers:kotlin-$module:$version"

fun DependencyHandler.ormFramework() {
    implementation(exposed("core", "0.34.1"))
    implementation(exposed("jdbc", "0.34.1"))
    implementation(exposed("java-time", "0.34.1"))
}

fun DependencyHandler.database() {
    implementation(general("com.h2database:h2", "1.4.200"))
}

fun DependencyHandler.commandLineInterface() {
    implementation("org.jetbrains.kotlinx:kotlinx-cli:0.3.3")
}

fun DependencyHandler.standardLibrary() {
    implementation(kotlin("stdlib", "1.6.10"))
}

fun DependencyHandler.logger() {
    implementation(general("org.slf4j:slf4j-simple", "1.7.36"))
}

dependencies {
    standardLibrary()
    commandLineInterface()
    ormFramework()
    database()
    logger()
}

tasks {
    named<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar>("shadowJar") {
        archiveFileName.set("demodb.jar")
        mergeServiceFiles()
        // minimize()
        manifest {
            attributes("Main-Class" to "dev.kobalt.demodb.jvm.MainKt")
        }
    }
}