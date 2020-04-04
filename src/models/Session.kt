package com.akashbakshi.models

import com.akashbakshi.GameState
import java.util.*

class Session {
    val sessionId:String = UUID.randomUUID().toString()
    var isActive:Boolean
    var standings:ArrayList<Pair<String,Int>>
    var room: GameRoom
    var roomState: GameState
    var createdAt: Date
    constructor(active:Boolean,standings: ArrayList<Pair<String,Int>>,gameRoom:GameRoom,state:GameState){
        this.isActive = active
        this.standings = standings
        this.room = gameRoom
        this.roomState = state
        this.createdAt = Date()
    }
}