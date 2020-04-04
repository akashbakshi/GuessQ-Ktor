package com.akashbakshi.modules


import com.akashbakshi.SocketUser
import com.akashbakshi.models.GameRoom
import com.akashbakshi.newSocketUser
import com.akashbakshi.sessions
import com.google.gson.Gson
import io.ktor.application.Application
import io.ktor.http.cio.websocket.*
import io.ktor.routing.*
import io.ktor.websocket.*
import kotlinx.coroutines.channels.ClosedReceiveChannelException
import java.util.*



fun joinRoom(newUser:SocketUser,roomId:String){
    sessions.forEach {
        if(it.room.roomId == roomId){
            it.room.players.add(newUser)
            return@forEach
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
                    println("disconnected")
                    println(e)
                } catch (e: Throwable) {
                    e.printStackTrace()
                }
            }
            webSocket ("/leave"){
                try{
                    while (true) {
                        val frame = incoming.receive()
                        if (frame is Frame.Text) {
                            println("lobby leave ${frame.readText()}")
                            val joinInfo = Gson().fromJson(frame.readText(),JoinRoom::class.java)

                        }
                    }
                } catch (e: ClosedReceiveChannelException) {
                    println("disconnecting...")
                    println(e)
                } catch (e: Throwable) {
                    e.printStackTrace()
                }
            }
        }
    }


}