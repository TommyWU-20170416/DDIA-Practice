plugins {
    java
    id("org.springframework.boot") version "3.5.5" apply false
    id("io.spring.dependency-management") version "1.1.7" apply false
}

group = "com.example"
version = "0.0.1-SNAPSHOT"
description = "DDIA-Practice"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

allprojects {
    repositories {
        mavenCentral()
    }
}

val lombokVersion = "1.18.42"

subprojects {
    apply(plugin = "java")
    apply(plugin = "org.springframework.boot")
    apply(plugin = "io.spring.dependency-management")

    dependencies {
        implementation("org.springframework.boot:spring-boot-starter")
        implementation("org.springframework.boot:spring-boot-starter-web")

        testImplementation("org.springframework.boot:spring-boot-starter-test")
        testRuntimeOnly("org.junit.platform:junit-platform-launcher")

        compileOnly("org.projectlombok:lombok:${lombokVersion}")
        annotationProcessor("org.projectlombok:lombok:${lombokVersion}")
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }
}