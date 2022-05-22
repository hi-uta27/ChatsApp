package com.tavanhieu.chatapp.m_class
import java.util.*

class Conversations {
    var hoTen: String? = null
    var noiDungTinNhan: String? = null
    var thoiGianGui: Date? = null
    var uid: String? = null
    var anh: String? = null

    constructor()
    constructor(hoTen: String, noiDungTinNhan: String, thoiGianGui: Date, uid: String, anh: String?){
        this.hoTen          = hoTen
        this.noiDungTinNhan = noiDungTinNhan
        this.thoiGianGui    = thoiGianGui
        this.uid            = uid
        this.anh            = anh
    }
}