package com.tavanhieu.chatapp.adpater

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.tavanhieu.chatapp.R
import com.tavanhieu.chatapp.activity.FriendMessageActivity
import com.tavanhieu.chatapp.m_class.User
import de.hdodenhof.circleimageview.CircleImageView

class AdapterUserActive(var context: Context): RecyclerView.Adapter<AdapterUserActive.MyViewHolder>() {
    private lateinit var arr: ArrayList<User>

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imgUserActive: CircleImageView  = itemView.findViewById(R.id.imgUserActiveChatMain)
        var txtNameUserActive: TextView     = itemView.findViewById(R.id.txtNameUserActiveChatMain)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(arr: ArrayList<User>) {
        this.arr = arr
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val mView = LayoutInflater.from(context).inflate(R.layout.item_user_active, parent, false)
        return MyViewHolder(mView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val user = arr[position]

        holder.txtNameUserActive.visibility = View.VISIBLE
        holder.txtNameUserActive.text = user.hoTen
        if(user.anh != null)
            Picasso.get().load(user.anh).into(holder.imgUserActive)

        holder.itemView.setOnClickListener { openMessageActivity(user) }
        holder.imgUserActive.setOnClickListener { openMessageActivity(user) }
        holder.txtNameUserActive.setOnClickListener { openMessageActivity(user) }
    }

    private fun openMessageActivity(res: User) {
        val intent = Intent(context, FriendMessageActivity::class.java)
        intent.putExtra("hoTen", res.hoTen)
        intent.putExtra("receiverId", res.uid)
        context.startActivity(intent)
    }

    override fun getItemCount(): Int {
        return arr.size
    }
}