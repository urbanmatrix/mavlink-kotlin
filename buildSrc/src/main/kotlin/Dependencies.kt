object Deps {
    const val orgJson = "org.json:json:20220320"
    const val jacksonDataFormatXml = "com.fasterxml.jackson.dataformat:jackson-dataformat-xml:2.9.0"
    const val jacksonModuleKotlin = "com.fasterxml.jackson.module:jackson-module-kotlin:2.13.2"
    const val kotlinPoet = "com.squareup:kotlinpoet:1.11.0"
    const val mavlinkKotlinApi = "com.urbanmatrix.mavlink:api:${Specs.Lib.releaseVersion}"
    const val mavlinkKotlinSerialization = "com.urbanmatrix.mavlink:serialization:${Specs.Lib.releaseVersion}"
}

object TestDeps {
    const val jupiterApi = "org.junit.jupiter:junit-jupiter-api:5.8.2"
    const val jupiterEngine = "org.junit.jupiter:junit-jupiter-engine:5.8.2"
}
