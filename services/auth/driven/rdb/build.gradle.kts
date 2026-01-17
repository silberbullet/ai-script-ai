val authApplication: String by project
val authApi: String by project

dependencies {
    api(project(authApi))
    api(project(authApplication))
    api(project(":jpa-core"))
    api(project(":snowflake-id-hibernate"))

    // flyway
    api("org.flywaydb:flyway-core")
    api("org.flywaydb:flyway-database-postgresql")

    // mapstruct
    implementation("org.mapstruct:mapstruct:1.6.3")
    annotationProcessor("org.mapstruct:mapstruct-processor:1.6.3")
    annotationProcessor("org.projectlombok:lombok-mapstruct-binding:0.2.0")
}