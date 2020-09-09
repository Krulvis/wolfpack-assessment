package org.wolfpack.assessment.errors

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
class ErrorHandler {

    @RequestMapping("/testError")
    fun raiseError() {
        throw IllegalArgumentException("This shouldn't have happened")
    }

    @ExceptionHandler(IllegalArgumentException::class)
    @ResponseStatus(HttpStatus.CONFLICT)
    fun handleError(e: IllegalArgumentException) = e.message

    @ExceptionHandler(NotImplementedError::class)
    @ResponseStatus(HttpStatus.NOT_IMPLEMENTED)
    fun handleError(e: NotImplementedError) = e.message
}

@ResponseStatus
class ResourceNotFoundException(message: String) : Exception(message)