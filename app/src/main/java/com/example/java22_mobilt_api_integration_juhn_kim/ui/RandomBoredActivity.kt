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

class RandomBoredActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_random_bored_activity)

        val db = FirestoreUtil.getInstance();
        val tvActivity = findViewById<TextView>(R.id.tv_activity)
        val tvType = findViewById<TextView>(R.id.tv_type)
        val backToHomeBtn = findViewById<Button>(R.id.btn_home)

        val apiService = ApiService(this)


        apiService.fetchBoredAPI(
            onResponse = { response ->
                val gson = Gson()
                val jsonObject = gson.fromJson(response, JsonObject::class.java)
                val activity = jsonObject.get("activity").asString
                val type = jsonObject.get("type").asString

                tvActivity.text = "Activity suggestion: $activity"
                tvType.text = "Type of activity: $type"
            },
            onError = { error ->
                Toast.makeText(this, "Failed to fetch data: ${error.message}", Toast.LENGTH_LONG)
                    .show()
            }
        )

        val tvBoredCount = findViewById<TextView>(R.id.tv_bored_count)

        // Fetch the count of people who were not bored
        val boredRef = db.collection("counters").document("boredCount")
        boredRef.get()
            .addOnSuccessListener { document ->
                if (document != null && document.exists()) {
                    val boredCount = document.getLong("count") ?: 0
                    tvBoredCount.text = "$boredCount People have been bored while using this app"
                } else {
                    tvBoredCount.text = "People bored: 0"
                }
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, "Failed to fetch count: ${exception.message}", Toast.LENGTH_LONG)
                    .show()
            }

        backToHomeBtn.setOnClickListener {
            val goToMainActivity = Intent(this, MainActivity::class.java)
            goToMainActivity.flags =
                Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(goToMainActivity)
        }
    }
}