package org.wolfpack.assessment.errors

import com.fasterxml.jackson.module.kotlin.MissingKotlinParameterException
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler


@ResponseStatus(HttpStatus.NOT_FOUND)
class RecordNotFoundException(message: String) : Exception(message)

@RestControllerAdvice
class ErrorHandler : ResponseEntityExceptionHandler() {

    /**
     * Default Error handlers
     */
    @ExceptionHandler(IllegalArgumentException::class)
    @ResponseStatus(HttpStatus.CONFLICT)
    fun handleError(e: IllegalArgumentException) = e.message

    @ExceptionHandler(NotImplementedError::class)
    @ResponseStatus(HttpStatus.NOT_IMPLEMENTED)
    fun handleError(e: NotImplementedError) = e.message

    /**
     * Error handler when not being able to find a record
     */
    @ExceptionHandler(RecordNotFoundException::class)
    fun handleRecordNotFound(e: RecordNotFoundException, request: WebRequest?): ResponseEntity<ErrorResponse> {
        val error = ErrorResponse("Record Not Found", e.localizedMessage)
        return ResponseEntity(error, HttpStatus.NOT_FOUND)
    }

    /**
     * Error handler when being unable to parse http message
     * I.e. request body validation error
     */
    override fun handleHttpMessageNotReadable(
        ex: HttpMessageNotReadableException,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest
    ): ResponseEntity<Any> {
        val cause = ex.cause
        if (cause is MissingKotlinParameterException) {
            /*
            In this case we are dealing with missing parameters
            so we display which parameter is missing
             */
            val name = cause.path.fold("") { jsonPath, ref ->
                val suffix = when {
                    ref.index > -1 -> "[${ref.index}]"
                    else -> ".${ref.fieldName}"
                }
                (jsonPath + suffix).removePrefix(".")
            }
            val error = ErrorResponse("[$name] must not be null", ex.localizedMessage)
            return ResponseEntity(error, HttpStatus.BAD_REQUEST)
        }
        val error = ErrorResponse("Validation Error", ex.localizedMessage)
        return ResponseEntity(error, HttpStatus.BAD_REQUEST)
    }

}