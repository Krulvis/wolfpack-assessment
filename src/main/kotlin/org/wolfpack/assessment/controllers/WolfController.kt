package org.wolfpack.assessment.controllers

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.crossstore.ChangeSetPersister
import org.springframework.data.geo.Point
import org.springframework.web.bind.annotation.*
import org.wolfpack.assessment.Pack
import org.wolfpack.assessment.Wolf
import org.wolfpack.assessment.WolfRepository
import org.wolfpack.assessment.services.WolfService

@RestController
@RequestMapping("/api/wolf")
class WolfController(@Autowired private val service: WolfService) {

    @GetMapping("/")
    fun findAll() = service.getAllWolves()

    @PostMapping("/")
    fun createWolf(@RequestBody wolf: Wolf) =
        service.createWolf(wolf)

    @GetMapping("/{id}")
    fun findOne(@PathVariable id: String) =
        service.findWolfById(id)

    @PutMapping("/{id}")
    fun updateWolf(@PathVariable id: String, @RequestBody wolf: Wolf) =
        service.updateWolf(id, wolf)

    @DeleteMapping("/{id}")
    fun deleteWolf(@PathVariable id: String) =
        service.deleteWolf(id)

    @PutMapping("/{id}/location")
    fun updateLocation(@PathVariable id: String, @RequestBody point: Point) =
        service.updateLocationForId(id, point)
}