package com.github.lassulfi.starwars.api.services

import com.github.lassulfi.starwars.api.exceptions.InternalServerErrorException
import com.github.lassulfi.starwars.api.exceptions.ResourceNotFoundException
import com.github.lassulfi.starwars.api.exceptions.UnprocessableEntityException
import com.github.lassulfi.starwars.api.model.Planeta
import com.github.lassulfi.starwars.api.repository.PlanetaRepository
import com.github.lassulfi.starwars.api.services.impl.PlanetaServiceImpl
import org.junit.Assert.assertEquals
import org.junit.jupiter.api.*
import org.mockito.Mockito.*
import org.springframework.dao.RecoverableDataAccessException
import org.springframework.data.domain.Sort
import java.util.*

// CONSTANTS
val ID = UUID.fromString("11111111-1111-1111-1111-111111111111")
val PLANETA = Planeta(UUID.fromString("11111111-1111-1111-1111-111111111111"), "Planeta 1", "Clima 1", "Terreno 1")
val PLANETA_SEM_NOME = Planeta(UUID.fromString("11111111-1111-1111-1111-111111111111"), null, "Clima 1", "Terreno 1")
val PLANETA_SEM_CLIMA = Planeta(UUID.fromString("11111111-1111-1111-1111-111111111111"), "Planeta 1", null, "Terreno 1")
val PLANETA_SEM_TERRENO = Planeta(UUID.fromString("11111111-1111-1111-1111-111111111111"), "Planeta 1", "Clima 1", null)
val LISTA_PLANETAS = listOf(
        Planeta(UUID.fromString("11111111-1111-1111-1111-111111111111"), "Planeta 1", "Clima 1", "Terreno 1"),
        Planeta(UUID.fromString("22222222-2222-2222-2222-222222222222"), "Planeta 2", "Clima 2", "Terreno 2"),
        Planeta(UUID.fromString("33333333-3333-3333-3333-333333333333"), "Planeta 3", "Clima 3", "Terreno 3"))
private val LISTA_PLANETAS_ORDENADO_NOME_ASCENDENTE = listOf(
        Planeta(UUID.fromString("11111111-1111-1111-1111-111111111111"), "Planeta A", "Clima 1", "Terreno 1"),
        Planeta(UUID.fromString("22222222-2222-2222-2222-222222222222"), "Planeta B", "Clima 2", "Terreno 2"),
        Planeta(UUID.fromString("33333333-3333-3333-3333-333333333333"), "Planeta C", "Clima 3", "Terreno 3"))
private val LISTA_PLANETAS_ORDENADO_CLIMA_ASCENDENTE = listOf(
        Planeta(UUID.fromString("11111111-1111-1111-1111-111111111111"), "Planeta 1", "Clima A", "Terreno 1"),
        Planeta(UUID.fromString("22222222-2222-2222-2222-222222222222"), "Planeta 2", "Clima B", "Terreno 2"),
        Planeta(UUID.fromString("33333333-3333-3333-3333-333333333333"), "Planeta 3", "Clima C", "Terreno 3"))
private val LISTA_PLANETAS_ORDENADO_TERRENO_ASCENDENTE = listOf(
        Planeta(UUID.fromString("11111111-1111-1111-1111-111111111111"), "Planeta 1", "Clima 1", "Terreno A"),
        Planeta(UUID.fromString("22222222-2222-2222-2222-222222222222"), "Planeta 2", "Clima 2", "Terreno B"),
        Planeta(UUID.fromString("33333333-3333-3333-3333-333333333333"), "Planeta 3", "Clima 3", "Terreno C"))
private val LISTA_PLANETAS_ORDENADO_NOME_DESCENDENTE = listOf(
        Planeta(UUID.fromString("11111111-1111-1111-1111-111111111111"), "Planeta C", "Clima 1", "Terreno 1"),
        Planeta(UUID.fromString("22222222-2222-2222-2222-222222222222"), "Planeta B", "Clima 2", "Terreno 2"),
        Planeta(UUID.fromString("33333333-3333-3333-3333-333333333333"), "Planeta A", "Clima 3", "Terreno 3"))
private val LISTA_PLANETAS_ORDENADO_TERRENO_DESCENDENTE = listOf(
        Planeta(UUID.fromString("11111111-1111-1111-1111-111111111111"), "Planeta 1", "Clima 1", "Terreno C"),
        Planeta(UUID.fromString("22222222-2222-2222-2222-222222222222"), "Planeta 2", "Clima 2", "Terreno B"),
        Planeta(UUID.fromString("33333333-3333-3333-3333-333333333333"), "Planeta 3", "Clima 3", "Terreno A"))
private val LISTA_PLANETAS_ORDENADO_CLIMA_DESCENDENTE = listOf(
        Planeta(UUID.fromString("11111111-1111-1111-1111-111111111111"), "Planeta 1", "Clima C", "Terreno 1"),
        Planeta(UUID.fromString("22222222-2222-2222-2222-222222222222"), "Planeta 2", "Clima B", "Terreno 2"),
        Planeta(UUID.fromString("33333333-3333-3333-3333-333333333333"), "Planeta 3", "Clima A", "Terreno 3"))

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PlanetaServiceTest {

    private lateinit var planeta: Planeta
    private lateinit var id: UUID
    private lateinit var sort: String
    private lateinit var order: String
    private lateinit var planetas: List<Planeta>
    private lateinit var service: PlanetaService
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
            `entao esperamos que o planeta seja salvo com sucesso`()
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
    inner class `Recuperar Planetas de forma ordenada` {

        @Test
        fun `deve recuperar uma lista de planetas ordenado pelo nome em ordem ascendente`() {
            `dado que temos o parametro de ordenacao por nome`()
            `dado que temos o parametro de ordenacao em ordem ascendente`()
            `dado que repository recupera uma lista de planetas ordenados pelo nome em ordem ascendente`()
            `quando chamados o metodo recuperar todos de forma ordenada`()
            `entao esperamos uma lista de planetas ordenados pelo nome em ordem ascendente`()
        }

        @Test
        fun `deve recuperar uma lista de planetas ordenado pelo clima em ordem ascendente`() {
            `dado que temos o parametro de ordenacao por clima`()
            `dado que temos o parametro de ordenacao em ordem ascendente`()
            `dado que repository recupera uma lista de planetas ordenados pelo clima em ordem ascendente`()
            `quando chamados o metodo recuperar todos de forma ordenada`()
            `entao esperamos uma lista de planetas ordenados pelo clima em ordem ascendente`()
        }

        @Test
        fun `deve recuperar uma lista de planetas ordenado pelo terreno em ordem ascendente`() {
            `dado que temos o parametro de ordenacao por terreno`()
            `dado que temos o parametro de ordenacao em ordem ascendente`()
            `dado que repository recupera uma lista de planetas ordenados pelo terreno em ordem ascendente`()
            `quando chamados o metodo recuperar todos de forma ordenada`()
            `entao esperamos uma lista de planetas ordenados pelo terreno em ordem ascendente`()
        }

        @Test
        fun `deve recuperar uma lista de planetas ordenado pelo nome em ordem descendente`() {
            `dado que temos o parametro de ordenacao por nome`()
            `dado que temos o parametro de ordenacao em ordem descendente`()
            `dado que repository recupera uma lista de planetas ordenados pelo nome em ordem descendente`()
            `quando chamados o metodo recuperar todos de forma ordenada`()
            `entao esperamos uma lista de planetas ordenados pelo nome em ordem descendente`()
        }

        @Test
        fun `deve recuperar uma lista de planetas ordenado pelo terreno em ordem descendente`() {
            `dado que temos o parametro de ordenacao por terreno`()
            `dado que temos o parametro de ordenacao em ordem descendente`()
            `dado que repository recupera uma lista de planetas ordenados pelo terreno em ordem descendente`()
            `quando chamados o metodo recuperar todos de forma ordenada`()
            `entao esperamos uma lista de planetas ordenados pelo terreno em ordem descendente`()
        }

        @Test
        fun `deve recuperar uma lista de planetas ordenado pelo clima em ordem descendente`() {
            `dado que temos o parametro de ordenacao por clima`()
            `dado que temos o parametro de ordenacao em ordem descendente`()
            `dado que repository recupera uma lista de planetas ordenados pelo clima em ordem descendente`()
            `quando chamados o metodo recuperar todos de forma ordenada`()
            `entao esperamos uma lista de planetas ordenados pelo clima em ordem descendente`()
        }

        @Test
        fun `deve lancar um UnprocessableEntityException`() {
            `dado que temos um parametro de ordenacao invalido`()
            `entao esperamos que seja lancada uma ResourceNotFoundException`()
        }

        @Test
        fun `deve lancar um excecao ao recuperar lista ordenada`() {
            `dado que temos o parametro de ordenacao por nome`()
            `dado que temos o parametro de ordenacao em ordem ascendente`()
            `dado que repository lanca uma excecao ao recuperar a lista ordenada`()
            `entao esperamos que uma excecao seja lancada ao recuperar a lista ordenada`()
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

    @Nested
    inner class Atualizar {
        @Test
        fun `deve atualizar um planeta por id com sucesso`() {
            `dado que temos um planeta`()
            `dado que a entidade identifica pelo id existe no respositorio`()
            `dado que planetaRepository salva com sucesso`()
            `quando chamamos o metodo atualizar`()
            `entao esperamos que o planeta seja salvo com sucesso`()
        }
        @Test
        fun `deve lancar InternalServerErrorException ao atualizar`() {
            `dado que temos um planeta`()
            `dado que a entidade identifica pelo id existe no respositorio`()
            `dado que planeta repository lanca uma excecao ao atualizar`()
            `entao esperamos uma InternalServerErrorException ao atualizar`()
        }
        @Test
        fun `deve lancar ResourceNotFoundException ao atualizar`() {
            `dado que temos um planeta`()
            `dado que a entidade identificada pelo id nao existe no repositorio`()
            `entao esperamos uma ResourceNotFoundException ao atualizar`()
        }
    }

    @Nested
    inner class `Atualizacao Parcial` {
        @Test
        fun `deve atualizar parcialmente com sucesso`() {
            `dado que temos um planeta`()
            `dado que o repositorio retorna uma entidade por id`()
            `dado que planetaRepository salva com sucesso`()
            `quando chamamos o metodo atualizar parcialmente`()
            `entao esperamos que o planeta seja salvo com sucesso`()
        }
        @Test
        fun `deve atualizar um planeta sem nome com sucesso`() {
            `dado que temos um planeta sem nome`()
            `dado que o repositorio retorna uma entidade por id`()
            `dado que planetaRepository salva com sucesso`()
            `quando chamamos o metodo atualizar parcialmente`()
            `entao esperamos que o planeta seja salvo com sucesso`()
        }
        @Test
        fun `deve atualizar um planeta sem clima com sucesso`() {
            `dado que temos um planeta sem clima`()
            `dado que o repositorio retorna uma entidade por id`()
            `dado que planetaRepository salva com sucesso`()
            `quando chamamos o metodo atualizar parcialmente`()
            `entao esperamos que o planeta seja salvo com sucesso`()
        }
        @Test
        fun `deve atualizar um planeta sem terreno com sucesso`() {
            `dado que temos um planeta sem terreno`()
            `dado que o repositorio retorna uma entidade por id`()
            `dado que planetaRepository salva com sucesso`()
            `quando chamamos o metodo atualizar parcialmente`()
            `entao esperamos que o planeta seja salvo com sucesso`()
        }
        @Test
        fun `deve lancar InternalServerErrorException ao atualizar parcial`() {
            `dado que temos um planeta`()
            `dado que o repositorio retorna uma entidade por id`()
            `dado que planetaRepository lanca excecao ao salvar`()
            `entao esperamos uma InternalServerErrorException ao atualizar parcial`()
        }
        @Test
        fun `deve lancar ResourceNotFoundException ao atualizar parcial`() {
            `dado que temos um planeta`()
            `dado que respository retorna um Optional Vazio ao recuperar uma instancia por id`()
            `entao esperamos uma ResourceNotFoundException ao atualizar parcial`()
        }
    }

    private fun `dado que temos um id valido`() {
        id = ID
    }

    fun `dado que temos um planeta`() {
        planeta = PLANETA
        this.id = planeta.id
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


    private fun `dado que planeta repository lanca uma excecao ao atualizar`() {
        `when`(repository.save(planeta)).thenThrow(RecoverableDataAccessException("Banco de dados indisponivel"))
    }

    private fun `dado que o repositorio retorna uma entidade por id`() {
        `when`(repository.findById(id)).thenReturn(Optional.of(PLANETA))
    }


    private fun `dado que temos um planeta sem nome`() {
        this.planeta = PLANETA_SEM_NOME
        this.id = ID
    }


    private fun `dado que temos um planeta sem clima`() {
        this.planeta = PLANETA_SEM_CLIMA
        this.id = ID
    }


    private fun `dado que temos um planeta sem terreno`() {
        this.planeta = PLANETA_SEM_TERRENO
        this.id = ID
    }


    private fun `dado que temos o parametro de ordenacao por nome`() {
        this.sort = "nome"
    }

    private fun `dado que temos o parametro de ordenacao em ordem ascendente`() {
        this.order = "asc"
    }

    private fun `dado que repository recupera uma lista de planetas ordenados pelo nome em ordem ascendente`() {
        doReturn(LISTA_PLANETAS_ORDENADO_NOME_ASCENDENTE).`when`(repository).findAll(Sort.by(this.sort).ascending())
    }

    private fun `dado que temos o parametro de ordenacao por clima`() {
        this.sort = "clima"
    }

    private fun `dado que repository recupera uma lista de planetas ordenados pelo clima em ordem ascendente`() {
        doReturn(LISTA_PLANETAS_ORDENADO_CLIMA_ASCENDENTE).`when`(repository).findAll(Sort.by(this.sort).ascending())
    }

    private fun `dado que temos o parametro de ordenacao por terreno`() {
        this.sort = "terreno"
    }

    private fun `dado que repository recupera uma lista de planetas ordenados pelo terreno em ordem ascendente`() {
        doReturn(LISTA_PLANETAS_ORDENADO_TERRENO_ASCENDENTE).`when`(repository).findAll(Sort.by(this.sort).ascending())
    }

    private fun `dado que temos o parametro de ordenacao em ordem descendente`() {
        this.order = "desc"
    }

    private fun `dado que repository recupera uma lista de planetas ordenados pelo nome em ordem descendente`() {
        doReturn(LISTA_PLANETAS_ORDENADO_NOME_DESCENDENTE).`when`(repository).findAll(Sort.by(this.sort).descending())
    }

    private fun `dado que repository recupera uma lista de planetas ordenados pelo terreno em ordem descendente`() {
        doReturn(LISTA_PLANETAS_ORDENADO_TERRENO_DESCENDENTE).`when`(repository).findAll(Sort.by(this.sort).descending())
    }

    private fun `dado que repository recupera uma lista de planetas ordenados pelo clima em ordem descendente`() {
        doReturn(LISTA_PLANETAS_ORDENADO_CLIMA_DESCENDENTE).`when`(repository).findAll(Sort.by(this.sort).descending())
    }

    private fun `dado que repository lanca uma excecao ao recuperar a lista ordenada`() {
        doThrow(RecoverableDataAccessException("Banco de dados indisponivel")).`when`(repository).findAll(Sort.by(sort).ascending())
    }

    private fun `dado que temos um parametro de ordenacao invalido`() {
        this.sort = "invalido"
    }

    fun `quando chamamos o metodo salvar`() {
        id = service.create(planeta)
    }

    fun `quando chamamos o metodo recuperar todos`() {
        planetas = service.getAll(null, null)
    }

    private fun `quando chamamos o metodo recuperar por id`() {
        planeta = service.getById(id)
    }

    private fun `quando chamamos o metodo excluir por id`() {
        service.deleteById(id)
    }

    private fun `quando chamamos o metodo atualizar`() {
        service.update(planeta)
    }

    private fun `quando chamamos o metodo atualizar parcialmente`() {
        service.partialUpdate(planeta)
    }

    private fun `quando chamados o metodo recuperar todos de forma ordenada`() {
        this.planetas = service.getAll(this.sort, this.order)
    }

    fun `entao esperamos que o planeta seja salvo com sucesso`() {
        assertEquals(ID, planeta.id)
        verify(repository, times(1)).save(any(Planeta::class.java))
    }

    fun `entao esperamos que seja lancada uma excecao ao salvar`() {
        assertThrows<InternalServerErrorException> { service.create(planeta) }
    }

    fun `entao esperamos uma lista contento planetas`() {
        assertEquals(LISTA_PLANETAS, this.planetas)
    }

    fun `entao experamos que seja lancada uma excecao ao recuperar a lista de planetas`() {
        assertThrows<InternalServerErrorException> { service.getAll(null, null) }
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

    private fun `entao esperamos uma InternalServerErrorException ao atualizar`() {
        assertThrows<InternalServerErrorException> { service.update(planeta) }
    }

    private fun `entao esperamos que o planeta nao seja salvo`() {
        verify(repository, times(0)).save(any(Planeta::class.java))
    }

    private fun `entao esperamos uma ResourceNotFoundException ao atualizar`() {
        assertThrows<ResourceNotFoundException> { service.update(planeta) }
    }

    private fun `entao esperamos uma InternalServerErrorException ao atualizar parcial`() {
        assertThrows<InternalServerErrorException> { service.partialUpdate(planeta) }
    }

    private fun `entao esperamos uma ResourceNotFoundException ao atualizar parcial`() {
        assertThrows<ResourceNotFoundException> { service.partialUpdate(planeta) }
    }

    private fun `entao esperamos uma lista de planetas ordenados pelo nome em ordem ascendente`() {
        assertEquals(LISTA_PLANETAS_ORDENADO_NOME_ASCENDENTE, this.planetas)
    }

    private fun `entao esperamos uma lista de planetas ordenados pelo clima em ordem ascendente`() {
        assertEquals(LISTA_PLANETAS_ORDENADO_CLIMA_ASCENDENTE, this.planetas)
    }

    private fun `entao esperamos uma lista de planetas ordenados pelo terreno em ordem ascendente`() {
        assertEquals(LISTA_PLANETAS_ORDENADO_TERRENO_ASCENDENTE, this.planetas)
    }

    private fun `entao esperamos uma lista de planetas ordenados pelo nome em ordem descendente`() {
        assertEquals(LISTA_PLANETAS_ORDENADO_NOME_DESCENDENTE, this.planetas)
    }

    private fun `entao esperamos uma lista de planetas ordenados pelo terreno em ordem descendente`() {
        assertEquals(LISTA_PLANETAS_ORDENADO_TERRENO_DESCENDENTE, this.planetas)
    }

    private fun `entao esperamos uma lista de planetas ordenados pelo clima em ordem descendente`() {
        assertEquals(LISTA_PLANETAS_ORDENADO_CLIMA_DESCENDENTE, this.planetas)
    }

    private fun `entao esperamos que uma excecao seja lancada ao recuperar a lista ordenada`() {
        assertThrows<InternalServerErrorException> { service.getAll(this.sort, this.order) }
    }

    private fun `entao esperamos que seja lancada uma ResourceNotFoundException`() {
        assertThrows<UnprocessableEntityException> { service.getAll(sort) }
    }
}
