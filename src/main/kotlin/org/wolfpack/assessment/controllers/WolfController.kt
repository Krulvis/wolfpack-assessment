package org.wolfpack.assessment.controllers

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import org.wolfpack.assessment.Wolf
import org.wolfpack.assessment.WolfRepository

@RestController
@RequestMapping("/api/wolf")
class WolfController(@Autowired private val repository: WolfRepository) {

    @GetMapping("/")
    fun findAll() = repository.findAll()

    @GetMapping("/{id}")
    fun findOne(@PathVariable id: String) =
        repository.findById(id)

    @PostMapping("/")
    fun createWolf(@RequestBody wolf: Wolf) =
        repository.save(wolf)

    @PutMapping("/{id}")
    fun updateWolf(@PathVariable id: String, @RequestBody wolf: Wolf) {
        val wolf = repository.findById(id)
    }

}