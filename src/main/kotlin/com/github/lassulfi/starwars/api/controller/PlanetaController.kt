package com.github.lassulfi.starwars.api.controller

import com.github.lassulfi.starwars.api.dto.ErrorMessage
import com.github.lassulfi.starwars.api.dto.NoResponse
import com.github.lassulfi.starwars.api.model.Planeta
import com.github.lassulfi.starwars.api.services.PlanetaService
import io.swagger.annotations.*
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI
import java.util.*
import javax.validation.Valid

@RestController
@RequestMapping(value = ["/planetas"])
@Api(value = "/planetas", tags = ["Planetas"])
class PlanetaController(val service: PlanetaService) {

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Recupera todos os planetas",
            notes = "Recupera a lista de todos os planetas",
            response = Planeta::class,
            responseContainer = "List",
            produces = "application/json")
    @ApiResponses(
            ApiResponse(code = 200, message = "OK"),
            ApiResponse(code = 500, message = "Internal Server Error", response = ErrorMessage::class)
    )
    fun getAll(): List<Planeta> = service.getAll(null, null)

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Recupera um planeta", notes = "Recupera um planeta pelo seu identificador",
            response = Planeta::class, produces = "application/json")
    @ApiResponses(
            ApiResponse(code = 200, message = "OK"),
            ApiResponse(code = 404, message = "Not Found", response = ErrorMessage::class),
            ApiResponse(code = 500, message = "Internal Server Error", response = ErrorMessage::class)
    )
    fun getById(@PathVariable @ApiParam(value = "Identificador do planeta", required = true) id: UUID): Planeta = service.getById(id)

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Cria um Planeta", notes = "Cadastra um planeta", consumes = "application/json")
    @ApiResponses(
            ApiResponse(code = 201, message = "Created", response = NoResponse::class),
            ApiResponse(code = 400, message = "Bad Request", response = ErrorMessage::class),
            ApiResponse(code = 500, message = "Internal Server Error", response = ErrorMessage::class)
    )
    fun create(@RequestBody @Valid @ApiParam(name = "Planeta", value = " ", required = true) planeta: Planeta): ResponseEntity<Unit> {
        val id = service.create(planeta)
        return ResponseEntity.created(URI.create("/planetas/${id}")).build()
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Atualiza um planeta", notes = "Atualiza um planeta pelo seu id", consumes = "application/json")
    @ApiResponses(
            ApiResponse(code = 204, message = "No Content", response = NoResponse::class),
            ApiResponse(code = 400, message = "Bad Request", response = ErrorMessage::class),
            ApiResponse(code = 404, message = "Not Found", response = ErrorMessage::class),
            ApiResponse(code = 500, message = "Internal Server Error", response = ErrorMessage::class)
    )
    fun update(@PathVariable @ApiParam(value = "Identificador do planeta", required = true) id:UUID,
               @RequestBody @Valid @ApiParam(name = "Planeta", value = " ", required = true) planeta: Planeta): ResponseEntity<Unit> {
        planeta.id = id
        this.service.update(planeta)
        return ResponseEntity.noContent().build()
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Atualiza parcialmente um planeta", notes = "Atualiza parcialmente um planeta identificado pelo seu id")
    @ApiResponses(
            ApiResponse(code = 204, message = "No Content", response = NoResponse::class),
            ApiResponse(code = 400, message = "Bad Request", response = ErrorMessage::class),
            ApiResponse(code = 404, message = "Not Found", response = ErrorMessage::class),
            ApiResponse(code = 500, message = "Internal Server Error", response = ErrorMessage::class)
    )
    fun partialUpdate(@PathVariable @ApiParam(value = "Identificador do planeta", required = true) id: UUID,
                      @RequestBody @Valid @ApiParam(name = "Planeta", value = " ", required = true) planeta: Planeta): ResponseEntity<Unit> {
        planeta.id = id
        this.service.partialUpdate(planeta)
        return ResponseEntity.noContent().build()
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Exclui um planeta", notes = "Exclui um planeta pelo seu identificador")
    @ApiResponses(
            ApiResponse(code = 204, message = "No Content", response = NoResponse::class),
            ApiResponse(code = 400, message = "Bad Request", response = ErrorMessage::class),
            ApiResponse(code = 404, message = "Not Found", response = ErrorMessage::class),
            ApiResponse(code = 500, message = "Internal Server Error", response = ErrorMessage::class)
    )
    fun deleteById(@PathVariable @ApiParam(value = "Identificador do planeta", required = true) id: UUID): ResponseEntity<Unit> {
        this.service.deleteById(id)
        return ResponseEntity.noContent().build()
    }
}