plugins {
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    id("java")
    id("com.github.davidmc24.gradle.plugin.avro") version "1.7.1"
}

description = "ch1-avro"

dependencies {
    implementation("org.apache.avro:avro:1.11.1")
}

avro {
    isCreateSetters.set(false)
    fieldVisibility.set("PUBLIC")
    stringType.set("String")
    isCreateOptionalGetters.set(false)
    isGettersReturnOptional.set(false)
}
