import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "3.2.2"
    id("io.spring.dependency-management") version "1.1.4"
    kotlin("jvm") version "1.9.22"
    kotlin("plugin.spring") version "1.9.22"
    kotlin("plugin.noarg") version "1.9.22"
    kotlin("plugin.jpa") version "1.9.22"
    kotlin("kapt") version "1.9.22"
}

noArg {
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.MappedSuperclass")
    annotation("jakarta.persistence.Embeddable")
}

allOpen {
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.MappedSuperclass")
    annotation("jakarta.persistence.Embeddable")
}

group = "com.challenge-team-kotlin"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

val queryDslVersion = "5.0.0"

val kotestVersion = "5.5.5"

val mockkVersion = "1.13.8"

dependencies {
    // DB
    implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    runtimeOnly("com.h2database:h2")
//    runtimeOnly("com.mysql:mysql-connector-j")
//    runtimeOnly("org.postgresql:postgresql")
    // Redis
//    implementation("org.springframework.boot:spring-boot-starter-data-redis")
    // Validation
    implementation("org.springframework.boot:spring-boot-starter-validation")
    // WEB & MVC
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.2.0")
    // AOP
    implementation("org.springframework.boot:spring-boot-starter-aop")
    // NoArgsConstructor
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    // reflection
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    // JWT
    implementation("io.jsonwebtoken:jjwt-api:0.12.3")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.12.3")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.12.3")
    //Security
    implementation("org.springframework.boot:spring-boot-starter-security")
    // QueryDSL
    implementation("com.querydsl:querydsl-jpa:$queryDslVersion:jakarta")
    // coroutine
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.0-RC2")
    // logging
    implementation("io.github.oshai:kotlin-logging-jvm:6.0.2")
    // kapt
    kapt("com.querydsl:querydsl-apt:$queryDslVersion:jakarta")
    // property
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    //oauth
    implementation("org.springframework.boot:spring-boot-starter-oauth2-client")

    // test
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.kotest:kotest-runner-junit5:$kotestVersion")
    testImplementation("io.kotest:kotest-assertions-core:$kotestVersion")
    testImplementation("io.kotest.extensions:kotest-extensions-spring:1.1.3")
    testImplementation("io.mockk:mockk:$mockkVersion")
    testImplementation("com.squareup.okhttp3:mockwebserver:4.12.0")
    testImplementation("com.h2database:h2:2.2.220")
    testImplementation("com.ninja-squad:springmockk:4.0.2")
    testImplementation("org.apache.commons:commons-lang3:3.14.0")

    // .env
    implementation("me.paulschwarz:spring-dotenv:4.0.0")

    // web socket
    implementation("org.springframework.boot:spring-boot-starter-websocket")
    implementation("org.webjars:sockjs-client:1.1.2")
    implementation("org.webjars:stomp-websocket:2.3.3-1")


}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs += "-Xjsr305=strict"
		jvmTarget = "17"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

// plain.jar 생성 방지
tasks.getByName<Jar>("jar") {
    enabled = false
}