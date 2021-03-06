package com.github.lassulfi.starwars.api.services.impl

import com.github.lassulfi.starwars.api.exceptions.InternalServerErrorException
import com.github.lassulfi.starwars.api.exceptions.ResourceNotFoundException
import com.github.lassulfi.starwars.api.exceptions.UnprocessableEntityException
import com.github.lassulfi.starwars.api.model.Planeta
import com.github.lassulfi.starwars.api.repository.PlanetaRepository
import com.github.lassulfi.starwars.api.services.PlanetaService
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
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
    override fun getPageable(pageable: Pageable): Page<Planeta> {
        val planetas: Page<Planeta>
        try {
            planetas = repository.findAll(pageable)
        } catch (e: Exception) {
            throw InternalServerErrorException("Um erro inesperado ocorreu ao recuperar o planeta")
        }
        return planetas
    }

    @Cacheable("planetas")
    override fun getAll(sort:String?, order: String?): List<Planeta> {
        val planetas: List<Planeta>
        try {
            planetas = if (sort == null || sort.isEmpty()) repository.findAll().toList()
            else recuperarListaOrdenada(repository, sort, order)
        } catch (uex: UnprocessableEntityException) {
          throw uex
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

    private fun recuperarListaOrdenada(repository: PlanetaRepository, sort: String, order: String?): List<Planeta> {
        val validSortParams = listOf("nome", "clima", "terreno")
        if (validSortParams.contains(sort)) {
            return if (order != null && order.equals("asc", ignoreCase = true))
                repository.findAll(Sort.by(sort).ascending()).toList()
            else repository.findAll(Sort.by(sort).descending()).toList()
        } else {
            throw UnprocessableEntityException("Unprocessable Entity")
        }
    }
}