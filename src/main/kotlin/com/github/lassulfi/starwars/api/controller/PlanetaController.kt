package com.github.lassulfi.starwars.api.controller

import com.github.lassulfi.starwars.api.model.Planeta
import com.github.lassulfi.starwars.api.services.PlanetaService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = ["/planetas"])
class PlanetaController(val service: PlanetaService) {

    @GetMapping
    fun getAll(): List<Planeta> = service.getAll()
}