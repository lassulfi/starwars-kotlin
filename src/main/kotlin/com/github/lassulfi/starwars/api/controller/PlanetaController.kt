package com.github.lassulfi.starwars.api.controller

import com.github.lassulfi.starwars.api.model.Planeta
import com.github.lassulfi.starwars.api.services.PlanetaService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI
import java.util.*

@RestController
@RequestMapping(value = ["/planetas"])
class PlanetaController(val service: PlanetaService) {

    @GetMapping
    fun getAll(): List<Planeta> = service.getAll()

    @GetMapping("/{id}")
    fun getById(@PathVariable id: UUID): Planeta = service.getById(id)

    @PostMapping
    fun create(@RequestBody planeta: Planeta): ResponseEntity<Any> {
        val id = service.create(planeta)
        return ResponseEntity.created(URI.create("/planetas/${id}")).build()
    }
}