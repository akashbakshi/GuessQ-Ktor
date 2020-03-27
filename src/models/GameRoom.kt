package com.akashbakshi.models

import com.akashbakshi.SocketUser
import com.akashbakshi.GameState

class GameRoom {
    val roomId:String
    val host:SocketUser
    var players:ArrayList<SocketUser>

    constructor(id:String,host:SocketUser,players:ArrayList<SocketUser>){
        this.roomId = id
        this.host = host
        this.players = players
    }
}