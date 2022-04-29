package com.tavanhieu.chatapp.adpater

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.Picasso
import com.tavanhieu.chatapp.R
import com.tavanhieu.chatapp.activity.FriendMessageActivity
import com.tavanhieu.chatapp.m_class.Conversations
import com.tavanhieu.chatapp.m_class.User
import de.hdodenhof.circleimageview.CircleImageView
import java.text.SimpleDateFormat

class AdapterListChatMain(var context: Context): RecyclerView.Adapter<AdapterListChatMain.ViewHolder>() {
    private lateinit var arr: ArrayList<Conversations>

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imgUserItemChatApp: CircleImageView? = itemView.findViewById(R.id.imgUserItemChatApp)
        var viewStatusUserItemChatApp: View      = itemView.findViewById(R.id.viewStatusUserItemChatApp)
        var txtUserNameItemChatApp: TextView     = itemView.findViewById(R.id.txtUserNameItemChatApp)
        var txtContentMessItemChatApp: TextView  = itemView.findViewById(R.id.txtContentMessItemChatApp)
        var txtTimeItemChatApp: TextView         = itemView.findViewById(R.id.txtTimeItemChatApp)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(arr: ArrayList<Conversations>) {
        this.arr = arr
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val mView = LayoutInflater.from(context).inflate(R.layout.item_chat_app, parent, false)
        return ViewHolder(mView)
    }

    @SuppressLint("SimpleDateFormat", "SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val res = arr[position]
        val simpleDateFormat = SimpleDateFormat("HH:mm")

        holder.viewStatusUserItemChatApp.visibility = View.GONE
        holder.txtUserNameItemChatApp.text = res.hoTen
        holder.txtTimeItemChatApp.text = simpleDateFormat.format(res.timeSent!!)
        //Hiển thị ảnh:
        if(res.image != null)
            Picasso.get().load(res.image).into(holder.imgUserItemChatApp)

        //Nội dung nhắn:
        if(FirebaseAuth.getInstance().currentUser?.uid != res.uid)
            holder.txtContentMessItemChatApp.text = res.content
        else
            holder.txtContentMessItemChatApp.text = "Bạn: ${res.content}"

        //Chuyển sang màn hình chat
        holder.itemView.setOnClickListener { openMessageActivity(res) }
        holder.imgUserItemChatApp?.setOnClickListener { openMessageActivity(res) }
        holder.txtContentMessItemChatApp.setOnClickListener { openMessageActivity(res) }
        holder.txtUserNameItemChatApp.setOnClickListener { openMessageActivity(res) }
    }

    private fun openMessageActivity(res: Conversations) {
        val intent = Intent(context, FriendMessageActivity::class.java)
        intent.putExtra("hoTen", res.hoTen)
        intent.putExtra("receiverId", res.uid)
        context.startActivity(intent)
    }

    override fun getItemCount(): Int {
        return arr.size
    }
}