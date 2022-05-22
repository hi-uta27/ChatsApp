package com.tavanhieu.chatapp.m_class
import java.io.Serializable

class User: Serializable {
    var taiKhoan: String? = null
    var hoTen: String? = null
    var uid: String? = null
    var ngaySinh: String? = null
    var gioiTinh: String? = null
    var trangThaiHoatDong: Int? = null
    var anh: String? = null
    var token: String? = null

    constructor()
    constructor(taiKhoan: String, hoTen: String, uid: String, trangThaiHoatDong: Int,
                ngaySinh: String, gioiTinh: String, anh: String?, token: String?) {
        this.taiKhoan   = taiKhoan
        this.hoTen      = hoTen
        this.uid        = uid
        this.ngaySinh   = ngaySinh
        this.gioiTinh   = gioiTinh
        this.anh        = anh
        this.token      = token
        this.trangThaiHoatDong  = trangThaiHoatDong
    }
    //Cập nhật dữ liệu:
    constructor(hoTen: String, ngaySinh: String, gioiTinh: String) {
        this.hoTen      = hoTen
        this.ngaySinh   = ngaySinh
        this.gioiTinh   = gioiTinh
    }
}