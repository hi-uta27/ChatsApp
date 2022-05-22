package com.tavanhieu.chatapp.fragment_trang_chu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import com.tavanhieu.chatapp.m_class.ItemCaiDat
import com.tavanhieu.chatapp.R
import com.tavanhieu.chatapp.adpater.AdapterListCaiDat
import com.tavanhieu.chatapp.m_class.HangSo
import com.tavanhieu.chatapp.m_class.User
import de.hdodenhof.circleimageview.CircleImageView

class FragmentSettingChat: Fragment() {
    private lateinit var mView: View
    private lateinit var txtUserName: TextView
    private lateinit var imgUser: CircleImageView
    private lateinit var rcvList: RecyclerView
    private lateinit var mAdapter: AdapterListCaiDat
    private var arr = ArrayList<ItemCaiDat>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        mView = inflater.inflate(R.layout.cai_dat, container, false)
        anhXa()
        //Gán dữ liệu:
        docDuLieuUser()
        //Thêm danh sách cài đặt:
        addItemCaiDat()
        //Ánh xạ view cho danh sách cài đặt:
        mAdapter = AdapterListCaiDat(requireContext())
        mAdapter.setData(arr)
        rcvList.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        rcvList.adapter = mAdapter

        return mView
    }

    private fun anhXa() {
        imgUser     = mView.findViewById(R.id.imgUserCaiDat)
        txtUserName = mView.findViewById(R.id.txtUserNameCaiDat)
        rcvList     = mView.findViewById(R.id.recycleViewListCaiDat)
    }

    private fun docDuLieuUser() {
        val uid = FirebaseAuth.getInstance().currentUser?.uid
        //Thông tin user:
        Firebase.database.reference.child(HangSo.KEY_USER)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (data in snapshot.children) {
                        val user = data.getValue(User::class.java)
                        if (user?.uid == uid) {
                            txtUserName.text = user?.hoTen
                            if(user?.anh != null)
                                Picasso.get().load(user.anh).placeholder(R.drawable.user_default).into(imgUser)
                            break
                        }
                    }
                }
                override fun onCancelled(error: DatabaseError) {}
            })
    }

    private fun addItemCaiDat() {
        arr.add(ItemCaiDat(R.drawable.information, "Thông tin cá nhân"))
        arr.add(ItemCaiDat(R.drawable.ic_information, "Thông tin ứng dụng"))
        arr.add(ItemCaiDat(R.drawable.ic_language, "Ngôn ngữ"))
        arr.add(ItemCaiDat(R.drawable.m_help, "Trợ giúp"))
        arr.add(ItemCaiDat(R.drawable.ic_settings, "Cài đặt"))
        arr.add(ItemCaiDat(R.drawable.log_out, "Đăng xuất"))
    }
}