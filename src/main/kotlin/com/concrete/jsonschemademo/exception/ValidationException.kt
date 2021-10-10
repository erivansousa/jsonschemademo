package com.concrete.jsonschemademo.exception

class ValidationException(val violations: MutableList<String>) : RuntimeException()
