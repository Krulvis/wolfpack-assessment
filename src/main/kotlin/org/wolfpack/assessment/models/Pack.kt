package org.wolfpack.assessment.models

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "packs")
class Pack(
    @Indexed(unique = true)
    var name: String,
    var wolves: Iterable<Wolf>,
    @Id var id: String? = null
)