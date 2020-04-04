package com.akashbakshi.modules

import com.akashbakshi.SocketUser
import com.akashbakshi.models.Session
import com.google.gson.Gson
import io.ktor.application.Application
import io.ktor.http.cio.websocket.Frame
import io.ktor.http.cio.websocket.WebSocketSession
import io.ktor.http.cio.websocket.readText
import io.ktor.routing.*
import io.ktor.websocket.webSocket
import kotlinx.coroutines.channels.ClosedReceiveChannelException

data class SessionMessage(val type:String,val roomId:String, val socketId: SocketUser?)

fun handleSessionMessage(message:SessionMessage,socketSession: WebSocketSession){
    if(message.type == "request_question"){

    }
}

fun Application.gameSessionModule(){

    routing{
        webSocket("/game_session"){
            try{
                while (true) {
                    val frame = incoming.receive()
                    if (frame is Frame.Text) {
                        println("game session running ${frame.readText()}")
                        val msg = Gson().fromJson(frame.readText(),SessionMessage::class.java)
                        handleSessionMessage(msg,this)
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