val productApi: String by project

dependencies {
    api(project(productApi))
}