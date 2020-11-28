package com.github.lassulfi.starwars.api.model

import java.util.*
import javax.persistence.*

@Entity
@Table(name = "TBL_PLANETAS")
data class Planeta (
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: UUID,
        var nome: String?,
        var clima: String?,
        var terreno: String?
) {
    fun fillWith(obj: Planeta) {
        this.nome = obj.nome
        this.clima = obj.clima
        this.terreno = obj.terreno
    }
}