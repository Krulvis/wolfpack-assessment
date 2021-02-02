package org.wolfpack.assessment.models

import org.jetbrains.annotations.NotNull
import org.springframework.data.annotation.Id
import org.springframework.data.geo.Point
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import java.io.Serializable
import java.time.LocalDate

@Document(collection = "wolves")
class Wolf(
    @Indexed(unique = true)
    @NotNull var name: String,
    @NotNull var gender: String,
    @NotNull var birthday: LocalDate,
    @NotNull var location: Location,
    @Id var id: String? = null
) : Serializable {
    override fun toString(): String {
        return "Name: $name, Gender: $gender, Location: $location"
    }
}