import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.3.3.RELEASE"
    id("io.spring.dependency-management") version "1.0.10.RELEASE"
    kotlin("jvm") version "1.3.72"
    kotlin("plugin.spring") version "1.3.72"
}

group = "org.wolfpack.assessment"
version = "1.0-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
    mavenCentral()
    maven { url = uri("https://repo.spring.io/milestone") }
    maven { url = uri("https://repo.spring.io/snapshot") }
    maven { url = uri("https://plugins.gradle.org/m2/") }
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("com.thedeanda", "lorem", "2.1")
    implementation("org.jetbrains.kotlin", "kotlin-reflect")
    implementation("com.fasterxml.jackson.module", "jackson-module-kotlin")
    implementation("org.springframework.boot", "spring-boot-starter-test")
    implementation("org.springframework.boot", "spring-boot-starter-web")
    implementation("org.springframework.boot", "spring-boot-starter-actuator")
    implementation("org.springframework.boot", "spring-boot-starter-validation")
    implementation("org.springframework.boot", "spring-boot-starter-data-mongodb")
    implementation("io.springfox", "springfox-boot-starter", "3.0.0")
    testImplementation("com.ninja-squad:springmockk:2.0.3")
}

allOpen {
    annotation("javax.persistence.Entity")
    annotation("javax.persistence.Embeddable")
    annotation("javax.persistence.MappedSuperclass")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}