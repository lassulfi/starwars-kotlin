package com.github.lassulfi.starwars.api.repository

import com.github.lassulfi.starwars.api.model.Planeta
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface PlanetaRepository: CrudRepository<Planeta, Long> {
}