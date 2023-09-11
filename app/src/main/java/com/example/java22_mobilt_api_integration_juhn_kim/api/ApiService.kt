package com.example.java22_mobilt_api_integration_juhn_kim.api

import android.content.Context
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

class ApiService(private val context: Context) {

    private val requestQueue: RequestQueue by lazy {
        // applicationContext is key, it keeps you from leaking the
        // Activity or BroadcastReceiver if someone passes one in
        Volley.newRequestQueue(context.applicationContext)
    }

    fun fetchBoredAPI(onResponse: (String) -> Unit, onError: (Throwable) -> Unit) {
        val url = "https://www.boredapi.com/api/activity"

        val stringRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                onResponse(response)
            },
            { error ->
                onError(error)
            }
        )
        requestQueue.add(stringRequest)
    }

    fun fetchUselessFact(onResponse: (String) -> Unit, onError: (Throwable) -> Unit) {
        val url = "https://uselessfacts.jsph.pl/api/v2/facts/random"

        val stringRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                onResponse(response)
            },
            { error ->
                onError(error)
            }
        )
        requestQueue.add(stringRequest)
    }


}