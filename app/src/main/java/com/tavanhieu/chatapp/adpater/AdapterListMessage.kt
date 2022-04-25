package com.tavanhieu.chatapp.adpater

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import com.tavanhieu.chatapp.m_class.HangSo
import com.tavanhieu.chatapp.R
import com.tavanhieu.chatapp.m_class.Message
import de.hdodenhof.circleimageview.CircleImageView
import java.text.SimpleDateFormat
import kotlin.collections.ArrayList

class AdapterListMessage(var context: Context): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private lateinit var arr: ArrayList<Message>

    @SuppressLint("NotifyDataSetChanged")
    fun setData(arr: ArrayList<Message>) {
        this.arr = arr
        notifyDataSetChanged()
    }

    class MySentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var txtMyMess: TextView         = itemView.findViewById(R.id.txtUserSendMessage)
        var txtTime: TextView           = itemView.findViewById(R.id.txtTimeSendMessage)
    }

    class MyReceiverViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imgFriend: CircleImageView? = itemView.findViewById(R.id.imgUserItemMessage)
        var txtFriend: TextView         = itemView.findViewById(R.id.txtUserReceiverItemMessage)
        var txtTime: TextView           = itemView.findViewById(R.id.txtTimeReceiverMessage)
    }

    override fun getItemCount(): Int {
        return arr.size
    }

    override fun getItemViewType(position: Int): Int {
        return if(FirebaseAuth.getInstance().currentUser?.uid.equals(arr[position].senderId)) {
            HangSo.GUI_MESSAGE
        } else {
            HangSo.NHAN_MESSAGE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if(viewType == HangSo.GUI_MESSAGE) {
            val mView = LayoutInflater.from(context).inflate(R.layout.item_sent_message, parent, false)
            MySentViewHolder(mView)
        } else {
            val mView = LayoutInflater.from(context).inflate(R.layout.item_receiver_message, parent, false)
            MyReceiverViewHolder(mView)
        }
    }

    @SuppressLint("SimpleDateFormat")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val mess = arr[position]
        //Định dạng ngày và giờ gửi:
        val simpleDateFormat = SimpleDateFormat("HH:mm MMMM dd,yyyy")

        if(getItemViewType(position) == HangSo.GUI_MESSAGE) {
            var checkTimeSent = true
            val mHolder = (holder as MySentViewHolder)

            //Gán dữ liệu:
            mHolder.txtMyMess.text = mess.mess
            mHolder.txtTime.text   = simpleDateFormat.format(mess.time!!)
            //Xem thời gian gửi tin:
            holder.txtMyMess.setOnClickListener {
                if(checkTimeSent) {
                    checkTimeSent = false
                    mHolder.txtTime.visibility = View.VISIBLE
                } else {
                    checkTimeSent = true
                    mHolder.txtTime.visibility = View.GONE
                }
            }
        } else {
            var checkTimeSent = true
            val mHolder = (holder as MyReceiverViewHolder)

            //Gán dữ liệu:
            mHolder.txtFriend.text = mess.mess
            mHolder.txtTime.text   = simpleDateFormat.format(mess.time!!)
            //Xem thời gian gửi tin:
            holder.txtFriend.setOnClickListener {
                if(checkTimeSent) {
                    checkTimeSent = false
                    mHolder.txtTime.visibility = View.VISIBLE
                } else {
                    checkTimeSent = true
                    mHolder.txtTime.visibility = View.GONE
                }
            }
            //gán ảnh người nhận:
            FirebaseStorage.getInstance().reference.child(HangSo.KEY_USER)
                .child(mess.senderId!!).downloadUrl
                .addOnSuccessListener {
                    Picasso.get().load(it).into(mHolder.imgFriend)
                }
                .addOnFailureListener {}
        }
    }
}