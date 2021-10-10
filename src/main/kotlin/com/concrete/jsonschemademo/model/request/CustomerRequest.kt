package com.concrete.jsonschemademo.model.request

data class CustomerRequest(
    val name: String,
    val cpf: String,
    val phone: Phone,
    val email: String
)

data class Phone(
    val type: String,
    val number: String
)
