package org.wolfpack.assessment.controllers

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import org.wolfpack.assessment.PackRepository

@RestController
@RequestMapping("/api/pack")
class PackController(@Autowired private val repository: PackRepository) {

    @GetMapping("/")
    fun findAll() = repository.findAll()

    @GetMapping("/{id}")
    fun findOne(@PathVariable id: String) =
        repository.findById(id)

    @GetMapping("/{id}/wolves")
    fun getWolves(@PathVariable id: String) =
        repository.findById(id).map { it.wolves }

    @PutMapping("/")
    fun createPack(model: Model) {

    }

}