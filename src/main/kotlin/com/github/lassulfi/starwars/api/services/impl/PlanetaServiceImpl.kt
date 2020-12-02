package com.github.lassulfi.starwars.api.services.impl

import com.github.lassulfi.starwars.api.exceptions.InternalServerErrorException
import com.github.lassulfi.starwars.api.exceptions.ResourceNotFoundException
import com.github.lassulfi.starwars.api.model.Planeta
import com.github.lassulfi.starwars.api.repository.PlanetaRepository
import com.github.lassulfi.starwars.api.services.PlanetaService
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import java.util.*
@Service
class PlanetaServiceImpl(val repository: PlanetaRepository): PlanetaService {

    @CacheEvict("planetas", allEntries = true)
    override fun create(planeta: Planeta): UUID {
        try {
            repository.save(planeta)
        } catch (e: Exception) {
            throw InternalServerErrorException("Um erro inesperado ocorreu ao salvar o planeta")
        }
        return planeta.id
    }

    @Cacheable("planetas")
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
                throw InternalServerErrorException("Um erro inesperado ocorreu ao recuperar o planeta por id")
            }
        return planeta
    }

    @CacheEvict("planetas", allEntries = true)
    override fun deleteById(id: UUID) {
        try {
            if (!repository.existsById(id))
                throw ResourceNotFoundException("Planeta não encontrado com id: ${id}")
            else
                repository.deleteById(id)
        } catch (rex: ResourceNotFoundException) {
          throw rex
        } catch (e: Exception) {
            throw InternalServerErrorException("Um erro inesperado ocorreu ao excluir um planeta por id")
        }
    }

    @CacheEvict("planetas", allEntries = true)
    override fun update(planeta: Planeta) {
        try {
            if(!repository.existsById(planeta.id))
                throw ResourceNotFoundException("Planeta não encontrado com id: ${planeta.id}")
            else
                repository.save(planeta)
        } catch (rex: ResourceNotFoundException) {
            throw rex
        } catch (e: Exception) {
            throw InternalServerErrorException("Um erro inesperado ocorreu ao excluir um planeta por id")
        }
    }

    @CacheEvict("planetas", allEntries = true)
    override fun partialUpdate(planeta: Planeta) {
        try {
            val entidade = getById(planeta.id)
            entidade.fillWith(planeta)
            repository.save(entidade)
        } catch (rex: ResourceNotFoundException) {
            throw rex
        }catch (e: Exception) {
            throw InternalServerErrorException("Um erro inesperado ocorreu ao atualizar um planeta parcialmente")
        }
    }
}