package com.concrete.jsonschemademo

import com.concrete.jsonschemademo.configuration.SchemasConfig
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication


@SpringBootApplication
@EnableConfigurationProperties(SchemasConfig::class)
class JsonSchemaDemoApplication

fun main(args: Array<String>) {
	runApplication<JsonSchemaDemoApplication>(*args)
}
