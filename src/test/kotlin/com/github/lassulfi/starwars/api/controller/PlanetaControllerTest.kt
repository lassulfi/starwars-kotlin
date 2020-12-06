package com.github.lassulfi.starwars.api.controller

import com.github.lassulfi.starwars.api.exceptions.InternalServerErrorException
import com.github.lassulfi.starwars.api.exceptions.ResourceNotFoundException
import com.github.lassulfi.starwars.api.exceptions.UnprocessableEntityException
import com.github.lassulfi.starwars.api.model.Planeta
import com.github.lassulfi.starwars.api.services.PlanetaService
import org.junit.Assert.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
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
const val POST_URL = "/planetas"
const val PUT_URL = "/planetas/{id}"
const val PATCH_URL = "/planetas/{id}"
const val DELETE_URL = "/planetas/{id}"
const val CREATED_RESOURCE_URI = "/planetas/11111111-1111-1111-1111-111111111111"
const val GET_URL_SORTED = "/planetas?sort={sortBy}&order={orderBy}"
private val LISTA_PLANETAS_ORDENADO_NOME_ASCENDENTE = listOf(
        Planeta(UUID.fromString("11111111-1111-1111-1111-111111111111"), "Planeta A", "Clima 1", "Terreno 1"),
        Planeta(UUID.fromString("22222222-2222-2222-2222-222222222222"), "Planeta B", "Clima 2", "Terreno 2"),
        Planeta(UUID.fromString("33333333-3333-3333-3333-333333333333"), "Planeta C", "Clima 3", "Terreno 3"))
private val LISTA_PLANETAS_ORDENADO_TERRENO_ASCENDENTE = listOf(
        Planeta(UUID.fromString("11111111-1111-1111-1111-111111111111"), "Planeta 1", "Clima 1", "Terreno A"),
        Planeta(UUID.fromString("22222222-2222-2222-2222-222222222222"), "Planeta 3", "Clima 3", "Terreno B"),
        Planeta(UUID.fromString("33333333-3333-3333-3333-333333333333"), "Planeta 2", "Clima 2", "Terreno C"))
private val LISTA_PLANETAS_ORDENADO_CLIMA_ASCENDENTE = listOf(
        Planeta(UUID.fromString("11111111-1111-1111-1111-111111111111"), "Planeta 1", "Clima A", "Terreno 1"),
        Planeta(UUID.fromString("22222222-2222-2222-2222-222222222222"), "Planeta 2", "Clima B", "Terreno 2"),
        Planeta(UUID.fromString("33333333-3333-3333-3333-333333333333"), "Planeta 3", "Clima C", "Terreno 3"))
private val LISTA_PLANETAS_ORDENADO_NOME_DESCENDENTE = listOf(
        Planeta(UUID.fromString("11111111-1111-1111-1111-111111111111"), "Planeta C", "Clima 1", "Terreno 1"),
        Planeta(UUID.fromString("22222222-2222-2222-2222-222222222222"), "Planeta B", "Clima 2", "Terreno 2"),
        Planeta(UUID.fromString("33333333-3333-3333-3333-333333333333"), "Planeta A", "Clima 3", "Terreno 3"))
private val LISTA_PLANETAS_ORDENADO_TERRENO_DESCENDENTE = listOf(
        Planeta(UUID.fromString("11111111-1111-1111-1111-111111111111"), "Planeta 1", "Clima 1", "Terreno C"),
        Planeta(UUID.fromString("22222222-2222-2222-2222-222222222222"), "Planeta 3", "Clima 3", "Terreno B"),
        Planeta(UUID.fromString("33333333-3333-3333-3333-333333333333"), "Planeta 2", "Clima 2", "Terreno A"))
private val LISTA_PLANETAS_ORDENADO_CLIMA_DESCENDENTE = listOf(
        Planeta(UUID.fromString("11111111-1111-1111-1111-111111111111"), "Planeta 1", "Clima C", "Terreno 1"),
        Planeta(UUID.fromString("22222222-2222-2222-2222-222222222222"), "Planeta 2", "Clima B", "Terreno 2"),
        Planeta(UUID.fromString("33333333-3333-3333-3333-333333333333"), "Planeta 3", "Clima A", "Terreno 3"))

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PlanetaControllerTest: MvcControllerTestable<PlanetaController>() {

    private lateinit var id: UUID
    private lateinit var obj: Planeta
    private var sortBy: String = ""
    private var orderBy: String = ""
    @Mock private lateinit var service: PlanetaService
    @InjectMocks private lateinit var controller: PlanetaController
    private lateinit var response: MockHttpServletResponse

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
        fun `deve recuperar uma lista de planetas ordenada pelo nome em ordem ascendente`() {
            `dado que temos o parametro de ordenacao por nome`()
            `dado que temos o parametro de ordenacao por ordem ascendente`()
            `dado que o metodo getAll de service recupera uma lista de planetas ordenada pelo nome em ordem ascendente`()
            `quando chamados o metodo GET planetas com filtros de ordenacao`()
            `entao esperamos status code 200`()
            `entao esperamos uma lista de planetas ordenada por nome em ordem ascendente`()
        }

        @Test
        fun `deve recuperar uma lista de planetas ordenada pelo nome em ordem descendente`() {
            `dado que temos o parametro de ordenacao por nome`()
            `dado que temos o parametro de ordenacao por ordem descendente`()
            `dado que o metodo getAll de service recupera uma lista de planetas ordenada pelo nome em ordem descendente`()
            `quando chamados o metodo GET planetas com filtros de ordenacao`()
            `entao esperamos status code 200`()
            `entao esperamos uma lista de planetas ordenada por nome em ordem descendente`()
        }

        @Test
        fun `deve recuperar uma lista de planetas ordernada pelo terreno em ordem ascedente`() {
            `dado que temos o parametro de ordenacao pelo terreno`()
            `dado que temos o parametro de ordenacao por ordem ascendente`()
            `dado que o metodo getAll de service recupera uma lista de planetas ordenada pelo terreno em ordem ascendente`()
            `quando chamados o metodo GET planetas com filtros de ordenacao`()
            `entao esperamos status code 200`()
            `entao esperamos uma lista de planetas ordenada por terreno em ordem ascendente`()
        }

        @Test
        fun `deve recuperar uma lista de planetas ordernada pelo terreno em ordem descedente`() {
            `dado que temos o parametro de ordenacao pelo terreno`()
            `dado que temos o parametro de ordenacao por ordem descendente`()
            `dado que o metodo getAll de service recupera uma lista de planetas ordenada pelo terreno em ordem descendente`()
            `quando chamados o metodo GET planetas com filtros de ordenacao`()
            `entao esperamos status code 200`()
            `entao esperamos uma lista de planetas ordenada por terreno em ordem descendente`()
        }

        @Test
        fun `deve recuperar uma lista de planetas ordenada pelo clima em ordem ascendente`() {
            `dado que temos o parametro de ordenacao pelo clima`()
            `dado que temos o parametro de ordenacao por ordem ascendente`()
            `dado que o metodo getAll de service recupera uma lista de planetas ordenada pelo clima em ordem ascendente`()
            `quando chamados o metodo GET planetas com filtros de ordenacao`()
            `entao esperamos status code 200`()
            `entao esperamos uma lista de planetas ordenada pelo clima em ordem ascendente`()
        }

        @Test
        fun `deve recuperar uma lista de planetas ordenada pelo clima em ordem descendente`() {
            `dado que temos o parametro de ordenacao pelo clima`()
            `dado que temos o parametro de ordenacao por ordem descendente`()
            `dado que o metodo getAll de service recupera uma lista de planetas ordenada pelo clima em ordem descendente`()
            `quando chamados o metodo GET planetas com filtros de ordenacao`()
            `entao esperamos status code 200`()
            `entao esperamos uma lista de planetas ordenada pelo clima em ordem descendente`()
        }

        @Test
        fun `deve retornar um UnprocessableEntity para parametro sort invalido`() {
            `dado que temos um parametro de ordenacao invalido`()
            `dado que o metodo getAll de service retorna um UnprocessableEntityException`()
            `quando chamados o metodo GET planetas com filtros de ordenacao`()
            `entao esperamos o status code 422`()
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

    private fun `dado que temos um parametro de ordenacao invalido`() {
        this.sortBy = "invalido"
        this.orderBy = "asc"
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

    @Nested
    inner class ` POST planetas` {
        @Test
        fun `deve criar um planeta com sucesso`() {
            `dado que temos um planeta valido`()
            `dado que o metodo create de service retorna um id valido`()
            `quando chamamos a operacao POST planetas`()
            `entao esperamos status code 201`()
            `entao esperamos que o header location contenha a URI do recurso`()
        }
        @Test
        fun `deve retornar um Internal Server Error`() {
            `dado que temos um planeta valido`()
            `dado que o metodo create de service lanca uma InternalServerErrorException`()
            `quando chamamos a operacao POST planetas`()
            `entao esperamos status code 500`()
        }
        @Test
        fun `deve retornar um Internal Server Error devido a um erro generico`() {
            `dado que temos um planeta valido`()
            `dado que o metodo create lanca um erro generico`()
            `quando chamamos a operacao POST planetas`()
            `entao esperamos status code 500`()
        }
    }

    @Nested
    inner class `PUT planeta por id` {
        @Test
        fun `deve atualizar um planeta por id com sucesso`() {
            `dado que temos um identificador`()
            `dado que temos um planeta valido`()
            `dado que o metodo update de service retorna com sucesso`()
            `quando chamamos a operacao PUT planetas por id`()
            `entao esperamos status code 204`()
        }
        @Test
        fun `deve lancar uma Not Found`() {
            `dado que temos um identificador`()
            `dado que temos um planeta valido`()
            `dado que o metodo update de service lanca uma ResourceNotFoundException`()
            `quando chamamos a operacao PUT planetas por id`()
            `entao esperamos status code 404`()
        }
        @Test
        fun `deve lancar uma Internal Server Error`() {
            `dado que temos um identificador`()
            `dado que temos um planeta valido`()
            `dado que o metodo update de service lanca uma InternalServerErrorException`()
            `quando chamamos a operacao PUT planetas por id`()
            `entao esperamos status code 500`()
        }
        @Test
        fun `deve lancar um Internal Server Error devido a um erro generico`() {
            `dado que temos um identificador`()
            `dado que temos um planeta valido`()
            `dado que o metodo update lanca um erro generico`()
            `quando chamamos a operacao PUT planetas por id`()
            `entao esperamos status code 500`()
        }
    }

    @Nested
    inner class `PATCH planeta por id` {
        @Test
        fun `deve atualizar um planeta por id com sucesso`() {
            `dado que temos um identificador`()
            `dado que temos um planeta valido`()
            `dado que o metodo partialUpdate de service executa com sucesso`()
            `quando chamamos a operacao PATCH planetas por id`()
            `entao esperamos status code 204`()
        }
        @Test
        fun `deve lancar uma Not Found`() {
            `dado que temos um identificador`()
            `dado que temos um planeta valido`()
            `dado que o metodo partialUpdate de service lance uma ResourceNotFoundException`()
            `quando chamamos a operacao PATCH planetas por id`()
            `entao esperamos status code 404`()
        }
        @Test
        fun `deve lancar uma Internal Server Error`() {
            `dado que temos um identificador`()
            `dado que temos um planeta valido`()
            `dado que o metodo partialUpdate de service lance uma InternalServerErrorException`()
            `quando chamamos a operacao PATCH planetas por id`()
            `entao esperamos status code 500`()
        }
        @Test
        fun `deve lancar uma Internal Server Error devido a um erro generico`() {
            `dado que temos um identificador`()
            `dado que temos um planeta valido`()
            `dado que o metodo partialUpdate de service lanca um erro generico`()
            `quando chamamos a operacao PATCH planetas por id`()
            `entao esperamos status code 500`()
        }
    }

    @Nested
    inner class `DELETE planeta por id` {
        @Test
        fun `deve excluir um planeta por id com sucesso`() {
            `dado que temos um identificador`()
            `dado que o metodo deleteById de service executa com sucesso`()
            `quando chamamos o metodo DELETE planeta por id`()
            `entao esperamos status code 204`()
        }
        @Test
        fun `deve lancar uma Not Found`() {
            `dado que temos um identificador`()
            `dado que o metodo deleteById de service lanca uma ResourceNotFoundException`()
            `quando chamamos o metodo DELETE planeta por id`()
            `entao esperamos status code 404`()
        }
        @Test
        fun `deve lancar uma Internal Server Error`() {
            `dado que temos um identificador`()
            `dado que o metodo deleteById de service lanca uma InternalServerErrorException`()
            `quando chamamos o metodo DELETE planeta por id`()
            `entao esperamos status code 500`()
        }
        @Test
        fun `deve lancar uma Internal Server Error devido a um erro generico`(){
            `dado que temos um identificador`()
            `dado que o metodo deleteById de service lanca um erro generico`()
            `quando chamamos o metodo DELETE planeta por id`()
            `entao esperamos status code 500`()
        }
    }

    private fun `dado que o metodo partialUpdate de service executa com sucesso`() {
        doNothing().`when`(service).partialUpdate(this.obj)
    }

    private fun `dado que temos um identificador`() {
        this.id = ID_DEFAULT
    }

    private fun `dado que o metodo getAll de service recupera uma lista de planetas`() {
        doReturn(LISTA_PLANETAS).`when`(service).getAll("")
    }

    private fun `dado que o metodo getAll de service lanca um InternalServerError`() {
        doThrow(InternalServerErrorException("Internal Error")).`when`(service).getAll("")
    }

    private fun `dado que o metodo getAll de service lanca um erro generico`() {
        doThrow(RuntimeException("Generic Error")).`when`(service).getAll("")
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

    private fun `dado que temos um planeta valido`() {
        this.obj = PLANETA
    }

    private fun `dado que o metodo create de service retorna um id valido`() {
        doReturn(ID_DEFAULT).`when`(service).create(this.obj)
    }

    private fun `dado que o metodo create de service lanca uma InternalServerErrorException`() {
        doThrow(InternalServerErrorException("Internal Error")).`when`(service).create(this.obj)
    }


    private fun `dado que o metodo create lanca um erro generico`() {
        doThrow(RuntimeException("Generic Error")).`when`(service).create(this.obj)
    }


    private fun `dado que o metodo update de service retorna com sucesso`() {
        doNothing().`when`(service).update(this.obj)
    }

    private fun `dado que o metodo update de service lanca uma ResourceNotFoundException`() {
        doThrow(ResourceNotFoundException("Not found")).`when`(service).update(this.obj)
    }

    private fun `dado que o metodo update de service lanca uma InternalServerErrorException`() {
        doThrow(InternalServerErrorException("Internal Error")).`when`(service).update(this.obj)
    }

    private fun `dado que o metodo update lanca um erro generico`() {
        doThrow(RuntimeException("Generic Error")).`when`(service).update(this.obj)
    }

    private fun `dado que o metodo partialUpdate de service lance uma ResourceNotFoundException`() {
        doThrow(ResourceNotFoundException("Not Found")).`when`(service).partialUpdate(this.obj)
    }

    private fun `dado que o metodo partialUpdate de service lance uma InternalServerErrorException`() {
        doThrow(InternalServerErrorException("Internal Error")).`when`(service).partialUpdate(this.obj)
    }

    private fun `dado que o metodo partialUpdate de service lanca um erro generico`() {
        doThrow(RuntimeException("Generic Error")).`when`(service).partialUpdate(this.obj)
    }

    private fun `dado que o metodo deleteById de service executa com sucesso`() {
        doNothing().`when`(service).deleteById(this.id)
    }

    private fun `dado que o metodo deleteById de service lanca uma ResourceNotFoundException`() {
        doThrow(ResourceNotFoundException("Not Found")).`when`(service).deleteById(this.id)
    }

    private fun `dado que o metodo deleteById de service lanca uma InternalServerErrorException`() {
        doThrow(InternalServerErrorException("Internal Error")).`when`(service).deleteById(this.id)
    }

    private fun `dado que o metodo deleteById de service lanca um erro generico`() {
        doThrow(RuntimeException("Generic Error")).`when`(service).deleteById(this.id)
    }

    private fun `dado que temos o parametro de ordenacao por nome`() {
        this.sortBy = "nome"
    }

    private fun `dado que temos o parametro de ordenacao por ordem ascendente`() {
        this.orderBy = "asc"
    }

    private fun `dado que o metodo getAll de service recupera uma lista de planetas ordenada pelo nome em ordem ascendente`() {
        doReturn(LISTA_PLANETAS_ORDENADO_NOME_ASCENDENTE).`when`(service).getAll(sortBy, orderBy)
    }

    private fun `dado que temos o parametro de ordenacao pelo terreno`() {
        this.sortBy = "clima"
    }

    private fun `dado que o metodo getAll de service recupera uma lista de planetas ordenada pelo terreno em ordem ascendente`() {
        doReturn(LISTA_PLANETAS_ORDENADO_TERRENO_ASCENDENTE).`when`(service).getAll(sortBy, orderBy)
    }

    private fun `dado que temos o parametro de ordenacao pelo clima`() {
        this.sortBy = "clima"
    }

    private fun `dado que o metodo getAll de service recupera uma lista de planetas ordenada pelo clima em ordem ascendente`() {
        doReturn(LISTA_PLANETAS_ORDENADO_CLIMA_ASCENDENTE).`when`(service).getAll(sortBy, orderBy)
    }

    private fun `dado que temos o parametro de ordenacao por ordem descendente`() {
        this.orderBy = "desc"
    }

    private fun `dado que o metodo getAll de service recupera uma lista de planetas ordenada pelo nome em ordem descendente`() {
        doReturn(LISTA_PLANETAS_ORDENADO_NOME_DESCENDENTE).`when`(service).getAll(sortBy, orderBy)
    }

    private fun `dado que o metodo getAll de service recupera uma lista de planetas ordenada pelo terreno em ordem descendente`() {
        doReturn(LISTA_PLANETAS_ORDENADO_TERRENO_DESCENDENTE).`when`(service).getAll(sortBy, orderBy)
    }

    private fun `dado que o metodo getAll de service recupera uma lista de planetas ordenada pelo clima em ordem descendente`() {
        doReturn(LISTA_PLANETAS_ORDENADO_CLIMA_DESCENDENTE).`when`(service).getAll(sortBy, orderBy)
    }

    private fun `dado que o metodo getAll de service retorna um UnprocessableEntityException`() {
        doThrow(UnprocessableEntityException("Entidade improcessável")).`when`(service).getAll(sortBy)
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

    private fun `quando chamamos a operacao POST planetas`() {
        this.response = performCall(MockMvcRequestBuilders
                .post(POST_URL)
                .content(toJson(this.obj))
                .contentType(MediaType.APPLICATION_JSON))
    }

    private fun `quando chamamos a operacao PUT planetas por id`() {
        this.response = performCall(MockMvcRequestBuilders
                .put(PUT_URL, this.id)
                .content(toJson(this.obj))
                .contentType(MediaType.APPLICATION_JSON))
    }

    private fun `quando chamamos a operacao PATCH planetas por id`() {
        this.response = performCall(MockMvcRequestBuilders
                .patch(PATCH_URL, this.id)
                .content(toJson(this.obj))
                .contentType(MediaType.APPLICATION_JSON))
    }

    private fun `quando chamamos o metodo DELETE planeta por id`() {
        this.response = performCall(MockMvcRequestBuilders
                .delete(DELETE_URL, this.id))
    }

    private fun `quando chamados o metodo GET planetas com filtros de ordenacao`() {
        this.response = performCall(MockMvcRequestBuilders
                .get(GET_URL_SORTED, this.sortBy, this.orderBy)
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

    private fun `entao esperamos status code 201`() {
        assertEquals(HttpStatus.CREATED.value(), this.response.status)
    }

    private fun `entao esperamos que o header location contenha a URI do recurso`() {
        val location = this.response.getHeader("location")
        assertEquals(CREATED_RESOURCE_URI, location)
    }

    private fun `entao esperamos status code 204`() {
        assertEquals(HttpStatus.NO_CONTENT.value(), this.response.status)
    }

    private fun `entao esperamos uma lista de planetas ordenada por nome em ordem ascendente`() {
        assertEquals(super.toJson(LISTA_PLANETAS_ORDENADO_NOME_ASCENDENTE),
                String(this.response.contentAsByteArray))
    }

    private fun `entao esperamos uma lista de planetas ordenada por terreno em ordem ascendente`() {
        assertEquals(super.toJson(LISTA_PLANETAS_ORDENADO_TERRENO_ASCENDENTE),
                String(this.response.contentAsByteArray))
    }

    private fun `entao esperamos uma lista de planetas ordenada pelo clima em ordem ascendente`() {
        assertEquals(super.toJson(LISTA_PLANETAS_ORDENADO_CLIMA_ASCENDENTE),
                String(this.response.contentAsByteArray))
    }

    private fun `entao esperamos uma lista de planetas ordenada por nome em ordem descendente`() {
        assertEquals(super.toJson(LISTA_PLANETAS_ORDENADO_NOME_DESCENDENTE),
                String(this.response.contentAsByteArray))
    }


    private fun `entao esperamos uma lista de planetas ordenada por terreno em ordem descendente`() {
        assertEquals(super.toJson(LISTA_PLANETAS_ORDENADO_TERRENO_DESCENDENTE),
                String(this.response.contentAsByteArray))
    }

    private fun `entao esperamos uma lista de planetas ordenada pelo clima em ordem descendente`() {
        assertEquals(super.toJson(LISTA_PLANETAS_ORDENADO_CLIMA_DESCENDENTE),
                String(this.response.contentAsByteArray))
    }

    private fun `entao esperamos o status code 422`() {
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY.value(), this.response.status)
    }
}
