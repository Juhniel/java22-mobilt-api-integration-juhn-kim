package com.example.java22_mobilt_api_integration_juhn_kim.api

import android.content.Context
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

class ApiService(private val context: Context) {
    // Initialize a request queue using Volley for making HTTP requests.
    private val requestQueue: RequestQueue by lazy {
        Volley.newRequestQueue(context.applicationContext)
    }

    // Function to fetch data from the Bored API.
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

    // Function to fetch data from the useless fact API.
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