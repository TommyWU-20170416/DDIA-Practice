plugins {
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    id("java")
    id("com.github.davidmc24.gradle.plugin.avro") version "1.7.1"
}

description = "desc: ch1-avro"

dependencies {
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-avro:2.17.2")
    implementation("org.apache.avro:avro:1.11.1")
}

tasks.withType<JavaCompile> {
    outputs.upToDateWhen { false }
}

avro {
    isCreateSetters.set(true)
    isCreateOptionalGetters.set(false)
    isGettersReturnOptional.set(false)
    isOptionalGettersForNullableFieldsOnly.set(false)
    fieldVisibility.set("PUBLIC")
    outputCharacterEncoding.set("UTF-8")
    stringType.set("String")
    templateDirectory.set(null as String?)
    isEnableDecimalLogicalType.set(true)
}