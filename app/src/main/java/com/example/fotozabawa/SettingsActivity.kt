package com.example.fotozabawa

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class SettingsActivity : AppCompatActivity() {

    private lateinit var spinnerT: Spinner
    private lateinit var spinnerA: Spinner
    private lateinit var spinnerbPS: Spinner
    private lateinit var spinneraPS: Spinner
    private lateinit var spinneraSPS: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        timeSpinner()
        amountSpinner()
        beforePhotoSound()
        afterPhotoSound()
        afterSeriesSound()

        val confirmButton = findViewById<Button>(R.id.btnConfirm)
        confirmButton.setOnClickListener {
                startActivity(Intent(this@SettingsActivity, MainActivity::class.java))
        }
    }

    fun timeSpinner() {
        val timeTab = arrayOf("1", "2", "5", "10", "30")
        spinnerT = findViewById(R.id.spinnerTime)
        if (spinnerT != null) {
            val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item, timeTab
            )
            spinnerT.adapter = adapter
        }
        spinnerT.onItemSelectedListener = object :
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
    }

    fun amountSpinner() {
        val amountTab = arrayOf("1", "2", "5", "10", "30")
        spinnerA = findViewById(R.id.spinnerAmount)
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
    }

    fun beforePhotoSound() {
        val bpsTab = arrayOf("1", "2", "3", "4")
        spinnerbPS = findViewById(R.id.spinnerbPS)
        if (spinnerbPS != null) {
            val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item, bpsTab
            )
            spinnerbPS.adapter = adapter
        }
        spinnerbPS.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View, position: Int, id: Long
            ) {
                Toast.makeText(
                    this@SettingsActivity, bpsTab[position], Toast.LENGTH_SHORT
                ).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                Toast.makeText(
                    this@SettingsActivity, "Nothing selected", Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    fun afterPhotoSound() {
        val apsTab = arrayOf("1", "2", "3", "4")
        spinneraPS = findViewById(R.id.spinneraPS)
        if (spinneraPS != null) {
            val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item, apsTab
            )
            spinneraPS.adapter = adapter
        }
        spinneraPS.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View, position: Int, id: Long
            ) {
                Toast.makeText(
                    this@SettingsActivity, apsTab[position], Toast.LENGTH_SHORT
                ).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                Toast.makeText(
                    this@SettingsActivity, "Nothing selected", Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    fun afterSeriesSound() {
        val aspsTab = arrayOf("1", "2", "3", "4")
        spinneraSPS = findViewById(R.id.spinneraSPS)
        if (spinneraSPS != null) {
            val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item, aspsTab
            )
            spinneraSPS.adapter = adapter
        }
        spinneraSPS.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View, position: Int, id: Long
            ) {
                Toast.makeText(
                    this@SettingsActivity, aspsTab[position], Toast.LENGTH_SHORT
                ).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                Toast.makeText(
                    this@SettingsActivity, "Nothing selected", Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}