package com.example.java22_mobilt_api_integration_juhn_kim.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.java22_mobilt_api_integration_juhn_kim.R

class RandomFactActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_random_fact_activity)

        val btnYes = findViewById<Button>(R.id.btn_yes)
        val btnNo = findViewById<Button>(R.id.btn_no)

        btnYes.setOnClickListener {
            val intent = Intent(this, RandomBoredActivity::class.java)
            startActivity(intent)
        }

        btnNo.setOnClickListener {
            val intent = Intent(this, RandomBoredActivity::class.java)
            startActivity(intent)
        }
    }
}