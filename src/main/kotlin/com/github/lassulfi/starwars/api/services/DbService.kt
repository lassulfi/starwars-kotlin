package com.github.lassulfi.starwars.api.services

import com.github.lassulfi.starwars.api.model.Planeta
import com.github.lassulfi.starwars.api.repository.PlanetaRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.text.ParseException
import java.util.*

@Service
class DbService {

    @Autowired
    private lateinit var planetaRepository: PlanetaRepository

    fun instanciarBanco() {
        try {
            planetaRepository.saveAll(listOf(
                    Planeta(UUID.randomUUID(), "Yavin IV", "temperado, tropical", "florestas, florestas tropicais"),
                    Planeta(UUID.randomUUID(), "Tatooine", "arido", "deserto"),
                    Planeta(UUID.randomUUID(), "Alderaan", "temperado", "planicies, montanhas"),
                    Planeta(UUID.randomUUID(), "Hoth", "polar", "tundra, cavernas congeladas, cordilheiras montanhosas"),
                    Planeta(UUID.randomUUID(), "Dagobah", "pantanoso", "pantanos, floresta"),
                    Planeta(UUID.randomUUID(), "Bespin", "temperado", "nuvem gasosa"),
                    Planeta(UUID.randomUUID(), "Endor", "temperado", "florestas, montanhas, lagos"),
                    Planeta(UUID.randomUUID(), "Naboo", "temperado", "planicies, pantanos, florestas, montanhas"),
                    Planeta(UUID.randomUUID(), "Coruscant", "temperado", "urbanizado, montanhas"),
                    Planeta(UUID.randomUUID(), "Kamino", "temperado", "oceanos"),
                    Planeta(UUID.randomUUID(), "Geonosis", "temperado, árido", "rochas, desertos, montanhas"),
                    Planeta(UUID.randomUUID(), "Utapau", "temperado, árido, ventoso", "árido, savana, canyons, sinkholes"),
                    Planeta(UUID.randomUUID(), "Mustafar", "quente", "vulcões, rios de lava, montanhas, cavernas"),
                    Planeta(UUID.randomUUID(), "Kashyyyk", "tropical", "salvas, florestas, lagos, rios"),
                    Planeta(UUID.randomUUID(), "Utapau", "temperado, árido, ventoso", "árido, savana, canyons, sinkholes"),
                    Planeta(UUID.randomUUID(), "Polis Massa", "temperado artificialmente", "asteróide sem ar"),
                    Planeta(UUID.randomUUID(), "Mygeeto", "frígido", "glaciers, montanhas, canyons congelados"),
                    Planeta(UUID.randomUUID(), "Felucia", "quente, úmido", "florestas de fungos"),
                    Planeta(UUID.randomUUID(), "Cato Neimoidia", "temperado, úmido", "montanhas, campos, florestas, arcos rochoosos"),
                    Planeta(UUID.randomUUID(), "Saleucami", "quente", "cavernas, deserto, montanhas, vulcões"),
                    Planeta(UUID.randomUUID(), "Stewjon", "temperado", "gramado")
            ))
        } catch (e: ParseException) {
            throw e
        }
    }
}