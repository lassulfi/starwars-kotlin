package com.github.lassulfi.starwars.api.services

import com.github.lassulfi.starwars.api.model.Planeta
import java.util.*

interface PlanetaService {
    fun create(planeta: Planeta): UUID
    fun getAll(): List<Planeta>
    fun getById(id: UUID): Planeta
}