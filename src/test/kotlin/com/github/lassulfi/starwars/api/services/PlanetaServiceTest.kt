package com.github.lassulfi.starwars.api.services

import com.github.lassulfi.starwars.api.exceptions.InternalServerErrorException
import com.github.lassulfi.starwars.api.model.Planeta
import com.github.lassulfi.starwars.api.repository.PlanetaRepository
import com.github.lassulfi.starwars.api.services.impl.PlanetaServiceImpl
import org.hamcrest.CoreMatchers.equalTo
import org.junit.Assert.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito.*
import java.util.*

// CONSTANTS
const val NOME_PLANETA = "Planeta"
const val CLIMA_PLANETA = "Clima"
const val TERRENO_PLANETA = "Terreno"
const val ID = "11111111-1111-1111-1111-111111111111"

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PlanetaServiceTest {

    val repository = mock(PlanetaRepository::class.java)
    val service = PlanetaServiceImpl(repository)
    lateinit var planeta: Planeta
    lateinit var entidade: Planeta
    lateinit var id: UUID

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

    fun `dado que temos um planeta`() {
        planeta = Planeta(UUID.fromString(ID), NOME_PLANETA, CLIMA_PLANETA, TERRENO_PLANETA)
    }

    fun `dado que planetaRepository salva com sucesso`() {
        `when`(repository.save(planeta)).thenReturn(Planeta(UUID.fromString(ID), NOME_PLANETA, CLIMA_PLANETA, TERRENO_PLANETA))
    }

    fun `dado que planetaRepository lanca excecao ao salvar`() {
        `when`(repository.save(planeta))
                .thenThrow(InternalServerErrorException("Ocorreu um erro inesperado ao salvar um planeta"))
    }

    fun `quando chamamos o metodo salvar`() {
        id = service.create(planeta)
    }

    fun `entao experamos que o planeta seja criado com sucesso`() {
        assertThat(id.toString(), equalTo(ID))
        verify(repository, times(1)).save(planeta)
    }

    fun `entao esperamos que seja lancada uma excecao ao salvar`() {
        assertThrows<InternalServerErrorException> { service.create(planeta) }
    }
}