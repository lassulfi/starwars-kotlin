package com.github.lassulfi.starwars.api

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class StarwarsApiApplication

fun main(args: Array<String>) {
	runApplication<StarwarsApiApplication>(*args)
}
