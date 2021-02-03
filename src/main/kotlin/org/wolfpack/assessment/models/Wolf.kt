package org.wolfpack.assessment.models

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import java.io.Serializable
import java.time.LocalDate
import javax.validation.Valid
import javax.validation.constraints.NotEmpty

@Document(collection = "wolves")
data class Wolf(
    @Indexed(unique = true)
    @NotEmpty var name: String,
    @NotEmpty var gender: String,
    @NotEmpty var birthday: LocalDate,
    @Valid @NotEmpty var location: Location,
    @Id var id: String? = null
) : Serializable {
    override fun toString(): String {
        return "Name: $name, Gender: $gender, Location: $location"
    }
}