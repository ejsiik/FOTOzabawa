package com.example.fotozabawa

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        /*val confirmButton = findViewById<Button>(R.id.btnConfirm)

        confirmButton.setOnClickListener {
            startActivity(Intent(this@MenuActivity, MainActivity::class.java))
        }*/
    }
}