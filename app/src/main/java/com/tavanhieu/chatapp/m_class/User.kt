package com.tavanhieu.chatapp.m_class

import java.io.Serializable

class User: Serializable {
    var taiKhoan: String? = null
    var hoTen: String? = null
    var uid: String? = null
    var ngaySinh: String? = null
    var gioiTinh: String? = null
    var available: Int? = null
    var image: String? = null
    var token: String? = null

    constructor()
    constructor(taiKhoan: String, hoTen: String, uid: String, available: Int,
                ngaySinh: String, gioiTinh: String, image: String?, token: String?) {
        this.taiKhoan   = taiKhoan
        this.hoTen      = hoTen
        this.uid        = uid
        this.available  = available
        this.ngaySinh   = ngaySinh
        this.gioiTinh   = gioiTinh
        this.image      = image
        this.token      = token
    }
    //Cập nhật dữ liệu:
    constructor(hoTen: String, ngaySinh: String, gioiTinh: String) {
        this.hoTen      = hoTen
        this.ngaySinh   = ngaySinh
        this.gioiTinh   = gioiTinh
    }
}