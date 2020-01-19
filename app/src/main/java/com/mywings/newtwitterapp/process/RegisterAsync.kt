package com.mywings.newtwitterapp.process

import android.os.AsyncTask
import org.json.JSONObject

class RegisterAsync : AsyncTask<JSONObject?, Void, Int?>() {
    private lateinit var onRegistrationListener: OnRegistrationListener
    private val httpConnectionUtil = HttpConnectionUtil()
    private var response: String? = null
    override fun doInBackground(vararg params: JSONObject?): Int? {
        response = httpConnectionUtil.requestPost(HttpConstants.URL.plus(HttpConstants.REGISTER), params[0])
        response.let {
            it?.toInt() ?: return null
        }
        return null
    }

    override fun onPostExecute(result: Int?) {
        super.onPostExecute(result)
        onRegistrationListener.onRegistrationSuccess(result)
    }

    fun setOnRegisterListener(onRegistrationListener: OnRegistrationListener, request: JSONObject?) {
        this.onRegistrationListener = onRegistrationListener
        super.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, request)
    }

}