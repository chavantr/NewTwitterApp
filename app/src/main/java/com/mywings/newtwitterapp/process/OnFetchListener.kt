package com.mywings.newtwitterapp.process

import com.mywings.newtwitterapp.model.FetchAspect

interface OnFetchListener {
    fun onFetchSuccess(result: List<FetchAspect>?)
}