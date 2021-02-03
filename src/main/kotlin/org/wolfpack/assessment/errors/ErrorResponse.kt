package org.wolfpack.assessment.errors

import javax.xml.bind.annotation.XmlRootElement


/**
 * Error response to make reading errors for API users easier
 * [message] is the error
 * [details] contains information of the request
 */
@XmlRootElement(name = "error")
class ErrorResponse(
    val message: String,
    val details: String
) {
    override fun toString(): String {
        return "Message: $message, details: $details"
    }
}