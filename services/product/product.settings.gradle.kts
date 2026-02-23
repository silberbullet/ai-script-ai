val product: String by settings
val productApi: String by settings
val productDomain: String by settings
val productException: String by settings
val productReadModel: String by settings
val productApplication: String by settings
val productRdbAdapter: String by settings
val productWebMvcAdapter: String by settings
val productBatchAdapter: String by settings

fun getDirectories(vararg names: String): (String) -> File {
    var dir = rootDir
    for (name in names) {
        dir = dir.resolve(name)
    }
    return { targetName ->
        val directory = dir.walkTopDown().maxDepth(3)
            .filter(File::isDirectory)
            .associateBy { it.name }
        directory[targetName] ?: throw Error("그런 폴더가 없습니다: $targetName")
    }
}

val productDirectory = getDirectories("services", "product")

// SERVICE/product
include(
    product,
    productApi,
    productDomain,
    productException,
    productReadModel,
    productApplication,
    productRdbAdapter,
    productWebMvcAdapter,
    productBatchAdapter,
)

project(product).projectDir = productDirectory("product")
project(productApi).projectDir = productDirectory("api")
project(productDomain).projectDir = productDirectory("domain")
project(productException).projectDir = productDirectory("exception")
project(productReadModel).projectDir = productDirectory("readmodel")
project(productApplication).projectDir = productDirectory("application")
project(productRdbAdapter).projectDir = productDirectory("rdb")
project(productWebMvcAdapter).projectDir = productDirectory("web-mvc")
project(productBatchAdapter).projectDir = productDirectory("batch")