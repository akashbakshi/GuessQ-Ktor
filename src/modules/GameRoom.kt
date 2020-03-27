package com.akashbakshi.modules

import io.ktor.application.Application
import io.ktor.http.cio.websocket.Frame
import io.ktor.http.cio.websocket.readText
import io.ktor.routing.*
import io.ktor.websocket.webSocket
import kotlinx.coroutines.channels.ClosedReceiveChannelException



fun Application.gameSessionModule(){

    routing{
        webSocket("/game_session"){
            try{
                while (true) {
                    val frame = incoming.receive()
                    if (frame is Frame.Text) {
                        println("game session running ${frame.readText()}")
                    }
                }
            } catch (e: ClosedReceiveChannelException) {
                // Do nothing!
            } catch (e: Throwable) {
                e.printStackTrace()
            }
        }
    }
}