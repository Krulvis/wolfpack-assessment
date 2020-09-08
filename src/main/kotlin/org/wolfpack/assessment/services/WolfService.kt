package org.wolfpack.assessment.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.wolfpack.assessment.Pack
import org.wolfpack.assessment.Wolf
import org.wolfpack.assessment.WolfRepository

/**
 * Om ruimte over te laten voor business logic maak ik gebruik van services
 */
interface WolfService {

    fun getAllWolves(): Iterable<Wolf>

    fun createWolf(wolf: Wolf)

    fun updateWolf(id: String, wolf: Wolf)

    fun findWolfById(id: String): Wolf

    fun deleteWolf(id: String)
}

@Service
@Transactional
class WolfServiceImpl(@Autowired private val repository: WolfRepository) : WolfService {

    override fun getAllWolves(): Iterable<Wolf> {
        return repository.findAll()
    }

    override fun createWolf(wolf: Wolf) {
        TODO("Not yet implemented")
    }

    override fun updateWolf(id: String, wolf: Wolf) {
        TODO("Not yet implemented")
    }

    override fun findWolfById(id: String): Wolf {
        return repository.findById(id).orElseThrow()
    }

    override fun deleteWolf(id: String) {
        TODO("Not yet implemented")
    }

}
