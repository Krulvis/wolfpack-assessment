package org.wolfpack.assessment.controllers

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.wolfpack.assessment.models.Pack
import org.wolfpack.assessment.models.Wolf
import org.wolfpack.assessment.services.PackService
import javax.validation.Valid

@RestController
@RequestMapping("/api/")
class PackController(@Autowired private val service: PackService) {

    @GetMapping("packs/")
    fun findAll(): ResponseEntity<Iterable<Pack>> = ResponseEntity.ok(service.findAllPacks())

    @PostMapping("pack/")
    fun createOne(@Valid @RequestBody pack: Pack): ResponseEntity<String> =
        ResponseEntity("Created pack for id: ${service.createPack(pack)}", HttpStatus.CREATED)

    @GetMapping("pack/{id}")
    fun findOne(@PathVariable id: String): ResponseEntity<Pack> =
        ResponseEntity.ok(service.findPackById(id))

    @PostMapping("pack/{packId}/addWolf/{wolfId}")
    fun addWolf(@PathVariable packId: String, @PathVariable wolfId: String): ResponseEntity<String> =
        ResponseEntity.ok("Added wolf with id: $wolfId to pack with id: $${service.addWolfToPack(packId, wolfId)}")

    @PostMapping("pack/{packId}/removeWolf/{wolfId}")
    fun removeWolf(@PathVariable packId: String, @PathVariable wolfId: String): ResponseEntity<String> =
        ResponseEntity.ok("Added wolf with id: $wolfId to pack with id: $${service.removeWolfFromPack(packId, wolfId)}")

    @PutMapping("pack/{id}")
    fun updateOne(@PathVariable id: String, @Valid @RequestBody pack: Pack): ResponseEntity<String> =
        ResponseEntity.ok("Updated pack for id: ${service.updatePack(id, pack)}")

    @DeleteMapping("pack/{id}")
    fun deleteOne(@PathVariable id: String): ResponseEntity<String> {
        service.deletePack(id)
        return ResponseEntity.ok("Deleted pack")
    }

    @GetMapping("pack/{id}/wolves")
    fun getWolves(@PathVariable id: String): ResponseEntity<Iterable<Wolf>> =
        ResponseEntity.ok(service.findWolvesForPack(id))


}