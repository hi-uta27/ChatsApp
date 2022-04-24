package com.tavanhieu.chatapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.tavanhieu.chatapp.R
import com.tavanhieu.chatapp.dang_nhap_dang_ky.FragmentDangNhap

class DangNhapDangKyActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dang_nhap_dang_ky)
        supportFragmentManager.beginTransaction().replace(R.id.fragmentDangNhapDangKy, FragmentDangNhap()).commit()
    }
}