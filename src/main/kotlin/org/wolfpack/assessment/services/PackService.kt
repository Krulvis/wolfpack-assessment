package org.wolfpack.assessment.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.DuplicateKeyException
import org.springframework.data.domain.Sort
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

    fun addWolfToPack(packId: String, wolfId: String): String

    fun removeWolfFromPack(packId: String, wolfId: String): String

    fun deletePack(id: String)

    fun findWolvesForPack(id: String): Iterable<Wolf>
}

@Service
@Transactional
class PackServiceImpl(
    @Autowired private val repository: PackRepository,
    @Autowired private val wolfService: WolfService
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
     * Adds wolf for [wolfId] to pack for [packId]
     */
    override fun addWolfToPack(packId: String, wolfId: String): String {
        val wolf = wolfService.findWolfById(wolfId)
        val pack = findPackById(packId)
        if (pack.wolves.contains(wolf)) {
            throw DuplicateKeyException("Wolf: $wolfId already in pack: $packId")
        }
        pack.wolves.add(wolf)
        return updatePack(packId, pack)
    }

    /**
     * Removes wolf for [wolfId] from pack for [packId]
     */
    override fun removeWolfFromPack(packId: String, wolfId: String): String {
        val wolf = wolfService.findWolfById(wolfId)
        val pack = findPackById(packId)
        pack.wolves.remove(wolf)
        return updatePack(packId, pack)
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
    override fun findWolvesForPack(id: String): Iterable<Wolf> {
        return repository.findById(id).orElseThrow {
            RecordNotFoundException("Can't find pack for id: $id")
        }.wolves
    }

}

