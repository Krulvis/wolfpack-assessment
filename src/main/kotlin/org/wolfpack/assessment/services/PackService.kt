package org.wolfpack.assessment.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.wolfpack.assessment.PackRepository
import org.wolfpack.assessment.errors.RecordNotFoundException
import org.wolfpack.assessment.models.Pack
import org.wolfpack.assessment.models.Wolf

/**
 * To leave space for business logic im using services
 */
interface PackService {


    fun findAllPacks(): Iterable<Pack>

    fun createPack(pack: Pack)

    fun updatePack(id: String, pack: Pack)

    fun findPackById(id: String)

    fun deletePack(id: String)

    fun findWolvesForId(id: String): Iterable<Wolf>
}

@Service
@Transactional
class PackServiceImpl(@Autowired private val repository: PackRepository) : PackService {

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
    override fun createPack(pack: Pack) {
        repository.save(pack)
    }

    /**
     * Updates existing pack for [id]
     * Changes [id] of [pack] and removes previous pack with [id]
     */
    override fun updatePack(id: String, pack: Pack) {
        pack.id = id
        repository.deleteById(id)
        repository.save(pack)
    }

    /**
     * Returns a pack for given id
     * [id] of pack to look for
     */
    override fun findPackById(id: String) {
        repository.findById(id).orElseThrow {
            RecordNotFoundException("Can't find pack for id: $id")
        }
    }

    /**
     * Deletes pack with [id] if pack exists
     */
    override fun deletePack(id: String) {
        repository.deleteById(id)
    }

    /**
     * Returns all wolves in pack for [id]
     */
    override fun findWolvesForId(id: String): Iterable<Wolf> {
        return repository.findById(id).orElseThrow {
            RecordNotFoundException("Can't find pack for id: $id")
        }.wolves
    }

}

