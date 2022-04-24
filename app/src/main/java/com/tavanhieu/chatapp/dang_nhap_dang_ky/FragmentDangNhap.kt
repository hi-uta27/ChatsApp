package com.tavanhieu.chatapp.dang_nhap_dang_ky

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.tavanhieu.chatapp.activity.MainActivity
import com.tavanhieu.chatapp.R

class FragmentDangNhap: Fragment() {
    private lateinit var mView: View
    private lateinit var edtAccount: EditText
    private lateinit var edtPassWord: EditText
    private lateinit var cbNhoMatKhau: CheckBox
    private lateinit var txtQuenMatKhau: TextView
    private lateinit var txtTaoTaiKhoan: TextView
    private lateinit var btnDangNhap: Button
    private lateinit var imgLoginFaceBook: ImageView
    private lateinit var imgLoginGoogle: ImageView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        mView = inflater.inflate(R.layout.dang_nhapp, container, false)
        anhXa()

        //Tự động đăng nhập nếu đã đăng nhập trước đó:
        val authCurrent = FirebaseAuth.getInstance().currentUser
        if(authCurrent != null) {
            startActivity(Intent(requireContext(), MainActivity::class.java))
            requireActivity().finish()
        }
        mOnClick()
        return mView
    }

    private fun anhXa() {
        edtAccount      = mView.findViewById(R.id.txtInputEdtAccountDangNhap)
        edtPassWord     = mView.findViewById(R.id.txtInputEdtPassWord)
        cbNhoMatKhau    = mView.findViewById(R.id.checkRememberAccount)
        txtQuenMatKhau  = mView.findViewById(R.id.txtForgetPasswordDangNhap)
        txtTaoTaiKhoan  = mView.findViewById(R.id.txtNewAccountDangNhap)
        btnDangNhap     = mView.findViewById(R.id.btnSignInDangNhap)
        imgLoginFaceBook = mView.findViewById(R.id.imgLoginFacebookDangNhap)
        imgLoginGoogle   = mView.findViewById(R.id.imgLoginGoogleDangNhap)
    }

    private fun mOnClick() {
        val supportFragment = requireActivity().supportFragmentManager.beginTransaction()
        btnDangNhap.setOnClickListener {
            val account  = edtAccount.text.trim().toString()
            val password = edtPassWord.text.trim().toString()
            if(account.isEmpty() || account == "") {
                edtAccount.error = "Không được để trống"
            } else if(password.isEmpty() || password == "") {
                edtPassWord.error = "Không được để trống"
            } else {
                login(account, password)
            }
        }

        txtQuenMatKhau.setOnClickListener {
            supportFragment.replace(R.id.fragmentDangNhapDangKy, FragmentThayDoiMatKhau())
                .addToBackStack("DangNhap").commit()
        }

        txtTaoTaiKhoan.setOnClickListener {
            supportFragment.replace(R.id.fragmentDangNhapDangKy, FragmentDangKy())
                .addToBackStack("DangNhap").commit()
        }
    }

    private fun login(account: String, password: String) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(account, password)
            .addOnCompleteListener(requireActivity()) {task ->
                if(task.isSuccessful) {
                    startActivity(Intent(requireContext(), MainActivity::class.java))
                    requireActivity().finish()
                } else {
                    Toast.makeText(requireContext(), "Tài khoản không chính xác", Toast.LENGTH_SHORT).show()
                }
            }
    }
}