package com.akashbakshi.models

import com.akashbakshi.GameState

class Session {
    var isActive:Boolean
    var standings: Pair<String,Int>
    var room: GameRoom
    var roomState: GameState

    constructor(active:Boolean,standings:Pair<String,Int>,gameRoom:GameRoom,state:GameState){
        this.isActive = active
        this.standings = standings
        this.room = gameRoom
        this.roomState = state
    }
}