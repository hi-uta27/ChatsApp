package com.tavanhieu.chatapp.adpater

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.tavanhieu.chatapp.R
import com.tavanhieu.chatapp.activity.DangNhapDangKyActivity
import com.tavanhieu.chatapp.cai_dat.ThongTinCaNhan
import com.tavanhieu.chatapp.m_class.ItemCaiDat


class AdapterListCaiDat(var context: Context): RecyclerView.Adapter<AdapterListCaiDat.MyViewHolder>() {
    private lateinit var arr: ArrayList<ItemCaiDat>

    //Ánh xạ item trong ItemCaiDat
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var iconItem: ImageView = itemView.findViewById(R.id.imgIconItemCaiDat)
        var nameItem: TextView  = itemView.findViewById(R.id.txtNameItemCaiDat)
    }

    //Khởi tạo mảng dữ liệu, và cập nhật dữ liệu khi có thay đổi từ mảng
    @SuppressLint("NotifyDataSetChanged")
    fun setData(arr: ArrayList<ItemCaiDat>) {
        this.arr = arr
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val mView = LayoutInflater.from(context).inflate(R.layout.item_setting, parent, false)
        return MyViewHolder(mView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = arr[position]

        holder.iconItem.setImageResource(item.img)
        holder.nameItem.text = item.text

        holder.itemView.setOnClickListener {
            when(position) {
                0 -> {
                    context.startActivity(Intent(context, ThongTinCaNhan::class.java))
                }
                1 -> {
                    Toast.makeText(context, "Thông tin ứng dụng", Toast.LENGTH_SHORT).show()
                }
                2 -> {
                    Toast.makeText(context, "Ngôn ngữ", Toast.LENGTH_SHORT).show()
                }
                3 -> {
                    Toast.makeText(context, "Trợ giúp", Toast.LENGTH_SHORT).show()
                }
                4 -> {
                    Toast.makeText(context, "Cài đặt", Toast.LENGTH_SHORT).show()
                }
                5 -> {
                    //Đóng màn hình:
                    (context as Activity).finish()
                    //Mở màn hình đăng nhập
                    context.startActivity(Intent(context, DangNhapDangKyActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                    FirebaseAuth.getInstance().signOut()
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return arr.size
    }
}