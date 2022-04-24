package com.tavanhieu.chatapp.activity

import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.tavanhieu.chatapp.m_class.HangSo

open class UserActiveActivity: AppCompatActivity() {
    //List user active:
    private val db = Firebase.database.reference.child(HangSo.KEY_USER)
        .child(FirebaseAuth.getInstance().currentUser?.uid!!)
        .child(HangSo.KEY_AVAILABLE)

    override fun onPause() {
        super.onPause()
        db.setValue(0)
    }

    override fun onResume() {
        super.onResume()
        db.setValue(1)
    }
}