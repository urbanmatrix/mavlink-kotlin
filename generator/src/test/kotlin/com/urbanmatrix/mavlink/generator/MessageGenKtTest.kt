package com.urbanmatrix.mavlink.generator

import org.junit.jupiter.api.Test
import java.io.File

class MessageGenKtTest {

    @Test
    fun messageGeneration() {
        val models = listOf(readMavlinkMinimal(), readMavlinkCommon())
        val enumResolver = EnumResolver(BASE_PACKAGE, models)

        for (model in models) {
            model
                .generateDialectFile(BASE_PACKAGE)
                .writeTo(File(GENERATED_SOURCES_DIR))

            for (enum in model.enums) {
                enum
                    .generateEnumFile("$BASE_PACKAGE.${model.name}")
                    .writeTo(File(GENERATED_SOURCES_DIR))
            }

            for (message in model.messages) {
                message
                    .generateMessageFile("$BASE_PACKAGE.${model.name}", enumResolver)
                    .writeTo(File(GENERATED_SOURCES_DIR))
            }
        }
    }
}
