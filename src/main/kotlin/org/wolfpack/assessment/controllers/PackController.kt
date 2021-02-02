package org.wolfpack.assessment.controllers

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import org.wolfpack.assessment.models.Pack
import org.wolfpack.assessment.services.PackService

@RestController
@RequestMapping("/api/")
class PackController(@Autowired private val service: PackService) {

    @GetMapping("packs/")
    fun findAll() = service.findAllPacks()

    @PostMapping("pack/")
    fun createOne(@Validated @RequestBody pack: Pack) =
        service.createPack(pack)

    @GetMapping("pack/{id}")
    fun findOne(@PathVariable id: String) =
        service.findPackById(id)

    @PutMapping("pack/{id}")
    fun updateOne(@PathVariable id: String, @Validated @RequestBody pack: Pack) =
        service.updatePack(id, pack)

    @DeleteMapping("pack/{id}")
    fun deleteOne(@PathVariable id: String) =
        service.deletePack(id)

    @GetMapping("pack/{id}/wolves")
    fun getWolves(@PathVariable id: String) =
        service.findWolvesForId(id)


}