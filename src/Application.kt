package com.akashbakshi

import com.akashbakshi.models.GameRoom
import com.akashbakshi.models.Session
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.request.*
import io.ktor.routing.*
import io.ktor.http.*
import io.ktor.websocket.*
import io.ktor.http.cio.websocket.*
import java.time.*
import com.fasterxml.jackson.databind.*
import io.ktor.jackson.*
import io.ktor.features.*
import kotlinx.coroutines.channels.ClosedReceiveChannelException
import java.util.*
import com.google.gson.*
import io.ktor.server.engine.commandLineEnvironment
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import kotlin.collections.ArrayList

enum class GameState{
    GAME_PENDING,
    GAME_IN_PROGRESS,
    ROUND_START,
    QUESTION_START,
    QUESTIONS_IN_PROGRESS,
    QUESTION_END,
    ROUND_END,
    GAME_END
}

data class SocketUser(val socketId: String, var username:String?,var inGame:Boolean)

fun main(args: Array<String>){
    embeddedServer(Netty, commandLineEnvironment(args)).start(wait = true)
}

var sessions: ArrayList<Session> = ArrayList()

fun newSocketUser(id:String,username: String?,inGame: Boolean):SocketUser{
    return SocketUser(id,username,inGame)
}

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {

    install(WebSockets) {
        pingPeriod = Duration.ofSeconds(15)
        timeout = Duration.ofSeconds(15)
        maxFrameSize = Long.MAX_VALUE
        masking = false
    }

    install(ContentNegotiation) {
        jackson {
            enable(SerializationFeature.INDENT_OUTPUT)
        }
    }

    routing {
        webSocket("/host_room") {
            val hostSocketUser = newSocketUser(UUID.randomUUID().toString(),null,true)
            val newRoom = GameRoom(UUID.randomUUID().toString(),hostSocketUser,arrayListOf<SocketUser>())
            val tmpSession = Session(true,arrayListOf<Pair<String,Int>>(),newRoom,GameState.GAME_PENDING)
            sessions.add(tmpSession)

            try {
                while (true) {
                    val frame = incoming.receive()
                    if (frame is Frame.Text) {
                        send(Gson().toJson(tmpSession.room))
                    }
                }
            } catch (e: ClosedReceiveChannelException) {
                // Do nothing!

                println("onClose ${closeReason.await()}")
            } catch (e: Throwable) {
                e.printStackTrace()
            }

        }

    }
}



