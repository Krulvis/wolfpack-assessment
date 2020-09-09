package org.wolfpack.assessment

import org.springframework.data.mongodb.repository.MongoRepository
import org.wolfpack.assessment.models.Pack
import org.wolfpack.assessment.models.Wolf

/**
 * Interfaces that are used as repositories
 * They can be empty since they are autowired
 */
interface PackRepository : MongoRepository<Pack, String>

interface WolfRepository : MongoRepository<Wolf, String>