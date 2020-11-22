package com.github.lassulfi.starwars.api.services.impl

import com.github.lassulfi.starwars.api.exceptions.InternalServerErrorException
import com.github.lassulfi.starwars.api.exceptions.ResourceNotFoundException
import com.github.lassulfi.starwars.api.model.Planeta
import com.github.lassulfi.starwars.api.repository.PlanetaRepository
import com.github.lassulfi.starwars.api.services.PlanetaService
import org.springframework.stereotype.Service
import java.util.*
@Service
class PlanetaServiceImpl(val repository: PlanetaRepository): PlanetaService {

    override fun create(planeta: Planeta): UUID {
        try {
            repository.save(planeta)
        } catch (e: Exception) {
            throw InternalServerErrorException("Um erro inesperado ocorreu ao salvar o planeta")
        }
        return planeta.id
    }

    override fun getAll(): List<Planeta> {
        val planetas: List<Planeta>
        try {
            planetas = repository.findAll().toList()
        } catch (e: Exception) {
            throw InternalServerErrorException("Um erro inesperado ocorreu ao recuperar a lista de planetas")
        }
        return planetas
    }

    override fun getById(id: UUID): Planeta {
        val planeta: Planeta
        try {
            planeta = repository.findById(id)
                    .orElseThrow { ResourceNotFoundException("Planeta não encontrado com id: ${id}") }
        } catch (rex: ResourceNotFoundException) {
            throw rex
        } catch (e: Exception) {
                throw InternalServerErrorException("Um erro inesperado ocorreu ao recuperar a lista de planetas")
            }
        return planeta
    }

}