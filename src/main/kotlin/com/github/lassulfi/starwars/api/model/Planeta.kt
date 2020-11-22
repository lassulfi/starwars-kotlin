package com.github.lassulfi.starwars.api.model

import java.util.*
import javax.persistence.*

@Entity
@Table(name = "TBL_PLANETAS")
data class Planeta (
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: UUID,
        val nome: String = "",
        val clima: String = "",
        val terreno: String = ""
)