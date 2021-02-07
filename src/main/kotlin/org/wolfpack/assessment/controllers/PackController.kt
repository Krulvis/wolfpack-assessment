package org.wolfpack.assessment.controllers

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.wolfpack.assessment.models.Pack
import org.wolfpack.assessment.models.Wolf
import org.wolfpack.assessment.services.PackService
import org.wolfpack.assessment.services.WolfService
import javax.validation.Valid

/**
 * Since we use @Valid annotation for any of the routes that have a RequestBody,
 * we can be sure that all services receive validated Packs&Wolfs
 */
@RestController
@RequestMapping("/api/")
class PackController(
    @Autowired private val packService: PackService,
    @Autowired private val wolfService: WolfService
) {

    @GetMapping("packs/")
    fun findAll(): ResponseEntity<Iterable<Pack>> = ResponseEntity.ok(packService.findAllPacks())

    @PostMapping("pack/")
    fun createOne(@Valid @RequestBody pack: Pack): ResponseEntity<String> =
        ResponseEntity("Created pack for id: ${packService.createPack(pack)}", HttpStatus.CREATED)

    @GetMapping("pack/{id}")
    fun findOne(@PathVariable id: String): ResponseEntity<Pack> =
        ResponseEntity.ok(packService.findPackById(id))

    @PostMapping("pack/{packId}/addWolf/{wolfId}")
    fun addWolf(@PathVariable packId: String, @PathVariable wolfId: String): ResponseEntity<String> {
        val wolf = wolfService.findWolfById(wolfId)
        val pack = packService.findPackById(packId)
        return ResponseEntity.ok(
            "Added wolf with id: $wolfId to pack with id: $${
                packService.addWolfToPack(pack, wolf)
            }"
        )
    }

    @PostMapping("pack/{packId}/removeWolf/{wolfId}")
    fun removeWolf(@PathVariable packId: String, @PathVariable wolfId: String): ResponseEntity<String> {
        val wolf = wolfService.findWolfById(wolfId)
        val pack = packService.findPackById(packId)
        return ResponseEntity.ok(
            "Removed wolf with id: $wolfId from pack with id: $${
                packService.removeWolfFromPack(pack, wolf)
            }"
        )
    }

    @PutMapping("pack/{id}")
    fun updateOne(@PathVariable id: String, @Valid @RequestBody pack: Pack): ResponseEntity<String> =
        ResponseEntity.ok("Updated pack for id: ${packService.updatePack(id, pack)}")

    @DeleteMapping("pack/{id}")
    fun deleteOne(@PathVariable id: String): ResponseEntity<String> {
        packService.deletePack(id)
        return ResponseEntity.ok("Deleted pack for id: $id")
    }

    @GetMapping("pack/{id}/wolves")
    fun getWolves(@PathVariable id: String): ResponseEntity<Iterable<Wolf>> =
        ResponseEntity.ok(packService.findWolvesForPack(id))


}