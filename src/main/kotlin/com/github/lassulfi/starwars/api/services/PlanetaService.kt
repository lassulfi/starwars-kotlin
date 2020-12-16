package com.github.lassulfi.starwars.api.services

import com.github.lassulfi.starwars.api.model.Planeta
import java.util.*

interface PlanetaService: PageableService<Planeta> {
    fun create(planeta: Planeta): UUID
    fun getAll(sort: String?, order: String? = "asc"): List<Planeta>
    fun getById(id: UUID): Planeta
    fun deleteById(id: UUID)
    fun update(planeta: Planeta)
    fun partialUpdate(planeta: Planeta)
}