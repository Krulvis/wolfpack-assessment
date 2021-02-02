package org.wolfpack.assessment.controllers

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import org.wolfpack.assessment.models.Location
import org.wolfpack.assessment.models.Wolf
import org.wolfpack.assessment.services.WolfService
import javax.validation.Valid

@RestController
@RequestMapping("/api/")
class WolfController(@Autowired private val service: WolfService) {

    @GetMapping("wolves/")
    fun findAll() = service.getAllWolves()

    @PostMapping("wolf/")
    fun createOne(@Valid @RequestBody wolf: Wolf) =
        service.createWolf(wolf)

    @GetMapping("wolf/{id}")
    fun findOne(@PathVariable id: String) =
        service.findWolfById(id)

    @PutMapping("wolf/{id}")
    fun updateOne(@PathVariable id: String, @Valid @RequestBody wolf: Wolf) =
        service.updateWolf(id, wolf)

    @DeleteMapping("wolf/{id}")
    fun deleteOne(@PathVariable id: String) =
        service.deleteWolf(id)

    @PutMapping("wolf/{id}/location")
    fun updateLocation(@PathVariable id: String, @Valid @RequestBody location: Location) {
        val wolf = service.findWolfById(id)
        wolf.location = location
        println(wolf)
        service.updateWolf(id, wolf)
    }
}

