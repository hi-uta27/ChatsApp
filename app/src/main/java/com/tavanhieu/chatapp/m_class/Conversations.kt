package com.tavanhieu.chatapp.m_class

import java.util.*

class Conversations {
    var hoTen: String? = null
    var content: String? = null
    var timeSent: Date? = null
    var uid: String? = null
    var image: String? = null

    constructor()
    constructor(hoTen: String, content: String, timeSent: Date, uid: String, image: String?){
        this.hoTen = hoTen
        this.content = content
        this.timeSent = timeSent
        this.uid = uid
        this.image = image
    }
}