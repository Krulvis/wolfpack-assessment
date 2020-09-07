package org.wolfpack.assessment

import org.springframework.data.geo.Point
import java.time.LocalDate
import javax.persistence.*

@Entity
class Wolf(
    var name: String,
    var gender: String,
    var birthday: LocalDate,
    var location: Point,
    @Id @GeneratedValue var id: Long? = null
)

/**
 * Since we want to be able to display a list of all packs with their wolves,
 * I decided to put the relationship in the Pack entity
 */
@Entity
class Pack(
    var name: String,
    @ManyToMany var wolves: List<Wolf>,
    @Id @GeneratedValue var id: Long? = null
)