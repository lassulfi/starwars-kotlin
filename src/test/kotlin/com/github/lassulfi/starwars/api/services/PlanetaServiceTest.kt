package com.github.lassulfi.starwars.api.services

import com.github.lassulfi.starwars.api.exceptions.InternalServerErrorException
import com.github.lassulfi.starwars.api.exceptions.ResourceNotFoundException
import com.github.lassulfi.starwars.api.model.Planeta
import com.github.lassulfi.starwars.api.repository.PlanetaRepository
import com.github.lassulfi.starwars.api.services.impl.PlanetaServiceImpl
import org.junit.Assert.assertEquals
import org.junit.jupiter.api.*
import org.mockito.Mockito.*
import org.springframework.dao.RecoverableDataAccessException
import java.util.*

// CONSTANTS
val ID = UUID.fromString("11111111-1111-1111-1111-111111111111")
val PLANETA = Planeta(UUID.fromString("11111111-1111-1111-1111-111111111111"), "Planeta 1", "Clima 1", "Terreno 1")
val LISTA_PLANETAS = listOf(
        Planeta(UUID.fromString("11111111-1111-1111-1111-111111111111"), "Planeta 1", "Clima 1", "Terreno 1"),
        Planeta(UUID.fromString("22222222-2222-2222-2222-222222222222"), "Planeta 2", "Clima 2", "Terreno 2"),
        Planeta(UUID.fromString("33333333-3333-3333-3333-333333333333"), "Planeta 3", "Clima 3", "Terreno 3"))

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PlanetaServiceTest {

    lateinit var planeta: Planeta
    lateinit var id: UUID
    lateinit var planetas: List<Planeta>
    lateinit var service: PlanetaService
    private lateinit var repository: PlanetaRepository

    @BeforeEach
    internal fun setup() {
        repository = mock(PlanetaRepository::class.java)
        service = PlanetaServiceImpl(repository)
    }

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

    @Nested
    inner class `Recuperar planeta por id` {
        @Test
        fun `deve recuperar um planeta por id com sucesso`() {
            `dado que temos um id valido`()
            `dado que respository recupera uma instancia valida por um id`()
            `quando chamamos o metodo recuperar por id`()
            `entao experamos o id da instancia seja igual ao da requisicao`()
        }

        @Test
        fun `deve lancar uma InternalServerErrorException ao recupera uma instancia por id`() {
            `dado que temos um id valido`()
            `dado que repository lanca uma excecao`()
            `entao esperamos que seja lancada uma InternalServerErrorException ao recuperar um planeta por id`()
        }

        @Test
        fun `deve lancar uma ResourceNotFoundException ao recuperar uma instancia por id`() {
            `dado que temos um id valido`()
            `dado que respository retorna um Optional Vazio ao recuperar uma instancia por id`()
            `entao esperamos que seja lancada uma ResourceNotFoundException ao recuperar um planeta por id`()
        }
    }

    @Nested
    inner class `Excluir por Id` {

        @Test
        fun `deve excluir um planeta por id com sucesso`() {
            `dado que temos um id valido`()
            `dado que a entidade identifica pelo id existe no respositorio`()
            `dado que a exclusao por id ocorre com sucesso`()
            `quando chamamos o metodo excluir por id`()
            `entao esperamos que a instancia seja excluida`()
        }

        @Test
        fun `deve lancar InternalServerErrorException ao excluir planeta por id`() {
            `dado que temos um id valido`()
            `dado que a entidade identifica pelo id existe no respositorio`()
            `dado que a exclusao por id lanca uma excecao`()
            `entao esperamos uma InternalServerErrorException ao excluir um planeta por id`()
        }

        @Test
        fun `deve lancar ResourceNotException ao excluir planeta por id`() {
            `dado que temos um id valido`()
            `dado que a entidade identificada pelo id nao existe no repositorio`()
            `entao esperamos uma ResourceNotFoundException ao excluir um planeta por id`()
            `entao esperamos que o planeta nao seja excluido`()
        }
    }

    private fun `dado que temos um id valido`() {
        id = ID
    }

    fun `dado que temos um planeta`() {
        planeta = PLANETA
    }

    fun `dado que planetaRepository salva com sucesso`() {
        `when`(repository.save(planeta)).thenReturn(PLANETA)
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

    private fun `dado que respository recupera uma instancia valida por um id`() {
        doReturn(Optional.of(PLANETA)).`when`(repository).findById(id)
    }

    private fun `dado que repository lanca uma excecao`() {
        doThrow(RecoverableDataAccessException("Banco de dados indisponivel")).`when`(repository).findById(id)
    }

    private fun `dado que respository retorna um Optional Vazio ao recuperar uma instancia por id`() {
        `when`(repository.findById(id)).thenReturn(Optional.empty())
    }

    private fun `dado que a entidade identifica pelo id existe no respositorio`() {
        doReturn(true).`when`(repository).existsById(id)
    }

    private fun `dado que a exclusao por id ocorre com sucesso`() {
        doNothing().`when`(repository).deleteById(id)
    }

    private fun `dado que a exclusao por id lanca uma excecao`() {
        doThrow(RecoverableDataAccessException("Banco de dados indisponivel")).`when`(repository).deleteById(id)
    }

    private fun `dado que a entidade identificada pelo id nao existe no repositorio`() {
        doReturn(false).`when`(repository).existsById(id)
    }

    fun `quando chamamos o metodo salvar`() {
        id = service.create(planeta)
    }

    fun `quando chamamos o metodo recuperar todos`() {
        planetas = service.getAll()
    }

    private fun `quando chamamos o metodo recuperar por id`() {
        planeta = service.getById(id)
    }

    private fun `quando chamamos o metodo excluir por id`() {
        service.deleteById(id)
    }

    fun `entao experamos que o planeta seja criado com sucesso`() {
        assertEquals(ID, planeta.id)
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

    private fun `entao experamos o id da instancia seja igual ao da requisicao`() {
        assertEquals(ID, this.planeta.id)
    }

    private fun `entao esperamos que seja lancada uma InternalServerErrorException ao recuperar um planeta por id`() {
        assertThrows<InternalServerErrorException> { service.getById(id) }
    }

    private fun `entao esperamos que seja lancada uma ResourceNotFoundException ao recuperar um planeta por id`() {
        assertThrows<ResourceNotFoundException> { service.getById(id) }
    }

    private fun `entao esperamos que a instancia seja excluida`() {
        verify(repository, times(1)).deleteById(any(UUID::class.java))
    }

    private fun `entao esperamos uma InternalServerErrorException ao excluir um planeta por id`() {
        assertThrows<InternalServerErrorException> { service.deleteById(id) }
    }

    private fun `entao esperamos que o planeta nao seja excluido`() {
        verify(repository, times(0)).deleteById(any(UUID::class.java))
    }

    private fun `entao esperamos uma ResourceNotFoundException ao excluir um planeta por id`() {
        assertThrows<ResourceNotFoundException> { service.deleteById(id) }
    }
}
