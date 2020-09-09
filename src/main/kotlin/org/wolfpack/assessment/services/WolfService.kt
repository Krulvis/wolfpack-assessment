package org.wolfpack.assessment.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.geo.Point
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.wolfpack.assessment.Wolf
import org.wolfpack.assessment.WolfRepository
import org.wolfpack.assessment.controllers.ResourceNotFoundException

/**
 * Om ruimte over te laten voor business logic maak ik gebruik van services
 */
interface WolfService {

    fun getAllWolves(): Iterable<Wolf>

    fun createWolf(wolf: Wolf)

    fun updateWolf(id: String, wolf: Wolf)

    fun findWolfById(id: String): Wolf

    fun findWolfByName(name: String): Wolf

    fun updateLocationForId(id: String, point: Point)

    fun deleteWolf(id: String)
}

@Service
@Transactional
class WolfServiceImpl(
    @Autowired private val repository: WolfRepository,
    @Autowired private val mongoTemplate: MongoTemplate
) : WolfService {

    override fun getAllWolves(): Iterable<Wolf> {
        return repository.findAll()
    }

    override fun createWolf(wolf: Wolf) {
        repository.save(wolf)
    }

    override fun updateWolf(id: String, wolf: Wolf) {
        repository.findById(id).ifPresent {
            it.birthday = wolf.birthday
            it.name = wolf.name
            it.gender = wolf.gender
            it.location = wolf.location
            repository.save(it)
        }
    }

    override fun findWolfById(id: String): Wolf {
        return repository.findById(id).orElseThrow { ResourceNotFoundException("Wolf not found for id: $id") }
    }

    override fun findWolfByName(name: String): Wolf {
        val query = Query(Criteria.where("name").`is`(name))
        return mongoTemplate.findOne(query, Wolf::class.java)
            ?: throw ResourceNotFoundException("Could not find wolf for name: $name")
    }

    override fun updateLocationForId(id: String, point: Point) {
        repository.findById(id).ifPresent {
            it.location = point
            repository.save(it)
        }
    }

    override fun deleteWolf(id: String) {
        repository.deleteById(id)
    }

}
