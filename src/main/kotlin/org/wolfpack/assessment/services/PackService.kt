package org.wolfpack.assessment.services

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.wolfpack.assessment.Pack
import org.wolfpack.assessment.Wolf

/**
 * Om ruimte over te laten voor business logic maak ik gebruik van services
 */
interface PackService {
    fun findAllPacks(): Iterable<Pack>

    fun createPack(pack: Pack)

    fun updatePack(pack: Pack)

    fun findPackById(id: String)

    fun findWolvesForId(id: String): Iterable<Wolf>

    fun deletePack(id: String)
}

@Service
@Transactional
class PackServiceImpl : PackService {

    override fun findAllPacks(): Iterable<Pack> {
        TODO("Not yet implemented")
    }

    override fun createPack(pack: Pack) {
        TODO("Not yet implemented")
    }

    override fun updatePack(pack: Pack) {
        TODO("Not yet implemented")
    }

    override fun findPackById(id: String) {
        TODO("Not yet implemented")
    }

    override fun findWolvesForId(id: String): Iterable<Wolf> {
        TODO("Not yet implemented")
    }

    override fun deletePack(id: String) {
        TODO("Not yet implemented")
    }

}

