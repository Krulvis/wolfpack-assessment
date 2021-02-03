package org.wolfpack.assessment.models

import org.jetbrains.annotations.NotNull
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document
import java.io.Serializable
import javax.validation.Valid
import javax.validation.constraints.NotEmpty

@Document(collection = "packs")
class Pack(
    @Indexed(unique = true)
    @NotNull var name: String,
    @NotEmpty @DBRef var wolves: MutableList<@Valid Wolf>,
    @Id var id: String? = null
) : Serializable