val authApi: String by project
val authApplication: String by project
val authRdbAdapter: String by project
val authRedisAdapter: String by project
val authJwtAdapter: String by project
val authCryptoAdapter: String by project
val authWebMvcAdapter: String by project

dependencies {
    api(project(authApi))
    api(project(authApplication))
    api(project(authRdbAdapter))
    api(project(authRedisAdapter))
    api(project(authJwtAdapter))
    api(project(authCryptoAdapter))
    api(project(authRedisAdapter))
    api(project(authWebMvcAdapter))
}