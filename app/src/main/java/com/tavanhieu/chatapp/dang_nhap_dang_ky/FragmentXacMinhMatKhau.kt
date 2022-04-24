package com.tavanhieu.chatapp.dang_nhap_dang_ky

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.tavanhieu.chatapp.R

class FragmentXacMinhMatKhau: Fragment() {
    private lateinit var mView: View
    private lateinit var edtAccount: EditText
    private lateinit var edtCode:    EditText
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
        edtCode     = mView.findViewById(R.id.txtInputEdtCodeForgetPassXacMinh)
        txtDangNhap = mView.findViewById(R.id.txtNewAccountForgetPassXacMinh)
        btnXacNhan  = mView.findViewById(R.id.btnConfirmForgetPassXacMinh)
        imgBack     = mView.findViewById(R.id.imgArrowBackToolBarForgetPass)
    }

    private fun mOnClick() {
        val supportFragment = requireActivity().supportFragmentManager.beginTransaction()
        imgBack.setOnClickListener { requireActivity().onBackPressed() }

        btnXacNhan.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack("DangNhap", FragmentManager.POP_BACK_STACK_INCLUSIVE)
            requireActivity().supportFragmentManager.popBackStack("ThayDoiMK", FragmentManager.POP_BACK_STACK_INCLUSIVE)
            supportFragment.replace(R.id.fragmentDangNhapDangKy, FragmentDangNhap()).commit()
        }

        txtDangNhap.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack("DangNhap", FragmentManager.POP_BACK_STACK_INCLUSIVE)
            requireActivity().supportFragmentManager.popBackStack("ThayDoiMK", FragmentManager.POP_BACK_STACK_INCLUSIVE)
            supportFragment.replace(R.id.fragmentDangNhapDangKy, FragmentDangNhap()).commit()
        }
    }
}