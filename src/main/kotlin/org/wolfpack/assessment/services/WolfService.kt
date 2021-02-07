package org.wolfpack.assessment.services

import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.findById
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.Update
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.wolfpack.assessment.PackRepository
import org.wolfpack.assessment.WolfRepository
import org.wolfpack.assessment.errors.RecordNotFoundException
import org.wolfpack.assessment.models.Pack
import org.wolfpack.assessment.models.Wolf


/**
 * Base Wolf service interface
 */
interface WolfService {

    fun getAllWolves(): Iterable<Wolf>

    fun createWolf(wolf: Wolf): String

    fun updateWolf(id: String, wolf: Wolf): String

    fun findWolfById(id: String): Wolf

    fun findWolfByName(name: String): Wolf

    fun deleteWolf(wolf: Wolf)

    fun deleteAllWolves()

}

/**
 * Implementation of Wolf Service
 * Uses AutoWired Mongo DAO [repository] to create, delete, find and update wolves in MongoDB
 */
@Service
@Transactional
class WolfServiceImpl(
    @Autowired private val repository: WolfRepository,
    @Autowired private val packService: PackService,
    @Autowired private val mongoTemplate: MongoTemplate
) : WolfService {

    /**
     * Returns all wolves in db sorted by name
     */
    override fun getAllWolves(): Iterable<Wolf> {
        return repository.findAll(Sort.by(Sort.Direction.ASC, "name"))
    }

    /**
     * Saves new [wolf]
     */
    override fun createWolf(wolf: Wolf): String =
        repository.save(wolf).id!!

    /**
     * Stores [wolf] with [id] after deleting previous wolf with [id]
     * Changes id of [wolf] to [id] to make sure that it gets stored under the provided [id]
     *
     * Wolf does not have to exist when updating. As long as the wolf is valid it will get added to the DB
     */
    override fun updateWolf(id: String, wolf: Wolf): String {
        wolf.id = id
        repository.deleteById(id)
        return repository.save(wolf).id!!
    }

    /**
     * Returns wolves that matches [id]
     */
    override fun findWolfById(id: String): Wolf {
        return repository.findById(id).orElseThrow {
            RecordNotFoundException(
                "Can't find wolf for id: $id"
            )
        }
    }

    /**
     * Returns wolf for given [name]
     */
    override fun findWolfByName(name: String): Wolf {
        val query = Query(Criteria.where("name").`is`(name))
        return mongoTemplate.findOne(query, Wolf::class.java)
            ?: throw RecordNotFoundException("Can't find wolf for name: $name")
    }

    /**
     * Deletes [wolf] from collection
     * Removes all references to [wolf] from Pack collection
     */
    override fun deleteWolf(wolf: Wolf) {
        //Remove the wolf from all packs
        val query = Query(Criteria.where("wolves.id").`is`(wolf.id!!))
        val packs = mongoTemplate.find(query, Pack::class.java)
        packs.forEach { packService.removeWolfFromPack(it, wolf) }

        //Delete the wolf
        repository.deleteById(wolf.id!!)
    }

    /**
     * Deletes all wolves
     */
    override fun deleteAllWolves() {
        repository.deleteAll()
    }

}
