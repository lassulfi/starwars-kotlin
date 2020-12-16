package com.github.lassulfi.starwars.api.services

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface PageableService <T> {
    fun getPageable(pageable: Pageable): Page<T>
}