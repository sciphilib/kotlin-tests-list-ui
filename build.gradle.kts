plugins {
    kotlin("jvm") version "1.9.22"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.jetbrains.kotlin:kotlin-test")
    implementation ("org.junit.jupiter:junit-jupiter-api:5.7.0")
    testImplementation ("org.junit.jupiter:junit-jupiter-engine:5.7.0")
    implementation("com.fasterxml.jackson.core:jackson-core:2.12.4")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:1.2.2")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.12.3")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}