package com.tavanhieu.chatapp.fcm_notifications

import android.content.Context
import android.os.StrictMode
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.tavanhieu.chatapp.m_class.HangSo
import org.json.JSONException
import org.json.JSONObject

class MyFirebaseMessagingSend {
    companion object {
        fun pushNotifications(context: Context, token: String, title: String, content: String) {
            val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
            StrictMode.setThreadPolicy(policy)

            val queue = Volley.newRequestQueue(context)
            try {
                val json = JSONObject()
                json.put("to", token)
                val notifications = JSONObject()
                notifications.put("title", title)
                notifications.put("body", content)
                json.put("notification", notifications)

                val jsonObjectRequest = object: JsonObjectRequest(Method.POST, HangSo.SERVER_URL, json, {}, {}) {
                    override fun getHeaders(): MutableMap<String, String> {
                        val map: HashMap<String, String> = HashMap()
                        map["Authorization"] = "key=" + HangSo.SERVER_KEY
                        map["Content-Type"] = "application/json"
                        return map
                    }
                }
                queue.add(jsonObjectRequest)
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }
    }
}