package com.example.fotozabawa

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class SettingsActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val timeTab = arrayOf("1s", "2s", "5s", "10s", "30s")
        val spinner = findViewById<Spinner>(R.id.spinnerTime)
        if (spinner != null) {
            val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item, timeTab
            )
            spinner.adapter = adapter
        }
        spinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View, position: Int, id: Long
            ) {
                Toast.makeText(
                    this@SettingsActivity, timeTab[position], Toast.LENGTH_SHORT
                ).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                Toast.makeText(
                    this@SettingsActivity, "Nothing selected", Toast.LENGTH_SHORT
                ).show()
            }
        }

        val amountTab = arrayOf("1", "2", "5", "10", "30")
        val spinnerA = findViewById<Spinner>(R.id.spinnerAmount)
        if (spinnerA != null) {
            val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item, amountTab
            )
            spinnerA.adapter = adapter
        }
        spinnerA.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View, position: Int, id: Long
            ) {
                Toast.makeText(
                    this@SettingsActivity, amountTab[position], Toast.LENGTH_SHORT
                ).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                Toast.makeText(
                    this@SettingsActivity, "Nothing selected", Toast.LENGTH_SHORT
                ).show()
            }
        }

        val confirmButton = findViewById<Button>(R.id.btnConfirm)
        confirmButton.setOnClickListener {
            if (spinner != null && spinner.getSelectedItem() != null || spinnerA != null && spinnerA.getSelectedItem() != null) {
                // TODO set time interval and amount of photos on takePhotoButton
            }
        }
    }
}