package com.mywings.newtwitterapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.mywings.newtwitterapp.model.User
import com.mywings.newtwitterapp.process.LoginAsync
import com.mywings.newtwitterapp.process.OnLoginListener
import com.mywings.newtwitterapp.process.ProgressDialogUtil
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONObject


class LoginActivity : AppCompatActivity(), OnLoginListener {


    private lateinit var progressDialogUtil: ProgressDialogUtil

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        progressDialogUtil = ProgressDialogUtil(this@LoginActivity)
        btnSignIn.setOnClickListener {
            if (validate()) {
                initLogin()
            } else {
                Toast.makeText(this@LoginActivity, "Enter username and password", Toast.LENGTH_LONG).show()
            }
        }

        btnSignUp.setOnClickListener {
            val intent = Intent(this@LoginActivity, RegistrationActivity::class.java)
            startActivity(intent)
        }
    }

    private fun initLogin() {
        progressDialogUtil.show()
        var loginAsync = LoginAsync()
        var request = JSONObject()
        var param = JSONObject()
        param.put("Username", txtUsername.text)
        param.put("Password", txtPassword.text)
        request.put("request", param)
        loginAsync.setOnLoginListener(this, request)
    }

    override fun onLoginSuccess(user: User?) {
        progressDialogUtil.hide()
        if (null != user) startNextScreen() else Toast.makeText(
            this@LoginActivity,
            "Enter valid username and password",
            Toast.LENGTH_LONG
        ).show()
    }

    private fun startNextScreen() {
        val intent = Intent(this@LoginActivity, MainActivity::class.java)
        startActivity(intent)
    }

    private fun validate(): Boolean = !txtUsername.text.isNullOrEmpty() && !txtPassword.text.isNullOrEmpty()
}
