package com.mywings.newtwitterapp.process

import com.mywings.newtwitterapp.model.TwitterComments

interface OnFilterDataListener {
    fun onFilter(result: ArrayList<TwitterComments>?)
}