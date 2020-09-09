package org.wolfpack.assessment.controllers

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import org.wolfpack.assessment.models.Pack
import org.wolfpack.assessment.services.PackService

@RestController
@RequestMapping("/api/")
class PackController(@Autowired private val service: PackService) {

    @GetMapping("packs/")
    fun findAll() = service.findAllPacks()

    @GetMapping("pack/{id}")
    fun findOne(@PathVariable id: String) =
        service.findPackById(id)

    @GetMapping("pack/{id}/wolves")
    fun getWolves(@PathVariable id: String) =
        service.findWolvesForId(id)

    @PostMapping("pack/")
    fun createPack(@RequestBody pack: Pack) {
        service.createPack(pack)
    }

}