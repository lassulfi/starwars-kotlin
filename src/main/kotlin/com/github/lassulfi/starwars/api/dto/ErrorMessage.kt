package com.github.lassulfi.starwars.api.dto

import com.github.lassulfi.starwars.api.exceptions.HttpError

class ErrorMessage {
    val type: String
    val description: String
    val errors: Set<String>? = null

    constructor(type: String, description: String) {
        this.type = type
        this.description = description
    }

    constructor(error: HttpError) : this(error.getHttpError(), error.getDescription()) {
    }
}