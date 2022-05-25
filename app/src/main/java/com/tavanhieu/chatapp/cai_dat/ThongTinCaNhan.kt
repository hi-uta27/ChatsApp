package com.tavanhieu.chatapp.cai_dat

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import com.tavanhieu.chatapp.R
import com.tavanhieu.chatapp.activity.UserActiveActivity
import com.tavanhieu.chatapp.m_class.HangSo
import com.tavanhieu.chatapp.m_class.User
import de.hdodenhof.circleimageview.CircleImageView
import java.lang.Exception

class ThongTinCaNhan: UserActiveActivity() {
    private lateinit var imgBackground: ImageView
    private lateinit var imgBack:       ImageView
    private lateinit var imgCamera:     ImageView
    private lateinit var imgUser:       CircleImageView
    private lateinit var txtUserName:   TextView
    private lateinit var edtGmail:      EditText
    private lateinit var edtHoTen:      EditText
    private lateinit var edtNgaySinh:   EditText
    private lateinit var cbNam:         CheckBox
    private lateinit var cbNu:          CheckBox
    private lateinit var btnChinhSua:   Button
    private lateinit var btnCapNhat:    Button
    private val db = Firebase.database.reference.child(HangSo.KEY_USER)
    private val uid = FirebaseAuth.getInstance().currentUser?.uid!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ca_nhan_info)
        anhXa()
        mOnClick()
        hienThiNhatThongTinCaNhan()
    }

    private fun hienThiNhatThongTinCaNhan() {
        //Lấy user hiện tại:
        db.addValueEventListener(object: ValueEventListener {
                @SuppressLint("NotifyDataSetChanged")
                override fun onDataChange(snapshot: DataSnapshot) {
                    for(data in snapshot.children) {
                        val user = data.getValue(User::class.java)!!
                        if(uid == user.uid) {
                            //Set thông tin:
                            txtUserName.text = user.hoTen
                            edtGmail.setText(user.taiKhoan)
                            edtHoTen.setText(user.hoTen)
                            edtNgaySinh.setText(user.ngaySinh)
                            if(user.anh != null)
                                Picasso.get().load(user.anh).placeholder(R.drawable.user_default).into(imgUser)
                            when {
                                user.gioiTinh == "" -> {
                                    cbNam.isChecked = false
                                    cbNu.isChecked = false }
                                user.gioiTinh.equals("nam", ignoreCase = true) -> { cbNam.isChecked = true }
                                else -> { cbNu.isChecked = true }
                            }
                            break
                        }
                    }
                }
                override fun onCancelled(error: DatabaseError) { }
            })
    }

    private fun anhXa() {
        imgBackground   = findViewById(R.id.imgBackgroundCaNhan)
        imgBack         = findViewById(R.id.imgArrowBackToolBarCaNhan)
        imgCamera       = findViewById(R.id.imgButtonCameraCaNhan)
        imgUser         = findViewById(R.id.imgUserCaNhan)
        txtUserName     = findViewById(R.id.txtUserNameCaNhan)
        edtGmail        = findViewById(R.id.txtGmailCaNhan)
        edtHoTen        = findViewById(R.id.txtHoTenCaNhan)
        edtNgaySinh     = findViewById(R.id.txtNgaySinhCaNhan)
        cbNam           = findViewById(R.id.cbNamCaNhan)
        cbNu            = findViewById(R.id.cbNuCaNhan)
        btnCapNhat      = findViewById(R.id.btnCapNhatCaNhan)
        btnChinhSua     = findViewById(R.id.btnChinhSuaCaNhan)
    }

    @SuppressLint("ResourceAsColor")
    private fun mOnClick() {
        btnChinhSua.setOnClickListener {
            //Cho phép chỉnh sửa:
            edtHoTen.isEnabled    = true
            edtNgaySinh.isEnabled = true
            cbNam.isEnabled       = true
            cbNu.isEnabled        = true
            //Chuyển màu chữ:
            edtHoTen.setTextColor(Color.parseColor("#FF000000"))
            edtNgaySinh.setTextColor(Color.parseColor("#FF000000"))
        }
        btnCapNhat.setOnClickListener {
            //Tắt cho phép chỉnh sửa:
            edtHoTen.isEnabled    = false
            edtNgaySinh.isEnabled = false
            cbNam.isEnabled       = false
            cbNu.isEnabled        = false
            //Chuyển màu chữ:
            edtHoTen.setTextColor(Color.parseColor("#AEAEAE"))
            edtNgaySinh.setTextColor(Color.parseColor("#AEAEAE"))

            capNhatDuLieuLenFirebase()
        }
        cbNam.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked)
                cbNu.isChecked = false
        }
        cbNu.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked)
                cbNam.isChecked = false
        }
        imgBack.setOnClickListener {
            onBackPressed()
        }
        imgCamera.setOnClickListener {
            openGallery()
        }
    }

    private fun capNhatDuLieuLenFirebase() {
        //Lấy dữ liệu từ các editText để chỉnh sửa:
        val hoTen    = edtHoTen.text.trim().toString()
        val ngaySinh = edtNgaySinh.text.trim().toString()
        var gioiTinh = ""
        if(cbNam.isChecked) {
            gioiTinh = "nam"
        } else if(cbNu.isChecked) {
            gioiTinh = "nu"
        }
        //Kiểm tra dữ liệu:
        if(hoTen.isEmpty() || hoTen == "") {
            edtHoTen.error = "Không được để trống"
        } else if(ngaySinh.isEmpty() || ngaySinh == "") {
            edtNgaySinh.error = "Không được để trống"
        } else {
            //Cập nhật user: (họ tên, ngày sinh, giới tính)
            db.child(uid).child("hoTen").setValue(hoTen)
            db.child(uid).child("ngaySinh").setValue(ngaySinh)
            db.child(uid).child("gioiTinh").setValue(gioiTinh)
            Toast.makeText(this, "Cập nhật thành công", Toast.LENGTH_SHORT).show()
        }
    }

    @SuppressLint("ObsoleteSdkInt")
    private fun openGallery() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), HangSo.REQUEST_CODE_GALLERY)
            } else {
                pickImageFromGallery()
            }
        } else {
            pickImageFromGallery()
        }
    }

    //Khai báo và nhận kết quả trả về từ thư viện:
    private var mGalleryStartActivityForResult = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        try {
            //cập nhật ảnh:
            Picasso.get().load(uri).into(imgUser)
            //Cập nhật ảnh lên FireStorage:
            FirebaseStorage.getInstance().reference.child(HangSo.KEY_USER)
                .child(uid)
                .putFile(uri!!)
            //Cập nhật Uri ảnh từ Firebase Storage về RealTimeDB để tăng hiệu suất load ảnh:
            FirebaseStorage.getInstance().reference.child(HangSo.KEY_USER)
                .child(uid).downloadUrl
                .addOnSuccessListener {
                    //Lưu vào realTime:
                    db.child(uid).child("anh").setValue(it.toString())
                    Toast.makeText(this, "Cập nhật ảnh thành công", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Lỗi cập nhật ảnh", Toast.LENGTH_SHORT).show()
                }
        } catch (ex: Exception) {}
    }

    private fun pickImageFromGallery() {
        mGalleryStartActivityForResult.launch("image/*")
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode) {
            HangSo.REQUEST_CODE_GALLERY -> {
                if(requestCode > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    pickImageFromGallery()
                } else {
                    Toast.makeText(this, "Bạn không cho phép mở album", Toast.LENGTH_SHORT).show()
                }
            }
            else -> {
                super.onRequestPermissionsResult(requestCode, permissions, grantResults)
            }
        }
    }
}