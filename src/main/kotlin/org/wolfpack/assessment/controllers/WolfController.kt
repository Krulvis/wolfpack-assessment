package org.wolfpack.assessment.controllers

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import org.wolfpack.assessment.models.Location
import org.wolfpack.assessment.models.Wolf
import org.wolfpack.assessment.services.WolfService
import javax.validation.Valid

@RestController
@RequestMapping("/api/")
class WolfController(@Autowired private val service: WolfService) {

    @GetMapping("wolves/")
    fun findAll(): ResponseEntity<Iterable<Wolf>> =
        ResponseEntity.ok(service.getAllWolves())

    @PostMapping("wolf/")
    fun createOne(@Valid @RequestBody wolf: Wolf, bindingResult: BindingResult): ResponseEntity<String> =
        ResponseEntity("Created wolf with id: ${service.createWolf(wolf)}", HttpStatus.CREATED)

    @GetMapping("wolf/{id}")
    fun findOne(@PathVariable id: String): ResponseEntity<Wolf> =
        ResponseEntity.ok(service.findWolfById(id))

    @PutMapping("wolf/{id}")
    fun updateOne(
        @PathVariable id: String,
        @Valid @RequestBody wolf: Wolf
    ): ResponseEntity<String> =
        ResponseEntity.ok("Updated wolf for id: ${service.updateWolf(id, wolf)}")

    @DeleteMapping("wolf/{id}")
    fun deleteOne(@PathVariable id: String): ResponseEntity<String> {
        service.deleteWolf(id)
        return ResponseEntity.ok("Deleted wolf successfully")
    }

    @PutMapping("wolf/{id}/location")
    fun updateLocation(
        @PathVariable id: String,
        @Valid @RequestBody location: Location
    ): ResponseEntity<String> {
        val wolf = service.findWolfById(id)
        wolf.location = location
        println(wolf)
        service.updateWolf(id, wolf)
        return ResponseEntity.ok("Updated location successfully")
    }
}

