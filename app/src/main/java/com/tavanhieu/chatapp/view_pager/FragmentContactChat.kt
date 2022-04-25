package com.tavanhieu.chatapp.view_pager

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.tavanhieu.chatapp.R
import com.tavanhieu.chatapp.adpater.AdapterContactChat
import com.tavanhieu.chatapp.m_class.HangSo
import com.tavanhieu.chatapp.m_class.User

class FragmentContactChat: Fragment() {
    private lateinit var rcvListUser: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var mView: View
    private lateinit var mAdapter: AdapterContactChat
    private var arr = ArrayList<User>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        mView = inflater.inflate(R.layout.activity_contact_chat, container, false)
        rcvListUser = mView.findViewById(R.id.listUserContactChat)
        progressBar = mView.findViewById(R.id.progressBarContactChat)
        docListUser()

        //Ánh xạ view cho list người dùng:
        mAdapter = AdapterContactChat(requireContext())
        mAdapter.setData(arr)
        rcvListUser.adapter = mAdapter

        return mView
    }

    private fun docListUser() {
        progressBar.visibility = View.VISIBLE
        //Lấy ra user trong hệ thống:
        Firebase.database.reference
            .child(HangSo.KEY_USER)
            .addValueEventListener(object: ValueEventListener {
                @SuppressLint("NotifyDataSetChanged")
                override fun onDataChange(snapshot: DataSnapshot) {
                    arr.clear()
                    for(data in snapshot.children) {
                        val user = data.getValue(User::class.java)
                        if(FirebaseAuth.getInstance().currentUser?.uid != user?.uid)
                            arr.add(user!!)
                    }
                    mAdapter.notifyDataSetChanged()
                    //Sắp xếp tăng dần theo tên:
                    arr.sortBy { obj -> obj.hoTen }
                    progressBar.visibility = View.GONE
                }
                override fun onCancelled(error: DatabaseError) { }
            })
    }
}