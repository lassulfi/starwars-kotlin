package com.github.lassulfi.starwars.api.exceptions

interface HttpError {
    fun getHttpCode(): Int
    fun getHttpError(): String
    fun getDescription(): String
}