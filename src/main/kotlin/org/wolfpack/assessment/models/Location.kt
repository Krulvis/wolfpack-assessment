package org.wolfpack.assessment.models

import java.io.Serializable
import javax.validation.constraints.NotEmpty


/**
 * The location used in the Wolf document
 * Type validation does not work on Double so we use Number instead
 */
data class Location(
    @NotEmpty var longitude: Number,
    @NotEmpty var latitude: Number
) : Serializable {

    override fun toString(): String {
        return "Longitude: $longitude, Latitude: $latitude"
    }
}