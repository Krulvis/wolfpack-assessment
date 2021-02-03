package org.wolfpack.assessment.errors

/**
 * Error response to make reading errors for API users easier
 * [message] is the error
 * [details] contains information of the request
 */
class ErrorResponse(
    val message: String,
    val details: String
) {
    override fun toString(): String {
        return "Message: $message, details: $details"
    }
}