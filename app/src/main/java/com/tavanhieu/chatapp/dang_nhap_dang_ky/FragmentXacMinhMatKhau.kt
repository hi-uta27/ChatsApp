package com.tavanhieu.chatapp.dang_nhap_dang_ky

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.tavanhieu.chatapp.R
import com.tavanhieu.chatapp.m_class.HangSo
import com.tavanhieu.chatapp.m_class.User

class FragmentXacMinhMatKhau: Fragment() {
    private lateinit var mView: View
    private lateinit var edtAccount: EditText
    private lateinit var txtDangNhap: TextView
    private lateinit var btnXacNhan: Button
    private lateinit var imgBack: ImageView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mView = inflater.inflate(R.layout.forgetpass_xac_minh, container, false)
        anhXa()
        mOnClick()
        return mView
    }

    private fun anhXa() {
        edtAccount  = mView.findViewById(R.id.txtInputEdtAccountForgetPassXacMinh)
        txtDangNhap = mView.findViewById(R.id.txtNewAccountForgetPassXacMinh)
        btnXacNhan  = mView.findViewById(R.id.btnConfirmForgetPassXacMinh)
        imgBack     = mView.findViewById(R.id.imgArrowBackToolBarForgetPass)
    }

    private fun mOnClick() {
        val supportFragment = requireActivity().supportFragmentManager.beginTransaction()
        imgBack.setOnClickListener { requireActivity().onBackPressed() }

        btnXacNhan.setOnClickListener {
            //Thay đổi mật khẩu:
            val email = edtAccount.text.toString().trim()
            if(email.isEmpty() || email == "") {
                edtAccount.error = "Không được để trống"
                edtAccount.requestFocus()
            } else if(!email.matches(HangSo.REGEX_EMAIL.toRegex())) {
                edtAccount.error = "Email không hợp lệ"
                edtAccount.requestFocus()
            } else {
                FirebaseAuth.getInstance().sendPasswordResetEmail(email).addOnCompleteListener {
                    if(it.isSuccessful) {
                        Toast.makeText(requireContext(), "Kiểm tra email của bạn", Toast.LENGTH_SHORT).show()
                        requireActivity().supportFragmentManager.popBackStack("DangNhap", FragmentManager.POP_BACK_STACK_INCLUSIVE)
                        supportFragment.replace(R.id.fragmentDangNhapDangKy, FragmentDangNhap()).commit()
                    } else {
                        Toast.makeText(requireContext(), "Thử lại! Có lỗi xảy ra", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        txtDangNhap.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack("DangNhap", FragmentManager.POP_BACK_STACK_INCLUSIVE)
            supportFragment.replace(R.id.fragmentDangNhapDangKy, FragmentDangNhap()).commit()
        }
    }
}