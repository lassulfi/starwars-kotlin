package com.github.lassulfi.starwars.api.advice

import com.github.lassulfi.starwars.api.dto.ErrorMessage
import com.github.lassulfi.starwars.api.exceptions.InternalServerErrorException
import com.github.lassulfi.starwars.api.exceptions.ResourceNotFoundException
import com.github.lassulfi.starwars.api.exceptions.UnprocessableEntityException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ControllerAdvice {

    @ExceptionHandler(ResourceNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun notFound(ex: ResourceNotFoundException): ErrorMessage = ErrorMessage(ex)

    @ExceptionHandler(UnprocessableEntityException::class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    fun unprocessableEntity(ex: UnprocessableEntityException): ErrorMessage = ErrorMessage(ex)

    @ExceptionHandler(InternalServerErrorException::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun internalServerError(ex: InternalServerErrorException): ErrorMessage = ErrorMessage(ex)

    @ExceptionHandler(Exception::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    fun genericServerError(ex: Exception): ErrorMessage =
            ErrorMessage("Internal Server Error", ex.message?: "Erro genérico")
}