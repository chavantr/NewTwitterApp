package com.mywings.newtwitterapp.process

import android.os.AsyncTask
import org.json.JSONArray

class FetchFilterValue : AsyncTask<String?, Void, ArrayList<String>?>() {

    private lateinit var onFilterDataListener: OnFilterDataListener
    private var response: String? = null

    override fun doInBackground(vararg params: String?): ArrayList<String>? {
        response = HttpConnectionUtil().requestGet(HttpConstants.URL.plus(HttpConstants.LOAD_DATA))

        var filter: ArrayList<String>? = null

        response.let {

            if (it.isNullOrEmpty()) filter = null else {
                filter = ArrayList()
                val jFilters = JSONArray(it)
                for (i in 0 until jFilters.length()) {
                    filter?.add(jFilters.getJSONObject(i).getString(""))
                }
            }
        }
        return filter
    }

    override fun onPostExecute(result: ArrayList<String>?) {
        super.onPostExecute(result)
        onFilterDataListener.onFilter(result)
    }

    private fun setOnFilterListener(onFilterDataListener: OnFilterDataListener, param: String) {
        this.onFilterDataListener = onFilterDataListener
        this.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, param)
    }

}