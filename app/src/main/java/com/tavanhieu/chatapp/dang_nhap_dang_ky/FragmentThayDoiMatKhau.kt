package com.tavanhieu.chatapp.dang_nhap_dang_ky

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.tavanhieu.chatapp.R

class FragmentThayDoiMatKhau: Fragment() {
    private lateinit var mView: View
    private lateinit var edtAccount: EditText
    private lateinit var edtPassWord: EditText
    private lateinit var edtPassWord2: EditText
    private lateinit var txtDangNhap: TextView
    private lateinit var btnXacNhan: Button
    private lateinit var imgBack: ImageView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mView = inflater.inflate(R.layout.forget_pass, container, false)
        anhXa()
        mOnClick()
        return mView
    }

    private fun anhXa() {
        edtAccount      = mView.findViewById(R.id.txtInputEdtAccountForgetPass)
        edtPassWord     = mView.findViewById(R.id.txtInputEdtPassWordForgetPass)
        edtPassWord2    = mView.findViewById(R.id.txtInputEdtPassWord2ForgetPass)
        txtDangNhap     = mView.findViewById(R.id.txtDangNhapInForgetPass)
        btnXacNhan      = mView.findViewById(R.id.btnConfirmForgetPass)
        imgBack         = mView.findViewById(R.id.imgArrowBackToolBarForgetPass)
    }

    private fun mOnClick() {
        val supportFragment = requireActivity().supportFragmentManager.beginTransaction()
        imgBack.setOnClickListener { requireActivity().onBackPressed() }

        btnXacNhan.setOnClickListener {
            supportFragment.replace(R.id.fragmentDangNhapDangKy, FragmentXacMinhMatKhau()).addToBackStack("ThayDoiMK").commit()
        }

        txtDangNhap.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack("DangNhap", FragmentManager.POP_BACK_STACK_INCLUSIVE)
            supportFragment.replace(R.id.fragmentDangNhapDangKy, FragmentDangNhap()).commit()
        }
    }
}