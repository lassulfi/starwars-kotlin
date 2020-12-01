package com.github.lassulfi.starwars.api.dto

import com.github.lassulfi.starwars.api.exceptions.HttpError
import io.swagger.annotations.ApiModelProperty

class ErrorMessage {
    @ApiModelProperty(value = "Tipo do erro", required = true, example = "Bad Request")
    val type: String

    @ApiModelProperty(value = "Descrição do erro", required = true,
            example = "Existem problemas com a corpo da requisição")
    val description: String

    @ApiModelProperty(value = "Lista das causas do erro", example = "[\"A propriedade 'teste' não pode ser nula.\"]")
    val errors: Set<String>? = null

    constructor(type: String, description: String) {
        this.type = type
        this.description = description
    }

    constructor(error: HttpError) : this(error.getHttpError(), error.getDescription()) {
    }
}