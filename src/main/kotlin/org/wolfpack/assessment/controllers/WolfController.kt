package org.wolfpack.assessment.controllers

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.geo.Point
import org.springframework.web.bind.annotation.*
import org.wolfpack.assessment.models.Wolf
import org.wolfpack.assessment.services.WolfService

@RestController
@RequestMapping("/api/")
class WolfController(@Autowired private val service: WolfService) {

    @GetMapping("wolves/")
    fun findAll() = service.getAllWolves()

    @PostMapping("wolf/")
    fun createWolf(@RequestBody wolf: Wolf) =
        service.createWolf(wolf)

    @GetMapping("wolf/{id}")
    fun findOne(@PathVariable id: String) =
        service.findWolfById(id)

    @PutMapping("wolf/{id}")
    fun updateWolf(@PathVariable id: String, @RequestBody wolf: Wolf) =
        service.updateWolf(id, wolf)

    @DeleteMapping("wolf/{id}")
    fun deleteWolf(@PathVariable id: String) =
        service.deleteWolf(id)

    @PutMapping("wolf/{id}/location")
    fun updateLocation(@PathVariable id: String, @RequestBody point: Point) =
        service.updateLocationForId(id, point)
}