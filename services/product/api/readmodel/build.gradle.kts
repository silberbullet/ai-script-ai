val productDomain: String by project

dependencies {
    api(project(productDomain))
}