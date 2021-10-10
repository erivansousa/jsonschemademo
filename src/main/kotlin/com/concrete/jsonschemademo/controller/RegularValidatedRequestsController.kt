package com.concrete.jsonschemademo.controller

import com.concrete.jsonschemademo.exception.ValidationException
import com.concrete.jsonschemademo.model.request.CustomerRequest
import org.apache.commons.lang3.StringUtils
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class RegularValidatedRequestsController {

    @PostMapping("/regularCustomerRegister")
    fun registerNewCustomer(@RequestBody newCustomer: CustomerRequest): ResponseEntity<String> {

        validateCustomer(newCustomer)

        return ResponseEntity.ok("customer validated")
    }

    private fun validateCustomer(newCustomer: CustomerRequest) {

        val violations = mutableListOf<String>()

        newCustomer.run {
            //validacao de  nome
            name.let {
                when {
                    it.isNullOrBlank() -> violations.add("name is null or blank")
                    it.length > 20 -> violations.add("name must be less than 20 characters")
                    else -> println("name is valid")
                }
            }

            //validacao de cpf
            cpf.let {
                when {
                    it.isNullOrBlank() -> violations.add("cpf is null or blank")
                    it.length > 11 -> violations.add("cpf must be 11 characters long")
                    StringUtils.isNumeric(it).not() -> violations.add("cpf must contain only numbers")
                    else -> println("cpf is valid")
                }
            }

            //validacao de  email
            email.let {
                val emailRegex = Regex("([a-zA-Z0-9._%-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6})*")
                when {
                    it.isNullOrBlank() -> violations.add("email is null or blank")
                    emailRegex.matches(it).not() -> violations.add("email pattern is not correct")
                    else -> println("email is valid")
                }
            }

            //validacao telefone
            phone.let {
                when {
                    phone.type.isNullOrBlank() -> violations.add("phone.type is null or blank")
                    phone.type !in listOf("MOBILE", "LANDLINE") -> violations.add("phone.type value is not valid")
                    else -> println("phone.type is valid")
                }
                when {
                    phone.number.isNullOrBlank() -> violations.add("phone.number is null or blank")
                    phone.number.length < 10 -> violations.add("phone.number must be 10+ characters long")
                    StringUtils.isNumeric(phone.number).not() -> violations.add("phone.number must contain only numbers")
                    else -> println("phone.number is valid")
                }
            }
        }

        if (violations.isNotEmpty())
            throw ValidationException(violations)

    }
}