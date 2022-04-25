package com.tavanhieu.chatapp.m_class

import java.util.*

class Message {
    var time: Date?         = null
    var mess: String?       = null
    var senderId: String?   = null

    constructor()
    constructor(time: Date, mess: String, senderId: String) {
        this.mess = mess
        this.time = time
        this.senderId = senderId
    }
}
