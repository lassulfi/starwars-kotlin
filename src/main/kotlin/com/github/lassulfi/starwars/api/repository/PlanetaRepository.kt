package com.github.lassulfi.starwars.api.repository

import com.github.lassulfi.starwars.api.model.Planeta
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface PlanetaRepository: PagingAndSortingRepository<Planeta, UUID> {
}