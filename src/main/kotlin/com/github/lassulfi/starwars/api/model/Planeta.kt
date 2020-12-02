package com.github.lassulfi.starwars.api.model

import com.fasterxml.jackson.annotation.JsonPropertyOrder
import com.sun.istack.NotNull
import io.swagger.annotations.ApiModelProperty
import java.util.*
import javax.persistence.*
import javax.validation.constraints.Size

@Entity
@Table(name = "TBL_PLANETAS")
@JsonPropertyOrder(value = ["id", "nome", "clima", "terreno"], alphabetic = false)
data class Planeta (
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        @Column(name = "id")
        @ApiModelProperty(value = "Identificador do planetla", example = "1234XXXX-XXXX-XXXX-XXXX-XXXXXXXX5678")
        var id: UUID = UUID.randomUUID(),

        @Column(name = "nome")
        @NotNull
        @Size(min = 3, max = 20)
        @ApiModelProperty(value = "Nome do planeta", example = "Ando")
        var nome: String? = "",

        @Column(name = "clima")
        @NotNull
        @Size(min = 5, max = 500)
        @ApiModelProperty(value = "Clima do planeta", example = "Deserto")
        var clima: String? = "",

        @Column(name = "terreno")
        @NotNull
        @Size(min = 5, max = 500)
        @ApiModelProperty(value = "Terreno do planeta", example = "Predominantemente planicies")
        var terreno: String? = ""
) {
    fun fillWith(obj: Planeta) {
        if (obj.nome != null) this.nome = obj.nome
        if (obj.clima != null) this.clima = obj.clima
        if (obj.terreno != null) this.terreno = obj.terreno
    }
}