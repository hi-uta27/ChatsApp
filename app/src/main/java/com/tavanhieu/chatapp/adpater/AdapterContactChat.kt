package com.tavanhieu.chatapp.adpater

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import com.tavanhieu.chatapp.R
import com.tavanhieu.chatapp.activity.FriendMessageActivity
import com.tavanhieu.chatapp.m_class.HangSo
import com.tavanhieu.chatapp.m_class.User
import de.hdodenhof.circleimageview.CircleImageView

class AdapterContactChat(var context: Context): RecyclerView.Adapter<AdapterContactChat.MyViewHolder>() {
    private lateinit var arr: ArrayList<User>

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imgUser: CircleImageView = itemView.findViewById(R.id.imgUserContactChat)
        var viewStatus: View         = itemView.findViewById(R.id.viewStatusUserContactChat)
        var txtUserName: TextView    = itemView.findViewById(R.id.txtUserNameContactChat)
        var txtEmailUser: TextView   = itemView.findViewById(R.id.txtEmailContactChat)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(arr: ArrayList<User>) {
        this.arr = arr
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val mView = LayoutInflater.from(context).inflate(R.layout.item_user_contact, parent, false)
        return MyViewHolder(mView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val res = arr[position]
        holder.txtUserName.text = res.hoTen
        holder.txtEmailUser.text = res.taiKhoan

        //Trạng thái hoạt động
        if(res.available == 1)
            holder.viewStatus.visibility = View.VISIBLE
        else
            holder.viewStatus.visibility = View.GONE

        //Ảnh người dùng:
        FirebaseStorage.getInstance().reference.child(HangSo.KEY_USER)
            .child(res.uid!!)
            .downloadUrl
            .addOnSuccessListener {
                if(it.toString() != "" && it.toString().isNotEmpty())
                    Picasso.get().load(it).into(holder.imgUser)
            }

        holder.itemView.setOnClickListener {
            val intent = Intent(context, FriendMessageActivity::class.java)
            intent.putExtra("hoTen", res.hoTen)
            intent.putExtra("receiverId", res.uid)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return arr.size
    }
}