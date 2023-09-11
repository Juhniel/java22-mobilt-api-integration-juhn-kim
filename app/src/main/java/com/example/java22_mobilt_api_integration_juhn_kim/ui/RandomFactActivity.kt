package com.example.java22_mobilt_api_integration_juhn_kim.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.java22_mobilt_api_integration_juhn_kim.R
import com.example.java22_mobilt_api_integration_juhn_kim.api.ApiService
import com.google.gson.Gson
import com.google.gson.JsonObject


class RandomFactActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_random_fact_activity)

        val db = FirestoreUtil.getInstance();
        val tvFact = findViewById<TextView>(R.id.tv_fact)
        val backToHomeBtn = findViewById<Button>(R.id.btn_home)

        // api
        val apiService = ApiService(this)

        apiService.fetchUselessFact(
            onResponse = { response ->
                val gson = Gson()
                val jsonObject = gson.fromJson(response, JsonObject::class.java)
                val text = jsonObject.get("text").asString
                tvFact.text = "Random fact: $text"
            },
            onError = { error ->
                Toast.makeText(this, "Failed to fetch data: ${error.message}", Toast.LENGTH_LONG).show()
            }
        )

        val tvNotBoredCount = findViewById<TextView>(R.id.tv_not_bored_count)

        // Fetch the count of people who were not bored
        val notBoredRef = db.collection("counters").document("notBoredCount")
        notBoredRef.get()
            .addOnSuccessListener { document ->
                if (document != null && document.exists()) {
                    val notBoredCount = document.getLong("count") ?: 0
                    tvNotBoredCount.text = "$notBoredCount People has not been bored while using this app"
                } else {
                    tvNotBoredCount.text = "People not bored: 0"
                }
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, "Failed to fetch count: ${exception.message}", Toast.LENGTH_LONG)
                    .show()
            }

        // Navigate back to the main activity and clear the back stack.
        backToHomeBtn.setOnClickListener {
            val goToMainActivity = Intent(this, MainActivity::class.java)
            goToMainActivity.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(goToMainActivity)
        }
    }
}