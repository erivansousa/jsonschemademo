package com.concrete.jsonschemademo.controller

import com.concrete.jsonschemademo.exception.ValidationException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ExceptionController {

    @ExceptionHandler(value = [ValidationException::class])
    fun validationExceptionHandler(ex: ValidationException) = ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(Violations(violationsList = ex.violations))

    data class Violations(val type: String = "Validation errors", val violationsList: List<String>)
}