package com.concrete.jsonschemademo.configuration

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties("schemas-config")
data class SchemasConfig(val partners: List<PartnerSchemas>) {
    class PartnerSchemas(val name: String, val schema: String)
}

