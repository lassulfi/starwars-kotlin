package com.github.lassulfi.starwars.api.exceptions

class ResourceNotFoundException: RuntimeException, HttpError {

    private val description: String

    constructor(description: String): super() {
        this.description = description
    }

    override fun getHttpCode(): Int = 404

    override fun getHttpError(): String = "Not Found"

    override fun getDescription(): String = this.description
}