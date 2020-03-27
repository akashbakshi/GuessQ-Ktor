package com.akashbakshi.modules


import com.akashbakshi.SocketUser
import com.akashbakshi.models.GameRoom
import com.akashbakshi.newSocketUser
import com.akashbakshi.rooms
import com.fasterxml.jackson.databind.SerializationFeature
import com.google.gson.Gson
import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.features.ContentNegotiation
import io.ktor.http.cio.websocket.*
import io.ktor.jackson.jackson
import io.ktor.routing.*
import io.ktor.websocket.*
import io.ktor.http.cio.websocket.*
import kotlinx.coroutines.channels.ClosedReceiveChannelException
import java.time.Duration
import java.util.*



fun joinRoom(newUser:SocketUser,roomId:String){
    rooms.forEach {
        if(it.roomId == roomId){
            it.players.add(newUser)
            return
        }
    }
}

data class JoinRoom(val roomId:String, val nickname:String)

fun Application.lobbyModule(){

    routing{
        route("/lobby"){

            webSocket("/join") {

                try{
                    while (true) {
                        val frame = incoming.receive()
                        if (frame is Frame.Text) {
                            println("lobby join ${frame.readText()}")
                            val joinInfo = Gson().fromJson(frame.readText(),JoinRoom::class.java)


                            val newSocketUser = newSocketUser(UUID.randomUUID().toString(),joinInfo.nickname,true)
                            joinRoom(newSocketUser,joinInfo.roomId)
                            send(Gson().toJson(newSocketUser))
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


}