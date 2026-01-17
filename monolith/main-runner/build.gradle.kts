import org.springframework.boot.gradle.tasks.bundling.BootJar

val auth: String by project

version = "0.0.1-SNAPSHOT"

dependencies {
    // service
    api(project(auth))

    // core
    implementation(project(":exception-handler-core"))
    implementation(project(":jpa-core"))
    implementation(project(":cors-webmvc"))
    implementation(project(":rest-client"))
    implementation(project(":security-jwt-filter"))
    // implementation(project((":upload-image-local")))

    // webmvc
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-actuator")

    // db
    runtimeOnly("org.postgresql:postgresql:42.7.4")

    // test
    testImplementation("com.h2database:h2")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("com.fasterxml.jackson.core:jackson-databind")
    testImplementation("com.fasterxml.jackson.module:jackson-module-kotlin")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.withType<BootJar>{
    enabled = true
}

tasks.withType<Jar>{
    enabled = true
}