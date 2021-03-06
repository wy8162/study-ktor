val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project
val kotlinxHtmlVersion: String by project

plugins {
    application
    kotlin("jvm") version "1.6.10"
    id("org.jetbrains.kotlin.plugin.serialization") version "1.6.10"
}

group = "w.y"
version = "0.0.1"
application {
    mainClass.set("w.y.ApplicationKt")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.ktor:ktor-server-core:$ktor_version")
    implementation("io.ktor:ktor-serialization:$ktor_version")
    implementation("io.ktor:ktor-server-netty:$ktor_version")

    implementation("io.ktor:ktor-client-core:$ktor_version")
    implementation("io.ktor:ktor-client-cio:$ktor_version")
    implementation("io.ktor:ktor-client-logging:$ktor_version")
    implementation("io.ktor:ktor-client-serialization:$ktor_version")
    implementation("io.ktor:ktor-client-json-jvm:$ktor_version") // Provides JsonFeature

    implementation("io.ktor:ktor-auth:$ktor_version")

    implementation("io.ktor:ktor-html-builder:$ktor_version")
    implementation("io.ktor:ktor-freemarker:$ktor_version")
    implementation("org.jetbrains.kotlinx:kotlinx-html-jvm:$kotlinxHtmlVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-html:$kotlinxHtmlVersion")
    implementation("ch.qos.logback:logback-classic:$logback_version")
    implementation("io.ktor:ktor-auth:1.6.7")
    implementation("io.ktor:ktor-auth-jwt:1.6.7")
    implementation("io.ktor:ktor-locations:1.6.7")
    implementation("io.ktor:ktor-client-apache:1.6.7")

    // Tes framework
    testImplementation("io.ktor:ktor-server-tests:$ktor_version")
    testImplementation("io.ktor:ktor-client-mock:$ktor_version")
    testImplementation("io.ktor:ktor-client-mock-jvm:$ktor_version")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")
}