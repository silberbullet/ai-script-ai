val core = rootDir.resolve("core")
    .walkTopDown()
    .maxDepth(3)
    .filter(File::isDirectory)
    .associateBy(File::getName)

include(
    ":time-util",
    ":jpa-core",
    ":exception-handler-core",
    ":cors-api",
    ":cors-webmvc",
    ":security-jwt-filter",
    ":security-jwt-api",
    ":security-jwt-issuer",
    ":security-jwt-parser",
    ":security-password",
    ":snowflake-id-api",
    ":snowflake-id-hibernate",
    ":client-api",
    ":rest-client",
    ":redis-api",
    ":redis-template",
    ":redis-cache",
    ":upload-image-api",
    ":upload-image-local",
)

project(":time-util").projectDir = core["time-util"]!!
project(":jpa-core").projectDir = core["jpa-core"]!!
project(":exception-handler-core").projectDir = core["exception-handler-core"]!!
project(":cors-webmvc").projectDir = core["cors-webmvc"]!!
project(":cors-api").projectDir = core["cors-api"]!!
project(":security-jwt-filter").projectDir = core["jwt-filter"]!!
project(":security-jwt-api").projectDir = core["jwt-api"]!!
project(":security-jwt-issuer").projectDir = core["jwt-issuer"]!!
project(":security-jwt-parser").projectDir = core["jwt-parser"]!!
project(":security-password").projectDir = core["password"]!!
project(":snowflake-id-api").projectDir = core["snowflake-id-api"]!!
project(":snowflake-id-hibernate").projectDir = core["snowflake-id-hibernate"]!!
project(":client-api").projectDir = core["client-api"]!!
project(":rest-client").projectDir = core["rest-client"]!!
project(":redis-api").projectDir = core["redis-api"]!!
project(":redis-template").projectDir = core["redis-template"]!!
project(":redis-cache").projectDir = core["redis-cache"]!!
project(":upload-image-api").projectDir = core["upload-image-api"]!!
project(":upload-image-local").projectDir = core["upload-image-local"]!!
