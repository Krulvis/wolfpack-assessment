package org.wolfpack.assessment.errors

import com.fasterxml.jackson.databind.JsonMappingException
import com.fasterxml.jackson.databind.exc.InvalidFormatException
import com.fasterxml.jackson.module.kotlin.MissingKotlinParameterException
import org.springframework.dao.DuplicateKeyException
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
     * Error handler duplicate key is found
     * This is thrown when adding a wolf to pack that already contains that Wolf
     */
    @ExceptionHandler(DuplicateKeyException::class)
    fun handleDuplicateKeyError(e: DuplicateKeyException, request: WebRequest?): ResponseEntity<ErrorResponse> {
        val error = ErrorResponse("Duplicate key error", e.localizedMessage)
        return ResponseEntity(error, HttpStatus.BAD_REQUEST)
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
        when (cause) {
            is MissingKotlinParameterException -> {
                /*
                In this case we are dealing with missing parameters
                so we display which parameter is missing
                 */
                val name = getCorruptField(cause.path)
                val error = ErrorResponse("[$name] must not be null", ex.localizedMessage)
                return ResponseEntity(error, HttpStatus.BAD_REQUEST)
            }
            is InvalidFormatException -> {
                /*
                In this case the field is not parsable and we need to figure out what error to display
                 */
                val name = getCorruptField(cause.path)
//                println("Found InvalidFormatException at: $name, value: ${cause.value}")
                val format = getCorrectFormat(cause.targetType.simpleName)
                val error = ErrorResponse(
                    "Incorrect format for: [$name], correct format: \"${format}\"", ex.localizedMessage
                )
                return ResponseEntity(error, HttpStatus.BAD_REQUEST)
            }
        }
        val error = ErrorResponse("Validation Error", ex.localizedMessage)
        return ResponseEntity(error, HttpStatus.BAD_REQUEST)
    }

    /**
     * Extracts the corrupt field from
     */
    fun getCorruptField(path: List<JsonMappingException.Reference>): String {
        return path.fold("") { jsonPath, ref ->
            val suffix = when {
                ref.index > -1 -> "[${ref.index}]"
                else -> ".${ref.fieldName}"
            }
            (jsonPath + suffix).removePrefix(".")
        }
    }

    /**
     * Determines the correct format for requests with InvalidFormat error
     */
    fun getCorrectFormat(clazz: String): String {
        when (clazz) {
            "LocalDate" -> {
                return "1994-02-05"
            }
        }
        return "unknown"
    }


}