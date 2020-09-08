package org.wolfpack.assessment

import org.springframework.data.annotation.Id
import org.springframework.data.geo.Point
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDate

@Document(collection = "wolves")
class Wolf(
    var name: String,
    var gender: String,
    var birthday: LocalDate,
    var location: Point?,
    @Id var id: String? = null
)

@Document(collection = "packs")
class Pack(
    var name: String,
    var wolves: Iterable<Wolf>,
    @Id var id: String? = null
)