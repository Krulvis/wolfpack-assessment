package org.wolfpack.assessment.services

import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.DuplicateKeyException
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.wolfpack.assessment.PackRepository
import org.wolfpack.assessment.WolfRepository
import org.wolfpack.assessment.errors.RecordNotFoundException
import org.wolfpack.assessment.models.Pack
import org.wolfpack.assessment.models.Wolf

/**
 * To leave space for business logic im using services
 */
interface PackService {

    fun findAllPacks(): Iterable<Pack>

    fun createPack(pack: Pack): String

    fun updatePack(id: String, pack: Pack): String

    fun findPackById(id: String): Pack

    fun addWolfToPack(pack: Pack, wolf: Wolf): String

    fun removeWolfFromPack(pack: Pack, wolf: Wolf): String

    fun deletePack(id: String)

    fun findWolvesForPack(id: String): Iterable<Wolf>

}

@Service
@Transactional
class PackServiceImpl(
    @Autowired private val repository: PackRepository
) : PackService {

    /**
     * Returns all Packs
     */
    override fun findAllPacks(): Iterable<Pack> {
        return repository.findAll(Sort.by(Sort.Direction.ASC, "name"))
    }

    /**
     * Creates a new pack
     * [pack] should be a new Pack
     */
    override fun createPack(pack: Pack): String {
        return repository.save(pack).id!!
    }

    /**
     * Updates existing pack for [id]
     * Changes [id] of [pack] and removes previous pack with [id]
     */
    override fun updatePack(id: String, pack: Pack): String {
        pack.id = id
        repository.deleteById(id)
        return repository.save(pack).id!!
    }

    /**
     * Returns a pack for given [id]
     */
    override fun findPackById(id: String): Pack {
        return repository.findById(id).orElseThrow {
            RecordNotFoundException("Can't find pack for id: $id")
        }
    }

    /**
     * Adds wolf for [wolf] to [pack]
     */
    override fun addWolfToPack(pack: Pack, wolf: Wolf): String {
        if (pack.wolves.contains(wolf)) {
            throw DuplicateKeyException("Wolf: ${wolf.id} already in pack: ${pack.id}")
        }
        pack.wolves.add(wolf)
        return updatePack(pack.id!!, pack)
    }

    /**
     * Removes [wolf] from [pack]
     * If there are no more wolves in the pack
     * (Criteria of Pack is that it has at least 1 wolf)
     */
    override fun removeWolfFromPack(pack: Pack, wolf: Wolf): String {
        if (!pack.wolves.contains(wolf)) {
            throw RecordNotFoundException("Wolf: ${wolf.id} not in pack: ${pack.id}")
        }
        pack.wolves.remove(wolf)
        return if (pack.wolves.isEmpty()) {
            deletePack(pack.id!!)
            "Deleted pack: ${pack.id} since there are no wolves left"
        } else {
            updatePack(pack.id!!, pack)
        }
    }

    /**
     * Deletes pack with [id] if pack exists
     */
    override fun deletePack(id: String) {
        repository.findById(id).orElseThrow {
            RecordNotFoundException("Can't find pack for id: $id")
        }
        repository.deleteById(id)
    }

    /**
     * Returns all wolves in pack for [id]
     */
    override fun findWolvesForPack(id: String): Iterable<Wolf> {
        return repository.findById(id).orElseThrow {
            RecordNotFoundException("Can't find pack for id: $id")
        }.wolves
    }

}

