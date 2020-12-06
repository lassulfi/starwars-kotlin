package com.github.lassulfi.starwars.api.exceptions

class UnprocessableEntityException: RuntimeException, HttpError {
    private val descritption: String

    constructor(descritption: String) : super() {
        this.descritption = descritption
    }


    override fun getHttpCode(): Int = 422

    override fun getHttpError(): String = "Unprocessable Entity"

    override fun getDescription(): String = this.descritption
}