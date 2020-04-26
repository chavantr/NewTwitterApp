package com.mywings.newtwitterapp.process

import android.os.AsyncTask
import com.mywings.newtwitterapp.model.TwitterComments
import org.json.JSONArray

class FetchFilterValue : AsyncTask<String?, Void, ArrayList<TwitterComments>?>() {

    private lateinit var onFilterDataListener: OnFilterDataListener
    private var response: String? = null

    override fun doInBackground(vararg params: String?): ArrayList<TwitterComments>? {
        response = HttpConnectionUtil().requestGet(HttpConstants.URL.plus(HttpConstants.LOAD_DATA))
        var filter: ArrayList<TwitterComments>? = null
        response.let {
            if (it.isNullOrEmpty()) filter = null else {
                filter = ArrayList()
                val jFilters = JSONArray(it)
                for (i in 0 until jFilters.length()) {
                    val jNode = jFilters.getJSONObject(i)
                    val twitterComments = TwitterComments()
                    twitterComments.id = jNode.getInt(HttpConstants.ID)
                    twitterComments.comment = jNode.getString(HttpConstants.COMMENT_DATA)
                    twitterComments.stopWord = jNode.getString(HttpConstants.STOP_WORD_DATA)
                    twitterComments.stemmer = jNode.getString(HttpConstants.STIMMER_DATA)
                    twitterComments.classLabel = jNode.getString(HttpConstants.CLASS_LABEL)
                    filter?.add(twitterComments)
                }
            }
        }
        return filter
    }

    override fun onPostExecute(result: ArrayList<TwitterComments>?) {
        super.onPostExecute(result)
        onFilterDataListener.onFilter(result)
    }

    fun setOnFilterListener(onFilterDataListener: OnFilterDataListener, param: String) {
        this.onFilterDataListener = onFilterDataListener
        this.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, param)
    }
}