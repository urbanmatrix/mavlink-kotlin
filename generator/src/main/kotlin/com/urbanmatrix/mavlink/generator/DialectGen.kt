package com.urbanmatrix.mavlink.generator

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.buildCodeBlock
import com.urbanmatrix.mavlink.api.AbstractMavDialect
import com.urbanmatrix.mavlink.generator.models.MavlinkModel

fun MavlinkModel.generateDialectFile(basePackageName: String): FileSpec {
    val packageName = "$basePackageName.${name.lowercase()}"
    val dialect = TypeSpec.objectBuilder(formattedName)
        .superclass(AbstractMavDialect::class)
        .addSuperclassConstructorParameter(generateDependencies(basePackageName))
        .addSuperclassConstructorParameter(generateMessages(basePackageName))
        .build()

    return FileSpec.builder(packageName, formattedName)
        .addType(dialect)
        .build()
}

private fun MavlinkModel.generateDependencies(basePackageName: String) = buildCodeBlock {
    addStatement("")
    indent()

    if (includes.isEmpty()) {
        add("emptySet()")
        unindent()
        return@buildCodeBlock
    }

    addStatement("setOf(")
    indent()
    includes
        .map { it.removeSuffix(".xml") }
        .map {
            ClassName(
                "$basePackageName.$it",
                "${CaseFormat.fromSnake(it).toUpperCamel()}Dialect"
            )
        }
        .forEach { addStatement("%T", it) }
    unindent()
    add(")")

    unindent()
}

private fun MavlinkModel.generateMessages(basePackageName: String) = buildCodeBlock {
    addStatement("")
    indent()

    if (messages.isEmpty()) {
        add("emptyMap()")
        unindent()
        return@buildCodeBlock
    }

    addStatement("mapOf(")
    indent()
    messages
        .map {
            ClassName(
                "$basePackageName.$name",
                it.formattedName
            )
        }
        .forEach { addStatement("%1T.classMetadata.id to %1T.classMetadata,", it) }
    unindent()
    addStatement(")")

    unindent()
}
