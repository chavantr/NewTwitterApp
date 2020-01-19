package com.mywings.newtwitterapp.process

import com.mywings.newtwitterapp.model.User

interface OnLoginListener {
    fun onLoginSuccess(user: User?)
}