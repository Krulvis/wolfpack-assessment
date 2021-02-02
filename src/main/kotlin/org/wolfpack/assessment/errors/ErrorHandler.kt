package org.wolfpack.assessment.errors

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.context.request.WebRequest
import java.util.*

@ResponseStatus(HttpStatus.NOT_FOUND)
class RecordNotFoundException(message: String) : Exception(message)

@ControllerAdvice
class ErrorHandler {

    @ExceptionHandler(IllegalArgumentException::class)
    @ResponseStatus(HttpStatus.CONFLICT)
    fun handleError(e: IllegalArgumentException) = e.message

    @ExceptionHandler(NotImplementedError::class)
    @ResponseStatus(HttpStatus.NOT_IMPLEMENTED)
    fun handleError(e: NotImplementedError) = e.message

    @ExceptionHandler(RecordNotFoundException::class)
    fun handleRecordNotFoundException(e: RecordNotFoundException, request: WebRequest?): ResponseEntity<Any?> {
        val details: MutableList<String> = ArrayList()
        details.add(e.localizedMessage)
        val error = ErrorResponse("Record Not Found", details)
        return ResponseEntity(error, HttpStatus.NOT_FOUND)
    }

}