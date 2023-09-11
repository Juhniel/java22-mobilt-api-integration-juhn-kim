package com.example.java22_mobilt_api_integration_juhn_kim.ui

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.java22_mobilt_api_integration_juhn_kim.R
import com.google.firebase.firestore.FieldValue

class MenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        // Get instance of Firestore
        val db = FirestoreUtil.getInstance();

        val btnYes = findViewById<Button>(R.id.btn_yes)
        val btnNo = findViewById<Button>(R.id.btn_no)

        /*
        * Whenever a button is clicked we add the counter to keep track of how many are bored / not bored
        * We then store it in Firestore for later use in our RandomBoredActivity and RandomFactActivity
        * */

        btnYes.setOnClickListener {
            // Increment the "boredCount" field value by 1
            val boredRef = db.collection("counters").document("boredCount")
            boredRef.update("count", FieldValue.increment(1))
                .addOnSuccessListener {
                    Log.d(TAG, "Counter successfully incremented!")
                    val intent = Intent(this, RandomBoredActivity::class.java)
                    startActivity(intent)
                }
                .addOnFailureListener { e ->
                    Log.w(TAG, "Error updating counter", e)
                }
        }

        btnNo.setOnClickListener {
            // Increment the "notBoredCount" field value by 1
            val notBoredRef = db.collection("counters").document("notBoredCount")
            notBoredRef.update("count", FieldValue.increment(1))
                .addOnSuccessListener {
                    Log.d(TAG, "Not-bored counter successfully incremented!")
                    val intent = Intent(this, RandomFactActivity::class.java)
                    startActivity(intent)
                }
                .addOnFailureListener { e ->
                    Log.w(TAG, "Error updating not-bored counter", e)
                }
        }
    }
}