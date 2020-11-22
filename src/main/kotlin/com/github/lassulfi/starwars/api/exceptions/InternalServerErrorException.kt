package com.github.lassulfi.starwars.api.exceptions

import java.lang.RuntimeException

class InternalServerErrorException(message: String?) : RuntimeException(message) {
}