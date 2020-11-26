package com.github.lassulfi.starwars.api.controller

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.mock.web.MockHttpServletResponse
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.RequestBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders

abstract class MvcControllerTestable<T> {

    private lateinit var mvc: MockMvc

    protected fun initializeMvc(controller: T) {
        this.mvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(ControllerAdvice())
                .build()
    }

    protected fun performCall(requestBuilder: RequestBuilder): MockHttpServletResponse {
        val response: MockHttpServletResponse
        try {
            response = this.mvc
                    .perform(requestBuilder)
                    .andReturn()
                    .response
        } catch (ex: Exception) {
            throw RuntimeException(ex)
        }

        return response
    }

    protected fun toJson(obj: Any?): String {
        var json = ""
        try {
            if(obj != null) {
                val mapper = ObjectMapper()
                json = String(mapper.writeValueAsBytes(obj))
            }
        } catch (ex: Exception) {
            throw RuntimeException(ex)
        }

        return json
    }
}