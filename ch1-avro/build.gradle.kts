plugins {
    java
    id("org.springframework.boot") version "3.5.5"
    id("io.spring.dependency-management") version "1.1.7"
    id("com.github.davidmc24.gradle.plugin.avro") version "1.7.1"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"
description = "ch1-avro"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(24)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    // avro
    implementation("org.apache.avro:avro:1.11.1")
    
    // test dependencies
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

// Kotlin DSL style with Property API
avro {
    isCreateSetters.set(false)
    fieldVisibility.set("PUBLIC")
    stringType.set("String")
    // Other options
    isCreateOptionalGetters.set(false)
    isGettersReturnOptional.set(false)
}