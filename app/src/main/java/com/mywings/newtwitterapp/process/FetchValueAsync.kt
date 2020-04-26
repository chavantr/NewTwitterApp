package com.mywings.newtwitterapp.process

import android.os.AsyncTask
import com.mywings.newtwitterapp.model.FetchAspect
import com.mywings.newtwitterapp.process.HttpConstants.ASPECT_LIST
import com.mywings.newtwitterapp.process.HttpConstants.ASPECT_NAME
import com.mywings.newtwitterapp.process.HttpConstants.ASPECT_NEGATIVE
import com.mywings.newtwitterapp.process.HttpConstants.ASPECT_POSITIVE
import com.mywings.newtwitterapp.process.HttpConstants.ID
import org.json.JSONArray

class FetchValueAsync : AsyncTask<Void, Void, ArrayList<FetchAspect>>() {

    private val httpConnectionUtil = HttpConnectionUtil()
    private lateinit var onFetchListener: OnFetchListener

    override fun doInBackground(vararg params: Void?): ArrayList<FetchAspect> {
        val response = httpConnectionUtil.requestGet(HttpConstants.URL.plus(HttpConstants.FETCH_VALUE))
        val fetchValues = ArrayList<FetchAspect>()
        response.let {
            if (!it.isNullOrEmpty()) {
                val jFetch = JSONArray(it)
                for (i in 0 until jFetch.length()) {
                    val jNode = jFetch.getJSONObject(i)
                    val node = FetchAspect()
                    node.id = jNode.getInt(ID)
                    node.aspectName = jNode.optString(ASPECT_NAME)
                    node.aspectList = jNode.optString(ASPECT_LIST)
                    node.aspectPositive = jNode.optInt(ASPECT_POSITIVE)
                    node.aspectNegative = jNode.optInt(ASPECT_NEGATIVE)
                    fetchValues.add(node)
                }
            }
        }
        return fetchValues
    }

    override fun onPostExecute(result: ArrayList<FetchAspect>?) {
        super.onPostExecute(result)
        onFetchListener.onFetchSuccess(result)
    }

    fun setOnFetchListener(onFetchListener: OnFetchListener) {
        this.onFetchListener = onFetchListener
        super.executeOnExecutor(THREAD_POOL_EXECUTOR)
    }
}