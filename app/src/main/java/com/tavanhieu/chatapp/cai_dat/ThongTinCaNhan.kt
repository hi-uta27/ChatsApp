package com.tavanhieu.chatapp.cai_dat

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ca_nhan_info)
        anhXa()
        mOnClick()
        hienThiNhatThongTinCaNhan()
    }

    private fun hienThiNhatThongTinCaNhan() {
        //load ảnh:
        FirebaseStorage.getInstance().reference
            .child("${HangSo.KEY_USER}/${FirebaseAuth.getInstance().currentUser?.uid!!}")
            .downloadUrl.addOnSuccessListener {
                Picasso.get().load(it).into(imgUser)
            }.addOnFailureListener {
                Toast.makeText(this, "Chưa có ảnh để hiển thị", Toast.LENGTH_SHORT).show()
            }
        //Lấy user hiện tại:
        Firebase.database.reference
            .child(HangSo.KEY_USER)
            .addValueEventListener(object: ValueEventListener {
                @SuppressLint("NotifyDataSetChanged")
                override fun onDataChange(snapshot: DataSnapshot) {
                    for(data in snapshot.children) {
                        val user = data.getValue(User::class.java)!!
                        if(FirebaseAuth.getInstance().currentUser?.uid == user.uid) {
                            //Set thông tin:
                            txtUserName.text = user.hoTen
                            edtGmail.setText(user.taiKhoan)
                            edtHoTen.setText(user.hoTen)
                            edtNgaySinh.setText(user.ngaySinh)
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

    private fun mOnClick() {
        btnChinhSua.setOnClickListener {
            edtHoTen.isEnabled    = true
            edtNgaySinh.isEnabled = true
            cbNam.isEnabled       = true
            cbNu.isEnabled        = true
        }
        btnCapNhat.setOnClickListener {
            edtHoTen.isEnabled    = false
            edtNgaySinh.isEnabled = false
            cbNam.isEnabled       = false
            cbNu.isEnabled        = false
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
            val db = Firebase.database.reference.child(HangSo.KEY_USER).child(FirebaseAuth.getInstance().currentUser?.uid!!)
            db.child("hoTen").setValue(hoTen)
            db.child("ngaySinh").setValue(ngaySinh)
            db.child("gioiTinh").setValue(gioiTinh)
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

    private var mGalleryStartActivityForResult = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        try {
            imgUser.setImageURI(uri)
            FirebaseStorage.getInstance().reference.child(HangSo.KEY_USER)
                .child(FirebaseAuth.getInstance().currentUser?.uid!!)
                .putFile(uri!!)
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