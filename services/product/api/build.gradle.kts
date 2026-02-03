val productDomain: String by project
val productException: String by project
val productReadModel: String by project

dependencies {
    api(project(productDomain))
    api(project(productException))
    api(project(productReadModel))
}