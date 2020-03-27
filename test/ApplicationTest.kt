package com.akashbakshi

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.request.*
import io.ktor.routing.*
import io.ktor.http.*
import io.ktor.websocket.*
import io.ktor.http.cio.websocket.*
import java.time.*
import io.ktor.gson.*
import io.ktor.features.*
import com.fasterxml.jackson.databind.*
import io.ktor.jackson.*
import kotlin.test.*
import io.ktor.server.testing.*

class ApplicationTest {
    /*
    @Test
    fun testRoot() {
        withTestApplication({ module(testing = true) }) {
            handleWebSocket( "/lobby/join",null).apply {
                assertEquals(HttpStatusCode.OK, response.status())
                assertEquals("HELLO WORLD!", response.content)
            }
        }
    }*/
}
