package org.wolfpack.assessment.models

import org.jetbrains.annotations.NotNull
import org.springframework.data.mongodb.core.mapping.Document
import java.io.Serializable


@Document(collection = "wolves")
class Location(@NotNull var longitude: Double, @NotNull var latitude: Double) : Serializable {
    override fun toString(): String {
        return "Longitude: $longitude, Latitude: $latitude"
    }
}