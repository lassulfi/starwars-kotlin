package com.github.lassulfi.starwars.api.exceptions

class InternalServerErrorException: RuntimeException, HttpError {
    private val description: String

    constructor(description: String): super() {
        this.description = description
    }

    override fun getHttpCode(): Int = 500

    override fun getHttpError(): String = "Internal Server Error"

    override fun getDescription(): String = this.description
}