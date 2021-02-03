package org.wolfpack.assessment.models

import java.io.Serializable
import javax.validation.constraints.NotEmpty


data class Location(
    @NotEmpty var longitude: Double,
    @NotEmpty var latitude: Double
) : Serializable {

    override fun toString(): String {
        return "Longitude: $longitude, Latitude: $latitude"
    }
}