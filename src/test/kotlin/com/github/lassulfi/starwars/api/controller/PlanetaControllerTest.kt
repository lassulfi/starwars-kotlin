package com.github.lassulfi.starwars.api.controller

import com.github.lassulfi.starwars.api.exceptions.InternalServerErrorException
import com.github.lassulfi.starwars.api.exceptions.ResourceNotFoundException
import com.github.lassulfi.starwars.api.model.Planeta
import com.github.lassulfi.starwars.api.services.PlanetaService
import org.junit.Assert.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.doReturn
import org.mockito.Mockito.doThrow
import org.mockito.MockitoAnnotations
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.mock.web.MockHttpServletResponse
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import java.util.*

//CONSTANTS
private val ID_DEFAULT = UUID.fromString("11111111-1111-1111-1111-111111111111")
private val LISTA_PLANETAS = listOf(
        Planeta(UUID.fromString("11111111-1111-1111-1111-111111111111"), "Planeta 1", "Clima 1", "Terreno 1"),
        Planeta(UUID.fromString("22222222-2222-2222-2222-222222222222"), "Planeta 2", "Clima 2", "Terreno 2"),
        Planeta(UUID.fromString("33333333-3333-3333-3333-333333333333"), "Planeta 3", "Clima 3", "Terreno 3"))
private val PLANETA = Planeta(UUID.fromString("11111111-1111-1111-1111-111111111111"), "Planeta 1", "Clima 1", "Terreno 1")
const val GET_ALL_URL = "/planetas"
const val GET_BY_ID_URL = "/planetas/{id}"

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PlanetaControllerTest: MvcControllerTestable<PlanetaController>() {

    lateinit var id: UUID
    lateinit var obj: Planeta
    @Mock lateinit var service: PlanetaService
    @InjectMocks lateinit var controller: PlanetaController
    lateinit var response: MockHttpServletResponse

    @BeforeAll
    fun setup() {
        MockitoAnnotations.initMocks(this)
        super.initializeMvc(controller)
    }

    @Nested
    inner class `GET planetas`{

        @Test
        fun `deve recuperar todos os planetas com sucesso`() {
            `dado que o metodo getAll de service recupera uma lista de planetas`()
            `quando chamamos a operação GET planetas`()
            `entao esperamos status code 200`()
            `entao esperamos uma lista de de planetas`()
        }

        @Test
        fun `deve retornar um InternalServerError`() {
            `dado que o metodo getAll de service lanca um InternalServerError`()
            `quando chamamos a operação GET planetas`()
            `entao esperamos status code 500`()
        }

        @Test
        fun `deve retornar um InternalServerError devido a um erro generico`() {
            `dado que o metodo getAll de service lanca um erro generico`()
            `quando chamamos a operação GET planetas`()
            `entao esperamos status code 500`()
        }
    }

    @Nested
    inner class `GET planeta por id` {

        @Test
        fun `deve recuperar um planeta com sucesso`() {
            `dado que temos um identificador`()
            `dado que o metodo getById de service retorna um planeta`()
            `quando chamamos a operacao GET planetas por id`()
            `entao esperamos status code 200`()
            `entao esperamos um planeta`()
        }

        @Test
        fun `deve lancar um erro de nao encontrado`() {
            `dado que temos um identificador`()
            `dado que o metodo getById de service lanca uma ResourceNotFoundException`()
            `quando chamamos a operacao GET planetas por id`()
            `entao esperamos status code 404`()
        }

        @Test
        fun `deve retornar um InternalServerError`() {
            `dado que temos um identificador`()
            `dado que o metodo getById de service lanca uma InternalServerErrorException`()
            `quando chamamos a operacao GET planetas por id`()
            `entao esperamos status code 500`()
        }

        @Test
        fun `deve retornar um InternalServerError devido a um erro generico`() {
            `dado que temos um identificador`()
            `dado que o metodo getById da service lanca um erro generico`()
            `quando chamamos a operacao GET planetas por id`()
            `entao esperamos status code 500`()
        }
    }

    private fun `dado que temos um identificador`() {
        this.id = ID_DEFAULT
    }

    private fun `dado que o metodo getAll de service recupera uma lista de planetas`() {
        doReturn(LISTA_PLANETAS).`when`(service).getAll()
    }

    private fun `dado que o metodo getAll de service lanca um InternalServerError`() {
        doThrow(InternalServerErrorException("Internal Error")).`when`(service).getAll()
    }

    private fun `dado que o metodo getAll de service lanca um erro generico`() {
        doThrow(RuntimeException("Generic Error")).`when`(service).getAll()
    }

    private fun `dado que o metodo getById de service retorna um planeta`() {
        doReturn(PLANETA).`when`(service).getById(id)
    }

    private fun `dado que o metodo getById de service lanca uma ResourceNotFoundException`() {
        doThrow(ResourceNotFoundException("Recurso nao encontrado")).`when`(service).getById(id)
    }

    private fun `dado que o metodo getById de service lanca uma InternalServerErrorException`() {
        doThrow(InternalServerErrorException("Internal Error")).`when`(service).getById(this.id)
    }

    private fun `dado que o metodo getById da service lanca um erro generico`() {
        doThrow(RuntimeException("Generic Error")).`when`(service).getById(this.id)
    }

    private fun `quando chamamos a operação GET planetas`() {
        this.response = performCall(MockMvcRequestBuilders
                .get(GET_ALL_URL)
                .accept(MediaType.APPLICATION_JSON))
    }

    private fun `quando chamamos a operacao GET planetas por id`() {
        this.response = performCall(MockMvcRequestBuilders
                .get(GET_BY_ID_URL, this.id)
                .accept(MediaType.APPLICATION_JSON))
    }

    private fun `entao esperamos status code 200`() {
        assertEquals(HttpStatus.OK.value(), this.response.status)
    }

    private fun `entao esperamos uma lista de de planetas`() {
        assertEquals(super.toJson(LISTA_PLANETAS), String(this.response.contentAsByteArray))
    }

    private fun `entao esperamos status code 500`() {
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), this.response.status)
    }

    private fun `entao esperamos um planeta`() {
        assertEquals(super.toJson(PLANETA), String(this.response.contentAsByteArray))
    }

    private fun `entao esperamos status code 404`() {
        assertEquals(HttpStatus.NOT_FOUND.value(), this.response.status)
    }
}
