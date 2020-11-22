package com.github.lassulfi.starwars.api.services

import com.github.lassulfi.starwars.api.exceptions.InternalServerErrorException
import com.github.lassulfi.starwars.api.model.Planeta
import com.github.lassulfi.starwars.api.repository.PlanetaRepository
import com.github.lassulfi.starwars.api.services.impl.PlanetaServiceImpl
import org.hamcrest.CoreMatchers.equalTo
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito.*
import org.springframework.dao.RecoverableDataAccessException
import java.util.*

// CONSTANTS
const val NOME_PLANETA = "Planeta"
const val CLIMA_PLANETA = "Clima"
const val TERRENO_PLANETA = "Terreno"
const val ID = "11111111-1111-1111-1111-111111111111"
val LISTA_PLANETAS = listOf(
        Planeta(UUID.fromString("11111111-1111-1111-1111-111111111111"), "Planeta 1", "Clima 1", "Terreno 1"),
        Planeta(UUID.fromString("22222222-2222-2222-2222-222222222222"), "Planeta 2", "Clima 2", "Terreno 2"),
        Planeta(UUID.fromString("33333333-3333-3333-3333-333333333333"), "Planeta 3", "Clima 3", "Terreno 3"))

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PlanetaServiceTest {

    val repository = mock(PlanetaRepository::class.java)
    val service = PlanetaServiceImpl(repository)
    lateinit var planeta: Planeta
    lateinit var id: UUID
    lateinit var planetas: List<Planeta>

    @Nested
    inner class `Criar Planeta` {
        @Test
        fun `deve criar um planeta`() {
            `dado que temos um planeta`()
            `dado que planetaRepository salva com sucesso`()
            `quando chamamos o metodo salvar`()
            `entao experamos que o planeta seja criado com sucesso`()
        }

        @Test()
        fun `deve lancar uma InternalServerErrorException`() {
            `dado que temos um planeta`()
            `dado que planetaRepository lanca excecao ao salvar`()
            `entao esperamos que seja lancada uma excecao ao salvar`()
        }
    }

    @Nested
    inner class `Recuperar Todos os Planetas` {

        @Test
        fun `deve recuperar uma lista de planetas com sucesso`() {
            `dado que repository recupera uma lista de planetas com sucesso`()
            `quando chamamos o metodo recuperar todos`()
            `entao esperamos uma lista contento planetas`()
        }

        @Test
        fun `deve lancar um InternalServerErrorException ao recuperar a lista`() {
            `dado que repository lanca uma excecao ao recuperar a lista`()
            `entao experamos que seja lancada uma excecao ao recuperar a lista de planetas`()
        }
    }

    fun `dado que temos um planeta`() {
        planeta = Planeta(UUID.fromString(ID), NOME_PLANETA, CLIMA_PLANETA, TERRENO_PLANETA)
    }

    fun `dado que planetaRepository salva com sucesso`() {
        `when`(repository.save(planeta)).thenReturn(Planeta(UUID.fromString(ID), NOME_PLANETA, CLIMA_PLANETA, TERRENO_PLANETA))
    }

    fun `dado que planetaRepository lanca excecao ao salvar`() {
        `when`(repository.save(planeta))
                .thenThrow(RecoverableDataAccessException("Banco de dados indisponivel"))
    }

    fun `dado que repository recupera uma lista de planetas com sucesso`() {
        doReturn(LISTA_PLANETAS).`when`(repository).findAll()
    }

    fun `dado que repository lanca uma excecao ao recuperar a lista`() {
        `when`(repository.findAll()).thenThrow(RecoverableDataAccessException("Banco de dados indisponivel"))
    }

    fun `quando chamamos o metodo salvar`() {
        id = service.create(planeta)
    }

    fun `quando chamamos o metodo recuperar todos`() {
        planetas = service.getAll()
    }

    fun `entao experamos que o planeta seja criado com sucesso`() {
        assertThat(id.toString(), equalTo(ID))
        verify(repository, times(1)).save(planeta)
    }

    fun `entao esperamos que seja lancada uma excecao ao salvar`() {
        assertThrows<InternalServerErrorException> { service.create(planeta) }
    }

    fun `entao esperamos uma lista contento planetas`() {
        assertEquals(LISTA_PLANETAS, this.planetas)
    }

    fun `entao experamos que seja lancada uma excecao ao recuperar a lista de planetas`() {
        assertThrows<InternalServerErrorException> { service.getAll() }
    }
}