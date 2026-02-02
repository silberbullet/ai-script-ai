val productApi: String by project
val productApplication: String by project
val productRdbAdapter: String by project
val productWebMvcAdapter: String by project

dependencies {
    api(project(productApi))
    api(project(productApplication))
    api(project(productRdbAdapter))
    api(project(productWebMvcAdapter))
}