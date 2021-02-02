package org.wolfpack.assessment.models

import org.jetbrains.annotations.NotNull
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import java.io.Serializable

@Document(collection = "packs")
class Pack(
    @Indexed(unique = true)
    @NotNull var name: String,
    var wolves: Iterable<Wolf>,
    @Id var id: String? = null
) : Serializable