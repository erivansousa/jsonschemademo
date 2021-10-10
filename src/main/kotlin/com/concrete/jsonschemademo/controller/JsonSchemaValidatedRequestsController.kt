package com.concrete.jsonschemademo.controller

import com.concrete.jsonschemademo.configuration.SchemasConfig
import com.concrete.jsonschemademo.exception.ValidationException
import com.fasterxml.jackson.databind.ObjectMapper
import com.networknt.schema.JsonSchemaFactory
import com.networknt.schema.SpecVersion
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RestController


@RestController
class JsonSchemaValidatedRequestsController(
    val schemasConfig: SchemasConfig
) {
    @PostMapping("/jsonSchemaCustomerRegister")
    fun registerNewConsumer(
        @RequestHeader("partnerName") partnerName: String = "partner1",
        @RequestBody newCustomer: String
    ): ResponseEntity<String> {

        validateCustomer(partnerName, newCustomer)

        return ResponseEntity.ok("customer validated")
    }

    private fun validateCustomer(partnerName: String, newCustomer: String) {
        val mapper = ObjectMapper()
        val factory = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V7)
        val schema = schemasConfig.partners.first { it.name == partnerName }.schema

        val validator = factory.getSchema(schema)

        val violations = validator.validate(mapper.readTree(newCustomer)).map {
            it.message
        }

        if (violations.isNotEmpty())
            throw ValidationException(violations.toMutableList())
    }
}