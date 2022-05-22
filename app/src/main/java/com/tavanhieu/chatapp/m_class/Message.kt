package com.tavanhieu.chatapp.m_class
import java.util.*

class Message {
    var thoiGianGui: Date?         = null
    var noiDungTinNhan: String?    = null
    var uid: String?               = null
    var anh: String?               = null

    constructor()
    constructor(thoiGianGui: Date, noiDungTinNhan: String, uid: String, anh: String?) {
        this.noiDungTinNhan = noiDungTinNhan
        this.thoiGianGui    = thoiGianGui
        this.uid            = uid
        this.anh            = anh
    }
}
