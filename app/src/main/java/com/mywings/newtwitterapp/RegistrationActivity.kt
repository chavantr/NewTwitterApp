package com.mywings.newtwitterapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.mywings.newtwitterapp.process.OnRegistrationListener
import com.mywings.newtwitterapp.process.ProgressDialogUtil
import kotlinx.android.synthetic.main.activity_registration.*

class RegistrationActivity : AppCompatActivity(), OnRegistrationListener {

    private lateinit var progressDialogUtil: ProgressDialogUtil
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        progressDialogUtil = ProgressDialogUtil(this@RegistrationActivity)
        btnSignUp.setOnClickListener {
            if (validate()) {
                if (validateConfirm()) initRegister() else Toast.makeText(
                    this@RegistrationActivity,
                    "password and confirm password should be same.",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                Toast.makeText(this@RegistrationActivity, "All fields required", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onRegistrationSuccess(success: Int?) {
        if (success != null) {
            if (success > 0) {
                finish()
            } else {
                showError()
            }
        } else {
            showError()
        }
    }

    private fun showError() {
        Toast.makeText(this@RegistrationActivity, "Something went wrong, please try again", Toast.LENGTH_LONG)
            .show()
    }

    private fun initRegister() {
        progressDialogUtil.show()
        //val registerAsync = Registe
    }

    private fun validate(): Boolean =
        !txtName.text.isNullOrEmpty()
                && !txtEmail.text.isNullOrEmpty()
                && !txtPhone.text.isNullOrEmpty()
                && !txtPassword.text.isNullOrEmpty()

    private fun validateConfirm(): Boolean = txtPassword.text == txtConfirmPassword.text
}
