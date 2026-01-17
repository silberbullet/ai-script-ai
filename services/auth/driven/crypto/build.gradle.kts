val authApplication: String by project

dependencies {
    api(project(authApplication))
    api(project(":security-password"))
}