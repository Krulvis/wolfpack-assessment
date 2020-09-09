package org.wolfpack.assessment.controllers

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import org.wolfpack.assessment.Pack
import org.wolfpack.assessment.services.PackService

@RestController
@RequestMapping("/api/pack")
class PackController(@Autowired private val service: PackService) {

    @GetMapping("/")
    fun findAll() = service.findAllPacks()

    @GetMapping("/{id}")
    fun findOne(@PathVariable id: String) =
        service.findPackById(id)

    @GetMapping("/{id}/wolves")
    fun getWolves(@PathVariable id: String) =
        service.findWolvesForId(id)

    @PostMapping("/")
    fun createPack(@RequestBody pack: Pack) {
        service.createPack(pack)
    }

}