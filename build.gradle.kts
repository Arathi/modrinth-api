import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.8.21"
}

group = "com.undsf.modrinth"
version = "0.1.0-SNAPSHOT"

repositories {
    maven("https://maven.aliyun.com/repository/public")
    mavenCentral()
}

dependencies {
    implementation("org.slf4j:slf4j-api:2.0.7")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.15.2")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.15.2")
    testImplementation("ch.qos.logback:logback-classic:1.4.9")
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "17"
}
