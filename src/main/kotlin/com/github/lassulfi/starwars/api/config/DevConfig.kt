package com.github.lassulfi.starwars.api.config

import com.github.lassulfi.starwars.api.services.DbService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import java.text.ParseException

@Configuration
@Profile("dev")
class DevConfig {

    @Autowired
    lateinit var dbService: DbService

    @Bean
    fun instanciarBanco(): Boolean {
        try {
            dbService.instanciarBanco()

            return true
        } catch (e: ParseException) {
            throw e
        }
    }
}