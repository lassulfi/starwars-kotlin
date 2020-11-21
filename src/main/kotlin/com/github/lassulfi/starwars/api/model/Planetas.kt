package com.github.lassulfi.starwars.api.model

import javax.persistence.Entity
import javax.persistence.Table
import javax.persistence.Id

@Entity
@Table(name = "TBL_PLANETAS")
data class Planetas (
        @Id val id: Long = 1L,
        val nome: String = "",
        val clima: String = "",
        val terreno: String = ""
)