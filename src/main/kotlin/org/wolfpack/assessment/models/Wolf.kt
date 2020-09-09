package org.wolfpack.assessment.models

import org.springframework.data.annotation.Id
import org.springframework.data.geo.Point
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDate

@Document(collection = "wolves")
class Wolf(
    @Indexed(unique = true)
    var name: String,
    var gender: String,
    var birthday: LocalDate,
    var location: Point?,
    @Id var id: String? = null
)