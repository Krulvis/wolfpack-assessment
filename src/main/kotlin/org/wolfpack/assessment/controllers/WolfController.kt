package org.wolfpack.assessment.controllers

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.crossstore.ChangeSetPersister
import org.springframework.web.bind.annotation.*
import org.wolfpack.assessment.Wolf
import org.wolfpack.assessment.WolfRepository
import org.wolfpack.assessment.services.WolfService

@RestController
@RequestMapping("/api/wolf")
class WolfController(@Autowired private val service: WolfService) {

    @GetMapping("/")
    fun findAll() = service.getAllWolves()

    @GetMapping("/{id}")
    fun findOne(@PathVariable id: String) =
        service.findWolfById(id)

    @PostMapping("/")
    fun createWolf(@RequestBody wolf: Wolf) =
        service.createWolf(wolf)

    @PutMapping("/{id}")
    fun updateWolf(@PathVariable id: String, @RequestBody wolf: Wolf) =
        service.updateWolf(id, wolf)

    @DeleteMapping("/{id}")
    fun deleteWolf(@PathVariable id: String) =
        service.deleteWolf(id)


}